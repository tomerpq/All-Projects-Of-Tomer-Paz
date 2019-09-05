#ifndef MILESTONE2_MYPARALLELSERVER_H
#define MILESTONE2_MYPARALLELSERVER_H


#include <atomic>
#include "Server.h"
#include "MyClientHandler.h"

/*our parralel server takes care of clients in parralel */
template<class Problem,class Solution>

class MyParallelServer : public server_side::Server<Problem,Solution>{
    std::atomic<bool> stopIndicator;
    std::thread* myThread;
    void openInThread(int port,ClientHandler<Problem,Solution>* c);
    static int openNewThread(MyParallelServer* me , int port, ClientHandler<Problem, Solution> *c);
    static void sendToClientHandler(ClientHandler<Problem, Solution> *c, int newSock);
        public:
    std::thread* open(int port,ClientHandler<Problem,Solution>* c);
    void stop();
    MyParallelServer();
    ~MyParallelServer();
};


template<class Problem, class Solution>
// we now open here a new thread to activate the loop to listen to new clients
std::thread* MyParallelServer<Problem, Solution>::open(int port, ClientHandler<Problem, Solution> *c)
{
    //opening the server in new thread to be deleted by main
    return new std::thread(openNewThread, this, port, c);
}

template<class Problem, class Solution>
int MyParallelServer<Problem, Solution>::openNewThread(MyParallelServer *me, int port,
                                                       ClientHandler<Problem, Solution> *c) {
    //adapter to open the server in new thread
    me->openInThread(port,c);
    return 0;
}

template<class Problem, class Solution>\
void MyParallelServer<Problem, Solution>::openInThread(int port, ClientHandler<Problem, Solution> *c)
{

    int s = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in serv;
    serv.sin_addr.s_addr = INADDR_ANY;
    serv.sin_port = htons(port);
    serv.sin_family = AF_INET;

    if (bind(s, (sockaddr *)&serv, sizeof(serv)) < 0)
    {
        std::cerr << "Bad!" << std::endl;
    }

    int new_sock;
    //maxing out the client queue
    listen(s, SOMAXCONN);
    struct sockaddr_in client;
    socklen_t clilen = sizeof(client);
    timeval timeout;

    int i = 0;

    std::vector<std::thread*> threads = {};
    //waiting for first client for alot of time after one client it will be changed to 1 sec
    timeout.tv_sec = 100000000;
    timeout.tv_usec = 0;

    //loop untill the main process change the indicator to true with the close() method
    //or untill the server times out
    while(!stopIndicator.load(std::memory_order_seq_cst)) {

        setsockopt(s, SOL_SOCKET, SO_RCVTIMEO, (char *) &timeout, sizeof(timeout));

        new_sock = accept(s, (struct sockaddr *) &client, &clilen);

        if (new_sock < 0) {
            if (errno == EWOULDBLOCK) {
                std::cout << "timeout!, finishing with all other clients..." << std::endl;
                //waiting for all thread to finish
                for(auto t : threads){
                    // wating for all thread to join and deleting them
                    t->join();
                    delete t;
                }
                return;
            } else {
                perror("other error");
                return;
            }
        }else{
            //after creating new thread we add it to the list later to be deleted after we close the server
            auto* newthread = new std::thread(sendToClientHandler, c, new_sock);
            threads.emplace_back(newthread);
        }

        //changing waiting time to 1 sec
        timeout.tv_sec = 1;
        timeout.tv_usec = 0;

    }
    close(s);
    for(auto t : threads){
        // wating for all thread to join and deleting them
        t->join();
        delete t;
    }

}

template<class Problem, class Solution>
void MyParallelServer<Problem, Solution>::sendToClientHandler(ClientHandler<Problem, Solution> *c, int newSock) {
    c->handleClient(newSock);
}

template<class Problem, class Solution>
void MyParallelServer<Problem, Solution>::stop() {
    //changing the atomic indicator to true so we know we need to close server
    stopIndicator.exchange(true,std::memory_order_seq_cst);
}

template<class Problem, class Solution>
MyParallelServer<Problem, Solution>::MyParallelServer() {
    //in the constructor we initialize the atomic variable indicator
    stopIndicator.exchange(false,std::memory_order_seq_cst);
}

template<class Problem, class Solution>
MyParallelServer<Problem, Solution>::~MyParallelServer() {
}

#endif
