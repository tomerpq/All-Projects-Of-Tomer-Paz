#include <map>
#include <list>
#include <vector>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <mutex>
#include <thread>
#include <netdb.h>
#include <unistd.h>
#include <netinet/in.h>

#include <string.h>

#include <sys/socket.h>
#include <map>

#ifndef FIRST_STEP_DATAREADERSERVER_H
#define FIRST_STEP_DATAREADERSERVER_H

class DataReaderServer {
    std::map<std::string, double> dataReceived;
    std::vector<std::string> insertOrder;
    int newsockfd;

public:
    DataReaderServer();
    void execution (std::map<std::string, double> *symbolTable, std::map<std::string, std::string> *varAddresses,
                    int port, bool* accepted);
    void actualizeData(std::map<std::string, double> *symbolTable, std::map<std::string,std::string> *varAddresses);
};


#endif //FIRST_STEP_DATAREADERSERVER_H