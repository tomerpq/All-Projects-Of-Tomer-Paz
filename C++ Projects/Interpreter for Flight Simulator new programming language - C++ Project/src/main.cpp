#include "Parser.h"
/** running the Even Derech 1 project*/
int main(int argc, char* argv[]) {
    Lexer* lex = new Lexer(argv[1]);
    Parser* parser = new Parser(lex);
    parser->parse();
    delete lex;
    delete parser;
    return 0;
}