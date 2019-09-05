#ifndef MILESTONE2_SERVER_H
#define MILESTONE2_SERVER_H
#include "ClientHandler.h"

#include <stdio.h>
#include <stdlib.h>

#include <netdb.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <sstream>

#include <string.h>
#include <string>
#include <iostream>
#include <atomic>
#include <thread>

namespace server_side{
    /*our Server as interface */

    template<class Problem,class Solution>
    class Server{

    public:
        virtual std::thread* open(int port,ClientHandler<Problem,Solution>* c) = 0;
        virtual void stop() = 0;
        virtual ~Server(){}
    };
}

#endif