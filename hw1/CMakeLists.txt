cmake_minimum_required(VERSION 3.20)
project(hw1)

set(CMAKE_CXX_STANDARD 14)

add_subdirectory(lib/googletest)

include_directories(lib/googletest/googletest/include)

add_executable(hw1 src/tests.cpp)

target_link_libraries(hw1 gtest gtest_main)

#add_catch(test_lru_cache test.cpp)
