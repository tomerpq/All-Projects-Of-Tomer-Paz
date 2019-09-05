
#ifndef CONDITIONPARSER_H
#define CONDITIONPARSER_H

#include "varCommand.h"
#include "printCommand.h"
#include "connectCommand.h"
#include "openServerCommand.h"
#include "ShuntingYard.h"
#include "exitCommand.h"
#include "sleepCommand.h"
/** Condition Parser parses the command with {} (while or if), checks the boolean
 * if its true:run one time the loop/else finish. (done if boolean), if we had while loop (more then if case)
 * we will have parameter of it true by the constructor; if it is while, we keep checking the boolean and iterate again if its true.**/
class conditionParser : public Command {
private:
    std::map<std::string,double>* m_symbolTable;//all the varibales with their values; 0 for default(without set)
    std::map<std::string,std::string>* m_varBind;//all the bound variables with their address in the simulator
    std::list<Command*> commandsList; //List of commands we need to execute in case condition is true
    bool m_isWhile;//true iff this object represents while loop, 0 - if
    Command* cc;

public:
    conditionParser(std::map<std::string,double>* symbolTable,std::map<std::string,std::string>* varBind,bool isWhile,Command* command);
    virtual int execute(std::string* commands,int startIndex);
    ~conditionParser(){}
};

#endif //CONDITIONPARSER_H