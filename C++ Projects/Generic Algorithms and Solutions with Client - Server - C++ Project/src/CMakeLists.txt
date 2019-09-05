cmake_minimum_required(VERSION 3.13)
project(mileStone2)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS -pthread)

include_directories(.)

add_executable(mileStone2
        AdapterSolverToIsearcher.h
        AStar.h
        BestFirstSearch.h
        BFS.h
        CacheManager.h
        ClientHandler.h
        DFS.h
        FastestPath.h
        FileCacheManager.h
        HashMapI.h
        HashSetI.h
        HashSetStringState.h
        HeuristicForAStar.h
        HeuristicForBestFirstSearch.h
        ISearchable.h
        ISearcher.h
        ISolutionable.h
        Main.cpp
        MyClientHandler.h
        MyParallelServer.h
        MyPriorityQueue.h
        Queue.h
        Searcher.h
        Server.h
        SolutionWithString.h
        Solver.h
        State.h
        )
