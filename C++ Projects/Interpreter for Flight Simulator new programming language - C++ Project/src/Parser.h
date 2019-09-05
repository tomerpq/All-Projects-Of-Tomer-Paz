/*the parser module that getes the lexed script and parses it to
 * commands to be sent to execute(if its a valid command):*/

#ifndef Parser_H
#define Parser_H

#include "Lexer.h"
#include <mutex>
#include <map>
#include "conditionParser.h"
class Parser{
private:
    int arraySize;//the size of array from the lexer
    int index = 0;//start reading from here
    std::mutex* m;
    std::string* commands;//we get from lexer
    std::map<std::string,double> symbolTable;//all the varibales with their values; 0 for default(without set)
    std::map<std::string,std::string> varBind;//all the bound variables with their address in the simulator
    std::map<std::string,Command*> StrToCommand;//map of all commands avaliable from their string to actual object.
public:
    Parser(Lexer* lexer);
    ~Parser();
    void parse();
    std::map<std::string,std::string> getVarBind();
    std::map<std::string,double> getSymbolTable();
};


#endif //Parser_H