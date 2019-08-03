#include <windows.h>
#include <stdexcept>
#include "core/wechat/paths.h"
#include "common/utils/cleanup.h"


using Thanos::Common::Utils::EnsureCloseHKey;
using namespace Thanos::Core::WeChat;

std::wstring Paths::wechat_base_path{};
std::wstring Paths::wechat_executable_path{};
std::wstring Paths::wechat_win_library_path{};

std::wstring &Paths::getBasePath() {
    if (wechat_base_path.empty()) {
        EnsureCloseHKey key(nullptr);

        if (RegOpenKeyW(HKEY_CURRENT_USER, L"Software\\Tencent\\WeChat", key.ptr()) != ERROR_SUCCESS) {
            throw std::runtime_error("WeChat not found in system");
        }

        WCHAR path[MAX_PATH] = { 0 };
        DWORD value_type = REG_SZ;
        DWORD value_size = MAX_PATH * sizeof(WCHAR);
        if (RegQueryValueExW((HKEY)(key), L"InstallPath", nullptr, &value_type, (LPBYTE)&path, &value_size) != ERROR_SUCCESS) {
            throw std::runtime_error("Please make sure WeChat install correct");
        }

        wechat_base_path.append(path);
    }

    return wechat_base_path;
}

std::wstring &Paths::getExecutablePath() {
    if (wechat_executable_path.empty()) {
        wechat_executable_path = getBasePath() + L"\\WeChat.exe";
    }

    return wechat_executable_path;
}

std::wstring &Paths::getWinLibraryPath() {
    if (wechat_win_library_path.empty()) {
        wechat_win_library_path = getBasePath() + L"\\WeChatWin.dll";
    }

    return wechat_win_library_path;
}
