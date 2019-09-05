#include "varCommand.h"
using namespace std;

varCommand::varCommand(std::map<std::string, double> *symbolTable, std::map<std::string, std::string> *varAddresses,
                       Command *cc) : varAddresses(varAddresses), symbolTable(symbolTable), com(com) {
    equal = new equalCommand(symbolTable, varAddresses, com);
}

// This function check if there is an equal after variable, and execute the adequat function.
int varCommand::execute(std::string *commands, int startIndex) {
    if (((commands)[startIndex+2]) == "=") {
        return executeWithEqual(commands, startIndex);
    }
    return executeWithoutEqual(commands, startIndex);
}

// This function just add a new variable into symbol Table and gives it 0 value
// Returns 2 because it used only var command and variable name
int varCommand::executeWithoutEqual(std::string *commands, int startIndex) {
    (*symbolTable)[(commands)[startIndex+1]] = 0;
    return 2;
}

// This function is called in case we have equal after variable creation. We need in that case to call
// for the equal command which will give the symbol table the proper value of the variable.
int varCommand::executeWithEqual(std::string *commands, int startIndex) {
    return (equal->execute(commands, (startIndex+1)) + 1);
}