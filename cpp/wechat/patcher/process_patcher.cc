#include <set>
#include <shlwapi.h>
#include <shellapi.h>
#include <tlhelp32.h>
#include "core/wechat/patcher/process_patcher.h"
#include "common/utils/cleanup.h"

#define BUFFER_SIZE 0x1000
#define NT_API_SUCCESS(exp) ((ULONG)(exp) >= 0)
#define STATUS_INFO_LENGTH_MISMATCH(status) ((status) != (ULONG)0xC0000004L)


static bool closeMutexHandlers(SystemHandleInformation *information);
static std::set<DWORD> getWeChatProcessIds();
static HANDLE duplicateHandle(DWORD process_id, HANDLE src_handle, DWORD flags);

using Thanos::Common::Utils::EnsureCloseHandle;
using Thanos::Common::Utils::EnsureReleaseVirtualMemory;
using namespace Thanos::Core::WeChat::Patcher;

bool ProcessPatcher::patch() {
    auto ZwQuerySystemInformation = (ZwQuerySystemInformationFunc)GetProcAddress(
        GetModuleHandle("ntdll.dll"), "ZwQuerySystemInformation");

    EnsureReleaseVirtualMemory buffer;
    if (!buffer.realloc(BUFFER_SIZE)) {
        return false;
    }

    DWORD buffer_size = 0;
    auto status = ZwQuerySystemInformation(SYSTEM_HANDLE_INFORMATION, (LPVOID)buffer, BUFFER_SIZE, &buffer_size);
    if (!NT_API_SUCCESS(status) && STATUS_INFO_LENGTH_MISMATCH(status)) {
        return false;
    }

    // re-alloc correct buffer
    if (!buffer.realloc(buffer_size * 2)) {
        return false;
    }

    // actual query system information
    status = ZwQuerySystemInformation(SYSTEM_HANDLE_INFORMATION, (LPVOID)buffer, buffer_size * 2, nullptr);
    if (!NT_API_SUCCESS(status)) {
        return false;
    }

    return closeMutexHandlers((SystemHandleInformation*)((LPVOID)buffer));
}

extern bool closeMutexHandlers(SystemHandleInformation *information) {
    std::set<DWORD> wechat_process_ids(getWeChatProcessIds());
    auto NtQueryObject = (NtQueryObjectFunc)GetProcAddress(GetModuleHandleA("ntdll.dll"), "NtQueryObject");

    auto name_buffer = new u_char[BUFFER_SIZE]{ 0 };
    auto type_buffer = new u_char[BUFFER_SIZE]{ 0 };
    for (auto index = 0UL; index < information->numberOfHandles; ++index) {
        auto process_id = information->handles[index].uniqueProcessId;
        auto src_handle = (HANDLE)(uintptr_t)information->handles[index].handleValue;

        // handle owner for wechat
        if (wechat_process_ids.find(process_id) != wechat_process_ids.end()) {
            auto handle = duplicateHandle(process_id, src_handle, DUPLICATE_SAME_ACCESS);
            if (handle == nullptr) {
                continue;
            }

            if (!NT_API_SUCCESS(NtQueryObject(handle, OBJECT_NAME_INFORMATION, name_buffer, BUFFER_SIZE, nullptr))) {
                CloseHandle(handle);
                continue;
            }
            auto name_information = (ObjectNameInformation*)name_buffer;

            if (!NT_API_SUCCESS(NtQueryObject(handle, OBJECT_TYPE_INFORMATION, type_buffer, BUFFER_SIZE, nullptr))) {
                CloseHandle(handle);
                continue;
            }
            auto type_information = (ObjectNameInformation*)type_buffer;

            // Mutant Handle
            if (wcscmp((WCHAR*)type_information->name.buffer, L"Mutant") == 0) {
                auto handle_name = (WCHAR*)(name_information->name.buffer);
                if (handle_name == nullptr) {
                    continue;
                }

                // WeChat Instance Mutex (\\Sessions\\1\\BaseNamedObjects\\_WeChat_App_Instance_Identity_Mutex_Name)
                // WeChat User Mutex (\\Sessions\\1\\BaseNamedObjects\\wxid_3pshp1s6duc221_WeChat_Win_User_Identity_Mutex_Name)
                if (wcsstr(handle_name, L"_WeChat_") && wcsstr(handle_name, L"_Instance_Identity_Mutex_Name")) {
                    // close handle from duplicate
                    CloseHandle(handle);

                    // close source handle
                    handle = duplicateHandle(process_id, src_handle, DUPLICATE_CLOSE_SOURCE);
                    if (handle == nullptr) {
                        return false;
                    }
                    // close handle from duplicate secondly
                    CloseHandle(handle);
                }
            }
        }
    }

    delete[] name_buffer;
    delete[] type_buffer;
    return true;
}

extern std::set<DWORD> getWeChatProcessIds() {
    std::set<DWORD> wechat_process_ids;

    PROCESSENTRY32 pe32 = { sizeof(PROCESSENTRY32) };
    HANDLE snapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
    if (snapshot) {
        if (Process32First(snapshot, &pe32)) {
            do {
                if (strcmp(pe32.szExeFile, "WeChat.exe") == 0) {
                    wechat_process_ids.insert(pe32.th32ProcessID);
                }
            } while (Process32Next(snapshot, &pe32));
        }

        CloseHandle(snapshot);
    }

    return wechat_process_ids;
}

extern HANDLE duplicateHandle(DWORD process_id, HANDLE src_handle, DWORD flags) {
    HANDLE dst_handle = nullptr;

    EnsureCloseHandle process(OpenProcess(PROCESS_ALL_ACCESS, false, process_id));
    if (process.isValid()) {
        if (!DuplicateHandle((HANDLE)process, src_handle, GetCurrentProcess(), &dst_handle, 0, false, flags)) {
            dst_handle = nullptr;
        }
    }
    return dst_handle;
}
