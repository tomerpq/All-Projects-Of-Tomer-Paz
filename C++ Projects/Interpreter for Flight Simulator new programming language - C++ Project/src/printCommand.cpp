#include "printCommand.h"

using namespace std;

printCommand::printCommand(std::map<std::string, double> *symbolTable) : m_symbolTable(symbolTable) {

}

int printCommand::execute(std::string *commands, int startIndex) {
    int addToIndex = 0;
    string str = (commands[startIndex+1]);
    // in case that the first character is " we need to print the sentence
    if (str[0] == '"'){
        cout << str.substr(1, (commands[startIndex+1].length()-2)) << endl;
        return 2;
    }
    // otherwhile we need to print the value of expression
    else {
        int addToIndex = 1;
        ShuntingYard* sh = new ShuntingYard(m_symbolTable);//shunting yard object with the current symbolTable
        Expression* exp = sh->getExpression(commands,startIndex+1,&addToIndex);//we get the expression from the shunting yard object that reads the expression from the array(and updates addToIndex to  how much we need to advance the array)
        double pass = exp->calculate();//gets us the value of the expression object.
        delete exp;
        delete sh;
        cout << pass << endl;
        return addToIndex;
    }
}