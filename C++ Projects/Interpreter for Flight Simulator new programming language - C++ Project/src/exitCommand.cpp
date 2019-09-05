
#include "exitCommand.h"
int exitCommand::execute(std::string *commands, int startIndex) {
    if(commands){
        delete[] commands;
    }
    exit(777);
    return 0;
}