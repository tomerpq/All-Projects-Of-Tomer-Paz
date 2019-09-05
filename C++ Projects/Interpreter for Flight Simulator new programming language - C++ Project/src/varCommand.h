
#ifndef FIRST_STEP_VARCOMMAND_H
#define FIRST_STEP_VARCOMMAND_H

#include <iostream>
#include "Command.h"
#include "connectCommand.h"
#include "equalCommand.h"

/** For command var we call to this class **/
/** This class will create a new hash option in the map symbolTable from parser. From string it will gate the variable
 * name that user ask for, and then will put in there value 0. The equal command will then bind the value with his
 * address in simulator **/

class varCommand : public Command {
    std::map<std::string, double> *symbolTable;
    std::map<std::string,std::string> *varAddresses;
    equalCommand* equal; // Equal command we will call if we meet an equal
    Command* com;

    int executeWithEqual(std::string* commands,int startIndex); // Execution with equal
    int executeWithoutEqual(std::string* commands,int startIndex); //Execution without equal

public:
    varCommand(std::map<std::string, double> *symbolTable, std::map<std::string,std::string> *varAddresses,
            Command* com);
    virtual int execute(std::string* commands,int startIndex);
    virtual ~varCommand(){
        delete equal;
    }
};


#endif //FIRST_STEP_VARCOMMAND_H
