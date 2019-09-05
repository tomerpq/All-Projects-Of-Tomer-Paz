using namespace std;
#include "conditionParser.h"
conditionParser::conditionParser(std::map<std::string, double> *symbolTable,
                                 std::map<std::string, std::string> *varBind, bool isWhile,Command* command) {
    m_symbolTable = symbolTable;
    m_isWhile = isWhile;
    m_varBind = varBind;
    cc = command;
}
int conditionParser::execute(string *commands, int startIndex) {
    int addToIndex = 1;
    startIndex ++;//advance to the boolean expression
    int constBooleanStartIndex = startIndex;//place where boolean starts
    int dummyAddToIndex = addToIndex;
    ShuntingYard* sh = new ShuntingYard(m_symbolTable);//shunting yard object with the current symbolTable
    Expression* exp = sh->getExpression(commands,startIndex,&addToIndex);//we get the expression from the shunting yard object that reads the expression from the array(and updates addToIndex to  how much we need to advance the array)
    double pass = exp->calculate();//gets us the value of the expression object.
    startIndex += addToIndex;//gets startIndex one after '{'
    addToIndex ++;//represnting number of addes to get one after '{'
    int firstStartIndex = startIndex;//for while loop
    if(pass == 1.0){
        /*making list of commands until '}'*/
        //making commands and executing once. will be executed more if the boolean in while keep equaling true.
        string current = commands[startIndex];

        //first iteration:
        while(current.compare("}") != 0){
            Command* c;
            if(current.compare("openDataServer") == 0){
                c = new openServerCommand(m_symbolTable,m_varBind);
            }
            else if(current.compare("connect") == 0){
                c = new connectCommand();
            }
            else if(current.compare("var") == 0){
                c = new varCommand(m_symbolTable, m_varBind, cc);
            }
            else if(current.compare("exit") == 0){
                c = new exitCommand();
            }
            else if(current.compare("if") == 0){
                c = new conditionParser(m_symbolTable,m_varBind,false,cc);
            }
            else if(current.compare("while") == 0){
                c = new conditionParser(m_symbolTable,m_varBind,true,cc);
            }
            else if(current.compare("sleep") == 0){
                c = new sleepCommand();
            }
            else if(current.compare("print") == 0){
                c = new printCommand(m_symbolTable);
            }
            else{//if its = command or invalid command
                if (commands[startIndex +1] == "="){
                    c = new equalCommand(m_symbolTable, m_varBind, cc);
                } // it's invalid command
                else if(startIndex +1 >= commands->size()){
                    throw invalid_argument("not a valid command inside the loop!");
                }
            }
            commandsList.push_back(c);
            int addTemp = c->execute(commands,startIndex);//performing the first iteration-at least a "if" statement.
            startIndex += addTemp;
            addToIndex += addTemp;
            current = commands[startIndex];
        }
        addToIndex ++;//getting after '}'
        if(m_isWhile){
            exp = sh->getExpression(commands,constBooleanStartIndex,&dummyAddToIndex);
            pass = exp->calculate();
            while(pass == 1.0){//check if boolen is true for loop
                startIndex = firstStartIndex;
                for(list<Command*>::iterator it = commandsList.begin(); it != commandsList.end(); ++it){
                    startIndex += (*it)->execute(commands,startIndex);
                }
                exp = sh->getExpression(commands,constBooleanStartIndex,&dummyAddToIndex);
                pass = exp->calculate();
            }
        }
    }
    else{//lets find the "}" without executing anything in the while/if
        string current = commands[startIndex];
        int extraOpenings = 0;
        while(true){//searching for } that equals to { opened
            if(current.compare("}") == 0){
                if(extraOpenings == 0)
                    break;
                else
                    extraOpenings --;
            }
            if(current.compare("{") == 0){
                extraOpenings ++;
            }
            startIndex ++;
            addToIndex ++;
            current = commands[startIndex];
        }
        addToIndex ++;//getting after '}'
    }
    delete exp;
    delete sh;\
    commandsList.clear();
    return addToIndex;//return to add to -one after '}'
}