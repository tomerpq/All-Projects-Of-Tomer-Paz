#include <iostream>
#include "Command.h"
#include "ShuntingYard.h"

#ifndef PRINTCOMMAND_H
#define PRINTCOMMAND_H

/**the print command:*/
class printCommand : public Command {
private:
    std::map<std::string,double>* m_symbolTable;//all the varibales with their values; 0 for default(without set)
public:
    printCommand(std::map<std::string,double>* symbolTable);
    virtual int execute(std::string* order,int startIndex);
    ~printCommand(){}
};

#endif //PRINTCOMMAND_H