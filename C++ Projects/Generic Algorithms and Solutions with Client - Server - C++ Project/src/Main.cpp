
#include <iostream>
#include <vector>
#include <chrono>

#include "MyParallelServer.h"
#include "MyClientHandler.h"
#include "FastestPath.h"
#include "SolutionWithString.h"
#include "AdapterSolverToIsearcher.h"
#include "BFS.h"
#include "HashSetStringState.h"
#include "DFS.h"
#include "AStar.h"
#include "BestFirstSearch.h"
#include "FileCacheManager.h"
/*our main: */
int main(int argc, char* argv[]){
    //creating solutionable
    ISolutionable<std::string>* Is = new SolutionWithString();
    //creating AStar solver
    Solver<ISearchable<std::string>,ISolutionable<std::string>>* solver = new AdapterSolverToIsearcher<std::string>(new AStar<std::string>(Is, new HashSetStringState()));
    //creating cache manager
    CacheManager<ISearchable<std::string>, std::string>* myCManager = new FileCacheManager<ISearchable<std::string>, std::string>("FastestPathFile.txt");
    //creating client handler with the cache manager we created and with the solver we created
    ClientHandler<ISearchable<std::string>,ISolutionable<std::string>>* C_Handler = new MyClientHandler<ISearchable<std::string>,ISolutionable<std::string>>(solver, myCManager);
    //creating server and injecting it the client handler
    server_side::Server<ISearchable<std::string>,ISolutionable<std::string>>* myServer = new MyParallelServer<ISearchable<std::string>,ISolutionable<std::string>>();
    //opening server and saving the thread it created so we wait for it
    std::thread* t = myServer->open(std::stoi(std::string(argv[1])),C_Handler);
    //waiting for the server thread
    t->join();
    //deleting all allocated memory
    delete t;
    delete myServer;
    delete myCManager;
    delete C_Handler;
    //finishing process
    return 0;
}