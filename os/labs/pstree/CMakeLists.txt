cmake_minimum_required(VERSION 3.20)

#-- Specifies the build type
if(NOT CMAKE_BUILD_TYPE)
    message(STATUS "Build type not set - defaulting to Release")
    set(CMAKE_BUILD_TYPE "Release" CACHE
            STRING "Choose the type of build from: Debug Release."
            FORCE)
endif()

if (${CMAKE_BUILD_TYPE} MATCHES "Debug")
    add_definitions(-DBUILD_DEBUG)
endif()


add_executable(pstree pstree.c)
target_link_libraries(pstree m)
