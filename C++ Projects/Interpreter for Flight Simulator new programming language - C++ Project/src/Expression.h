
#ifndef EXPRESSION_H
#define EXPRESSION_H

#include <string>

/** interface for calculate command of long math calculation*/
class Expression{

public:
    virtual double calculate() = 0;
    virtual ~Expression(){}
};

#endif //EXPRESSION_H
