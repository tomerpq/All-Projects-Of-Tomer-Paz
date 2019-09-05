#ifndef MILESTONE2_MYCLIENTHANDLER_H
#define MILESTONE2_MYCLIENTHANDLER_H

#include "ClientHandler.h"
#include "Solver.h"
#include "CacheManager.h"
#include "FastestPath.h"
#include <cstdio>
#include <cstdlib>
#include <strings.h>
#include <unistd.h>
#include <string>
#include <vector>
#include <mutex>
#include <sstream>
#include <cstring>

/*this takes care of the client */
template<class Problem,class Solution>
class MyClientHandler : public ClientHandler<Problem,Solution>{
private:
    //every client have its solver
    Solver<Problem, Solution>* solver;
    //cache manager is only one so we dont delete it here
    CacheManager<Problem, std::string>* cacheManager;
    //solver lock so the prosseces wont interfere with eachouther
    std::mutex SolverLock;
public:
    void handleClient(int newsockfd);
    static void StatichandleClient(int newsockfd, Solver<Problem, Solution>*, CacheManager<Problem, Solution>*,  std::mutex*);
    virtual ~MyClientHandler();
    MyClientHandler(Solver<Problem, Solution> *solver, CacheManager<Problem, std::string> *cacheManager);
};
/*
template<class Problem,class Solution>
void MyClientHandler<Problem,Solution>::handleClient(int newsockfd){
    MyClientHandler<Problem, Solution>::StatichandleClient(newsockfd, solver, cacheManager, &SolverLock);
}
*/
template<class Problem, class Solution>
void MyClientHandler<Problem, Solution>::handleClient(int newsockfd) {
    std::vector<std::vector<std::string>> problem = {{}};
    char buffer[2048];
    int  n;
    int rowcounter = 0;
    std::string temp;
    // checks if the connection is valid
    if (newsockfd < 0)
    {
        perror("ERROR on accept");
        exit(1);
    }

    /* If connection is established then start communicating */
    bzero(buffer,2048);
    n = read( newsockfd,buffer,2048);
    //converting to string and removing ","
    int i = 0;
    while(buffer[i] != '\0')
    {
        if(buffer[i] == ',')
            buffer[i] = ' ';
        i++;
    }
    std::string BuffStr(buffer);

    //checking if the reading was sucessesfull
    if (n < 0)
    {
        perror("ERROR reading from socket");
        exit(1);
    }
    //while we dont read "end" we keep on reading and comunicating with the client

    while(BuffStr != "end")
    {
        //putting the data into a stream
        std::istringstream iss(BuffStr);

        while(iss >> temp){
            problem.at(rowcounter).emplace_back(temp);
        }

        rowcounter++;
        problem.emplace_back();

        /* Write a response to the client */
        n = write(newsockfd,"I got your message",18);
        if (n < 0) {
            perror("ERROR writing to socket");
            exit(1);
        }

        bzero(buffer,2048);
        n = read( newsockfd,buffer,2048);
        //converting to string and removing ","
        i = 0;
        while(buffer[i] != '\0')
        {
            if(buffer[i] == ',')
                buffer[i] = ' ';
            i++;
        }
        BuffStr = buffer;
    }

    //up to here we only got infromation from the client but now to the
    //solving operation we lock the mutex so the clients wont interfere each other
    problem.pop_back();
    SolverLock.lock();

    //creating new problem from the client input
    Problem* prob = new FastestPath(problem);
    //asking for solution from cache manager
    std::string* fromChache = cacheManager->getFromCache(prob);
    Solution* sol;

    //if we got null pointer from the cache manager it means we dont have the answer there
    //so we call the solver to solve it for us
    if(fromChache == nullptr) {
        //callong solver to solve
        sol = solver->solve(prob);
        //turning solution to string
        fromChache = new std::string((*sol).returnSolution());
        //adding answer to cache for next times we get the same problem
        cacheManager->addToCache(prob, fromChache);
    }

    //making string to char*
    char *cstr = new char[fromChache->length() + 1];
    std::strcpy(cstr, (*fromChache).c_str());

    n = write(newsockfd, cstr, fromChache->length() + 1);

    //deleting all pointers we created in this process
    delete [] cstr;
    delete fromChache;
    delete prob;

    //unlocking mutex
    SolverLock.unlock();

    //closing client socket
    close(newsockfd);
}


template<class Problem, class Solution>
MyClientHandler<Problem, Solution>::~MyClientHandler() {
    delete solver;
}

template<class Problem, class Solution>
MyClientHandler<Problem, Solution>::MyClientHandler(Solver<Problem, Solution> *solver,
                                                    CacheManager<Problem, std::string> *cacheManager):solver(
        solver), cacheManager(cacheManager) {}

#endif
