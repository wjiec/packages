#ifndef __THANOS_CORE_WECHAT_PATCHER_PROCESS_PATCHER_INCLUDED__
#define __THANOS_CORE_WECHAT_PATCHER_PROCESS_PATCHER_INCLUDED__


#include <windows.h>

// SystemInformationClass
typedef enum _SYSTEM_INFORMATION_CLASS {
    SYSTEM_HANDLE_INFORMATION = 16,
} SystemInformationClass;

// ZwQuerySystemInformation signature
typedef ULONG (NTAPI *ZwQuerySystemInformationFunc)(
IN SystemInformationClass systemInformationClass,
OUT PVOID systemInformation,
IN ULONG systemInformationLength,
OUT PULONG returnLength OPTIONAL
);

// SystemHandleTableEntryInfo
typedef struct SystemHandleTableEntryInfo_t {
    USHORT uniqueProcessId;
    USHORT creatorBackTraceIndex;
    UCHAR objectTypeIndex;
    UCHAR handleAttributes;
    USHORT handleValue;
    PVOID object;
    ULONG grantedAccess;
} SystemHandleTableEntryInfo;

// SystemHandleInformation
typedef struct SystemHandleInformation_t {
    ULONG numberOfHandles;
    SystemHandleTableEntryInfo handles[];
} SystemHandleInformation;

// ObjectInformationClass
typedef enum _OBJECT_INFORMATION_CLASS {
    OBJECT_NAME_INFORMATION = 1,
    OBJECT_TYPE_INFORMATION = 2,
} ObjectInformationClass;

// ObjectNameInformation
typedef struct ObjectNameInformation_t {
    struct {
        USHORT length;
        USHORT maxLen;
        USHORT *buffer;
    } name;
} ObjectNameInformation;

// NtQueryObject signature
typedef ULONG (NTAPI *NtQueryObjectFunc)(
_In_opt_   HANDLE handle,
_In_       ObjectInformationClass objectInformationClass,
_Out_opt_  PVOID objectInformation,
_In_       ULONG objectInformationLength,
_Out_opt_  PULONG returnLength
);


namespace Thanos {
namespace Core {
namespace WeChat {
namespace Patcher {

    class ProcessPatcher {
        public:
            static bool patch();
    };

}
}
}
}


#endif // __THANOS_CORE_WECHAT_PATCHER_PROCESS_PATCHER_INCLUDED__
