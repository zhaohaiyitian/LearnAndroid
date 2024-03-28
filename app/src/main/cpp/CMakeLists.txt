cmake_minimum_required(VERSION 3.22.1)

project("learn")

#调用头文件的具体路径
include_directories(${CMAKE_CURRENT_SOURCE_DIR})

add_library(
        learn
        SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        native-lib.cpp
        MemoryFile.cpp)


find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries(
        learn
        # List libraries link to the target library
        android
        log)