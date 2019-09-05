/*Lexer module:read from file the script to pass to Parser module*/
#ifndef LEXER_H
#define LEXER_H

#include <string>
#include <iostream>
#include <fstream>
#include <stdexcept>

class Lexer{
private:
    std::string fileName;//to get the script from
    int num;//the length of the string array lexed
public:
    Lexer(std::string fileName);
    std::string* lex();
    int getNum();
    ~Lexer(){}
};

#endif //LEXER_H