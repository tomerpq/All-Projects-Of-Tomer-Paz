/**exit command*/

#ifndef EXITCOMMAND_H
#define EXITCOMMAND_H

#include "Command.h"
class exitCommand : public Command{
public:
    virtual int execute(std::string* commands,int startIndex);
    ~exitCommand(){}
};

#endif //EXITCOMMAND_H
