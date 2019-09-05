cmake_minimum_required(VERSION 3.13)
project(Ex4 C)

set(CMAKE_C_STANDARD 99 )
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -pthread")

add_executable(Ex4 main.c osqueue.c osqueue.h threadPool.c threadPool.h)