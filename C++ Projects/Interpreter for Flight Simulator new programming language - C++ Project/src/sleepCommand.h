
#ifndef SLEEPCOMMAND_H
#define SLEEPCOMMAND_H

#include <thread>
#include <chrono>
#include <iostream>
#include "Command.h"
class sleepCommand : public Command{
public:
    virtual int execute(std::string* commands,int startIndex);
    ~sleepCommand(){}
};

#endif //SLEEPCOMMAND_H
