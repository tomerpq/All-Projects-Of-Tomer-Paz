#include "openServerCommand.h"

using namespace std;

openServerCommand::openServerCommand(std::map<std::string, double> *symbolTable,
                                     std::map<std::string, std::string> *varAddresses) : symbolTable(symbolTable),
                                                                                         varAddresses(varAddresses){
    data = new DataReaderServer();
}

int openServerCommand::execute(std::string order[], int index) {
    bool accepted = false;
    thread thread1(&DataReaderServer::execution, data, symbolTable, varAddresses, stoi(order[index+1]), &accepted);
    thread1.detach();
    while (!(accepted)) {
    }
    return 3;
}