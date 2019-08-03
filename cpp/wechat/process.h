#ifndef __THANOS_CORE_WECHAT_PROCESS_INCLUDED__
#define __THANOS_CORE_WECHAT_PROCESS_INCLUDED__


#include <string>
#include <windows.h>


namespace Thanos {
namespace Core {
namespace WeChat {

    class Process {
        public:
            explicit Process(std::wstring &executable);
            ~Process();

        public:
            bool startup();
            bool terminal();
            DWORD getProcessId();

        private:
            std::wstring executable;
            PROCESS_INFORMATION process_information;
    };

}
}
}



#endif // __THANOS_CORE_WECHAT_PROCESS_INCLUDED__
