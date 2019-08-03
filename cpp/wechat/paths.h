#ifndef __THANOS_CORE_WECHAT_PATHS_INCLUDED__
#define __THANOS_CORE_WECHAT_PATHS_INCLUDED__


#include <string>


namespace Thanos {
namespace Core {
namespace WeChat {

    class Paths {
        public:
            static std::wstring &getBasePath();
            static std::wstring &getExecutablePath();
            static std::wstring &getWinLibraryPath();

        private:
            static std::wstring wechat_base_path;
            static std::wstring wechat_executable_path;
            static std::wstring wechat_win_library_path;
    };

}

}
}



#endif // __THANOS_CORE_WECHAT_PATHS_INCLUDED__
