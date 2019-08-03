#include <stdexcept>
#include "core/wechat/process.h"
#include "common/utils/cleanup.h"
#include "core/wechat/patcher/process_patcher.h"
#include "common/logger/message_box.h"


#define nil nullptr
#define CREATE_FLAGS ((DWORD)(CREATE_NEW_CONSOLE | CREATE_SUSPENDED))


using Thanos::Common::Utils::EnsureCloseHandle;
using Thanos::Core::WeChat::Patcher::ProcessPatcher;
using namespace Thanos::Core::WeChat;

Process::Process(std::wstring &executable)
    : executable(executable), process_information({}) {
    // Process Constructor
}

Process::~Process() {
    terminal();
}

bool Process::startup() {
    auto command = (LPWSTR)executable.c_str();
    STARTUPINFOW si = { sizeof(STARTUPINFOW) };
    if (!CreateProcessW(nil, command, nil, nil, false, CREATE_FLAGS, nil, nullptr, &si, &process_information)) {
        throw std::runtime_error("Create WechatProcess error occurs");
    }

    // multi-wechat patch
    ProcessPatcher::patch();
    ResumeThread(process_information.hThread);
    Sleep(500); // waiting for injector

    // closed useless handlers
    CloseHandle(process_information.hThread);
    CloseHandle(process_information.hProcess);

    return true;
}

bool Process::terminal() {
    EnsureCloseHandle process(OpenProcess(SYNCHRONIZE | PROCESS_TERMINATE, true, getProcessId()));
    if (process.isValid()) {
        return (bool)TerminateProcess((HANDLE)process, 0);
    }
    return true;
}

DWORD Process::getProcessId() {
    return process_information.dwProcessId;
}
