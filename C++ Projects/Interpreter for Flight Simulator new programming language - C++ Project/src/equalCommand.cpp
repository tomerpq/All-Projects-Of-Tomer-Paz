#include "equalCommand.h"

using namespace std;

equalCommand::equalCommand(std::map<std::string, double> *symbolTable,
        std::map<std::string, std::string> *varAddresses, Command* com)
        : varAddresses(varAddresses), symbolTable(symbolTable) {
    cc = dynamic_cast<connectCommand*>(com);
}

int equalCommand::execute(std::string *commands, int startIndex) {
    // if we need to bind with an address / another variable
    if (commands[startIndex+2] == "bind"){
        return bindCommand(commands, startIndex);
    }
    // if it's an expression
    else {
        return regularEqual(commands, startIndex);
    }
}

int equalCommand::bindCommand(std::string *commands, int startIndex) {
    string str = (commands[startIndex+3]);
    // in case that the first character is " we need to bind the variable with an addresse
    if (str[0] == '"') {
        //(*varAddresses).erase((*varAddresses)[commands[startIndex]]);
        (*varAddresses)[commands[startIndex + 3]] = commands[startIndex];
        (*symbolTable)[commands[startIndex]] = 0;
    } else { // in this case, the bind is with an another variable
        (*varAddresses)[commands[startIndex+3]] = commands[startIndex];
        (*varAddresses)[commands[startIndex]] = commands[startIndex+3];
        (*symbolTable)[commands[startIndex]] = (*symbolTable)[commands[startIndex+3]];
    }
    return 4;
}

int equalCommand::regularEqual(std::string *commands, int startIndex) {
    int addToIndex = 1;
    ShuntingYard* sh = new ShuntingYard(symbolTable);//shunting yard object with the current symbolTable
    Expression* exp = sh->getExpression(commands,startIndex+2,&addToIndex);//we get the expression from the shunting yard object that reads the expression from the array(and updates addToIndex to  how much we need to advance the array)
    double pass = exp->calculate();//gets us the value of the expression object.
    delete exp;
    delete sh;
    bool notbinded = false;
   // if we find the variable on the left size of map of binded variable, it means it is bind to an another element.
    if ((*varAddresses).count(commands[startIndex]) == 1) {
        notbinded = true;
        (*symbolTable)[(*varAddresses).at(commands[startIndex])] = pass;
    }

    (*symbolTable)[commands[startIndex]] = pass;
    // if variable is not bind to another variable, we need right now to check if it's bind to an address
    // if it is, we will ask for connect command to send data to simulator
    if (!notbinded) {
        for (auto itr = (*varAddresses).begin(); itr != (*varAddresses).end(); ++itr) {
            if (itr->second == commands[startIndex]) {
                cc->set(itr->first, pass);
            }
        }
    }
    return addToIndex+1;
}