

#ifndef DIV_H
#define DIV_H

#include "BinaryExpression.h"
class Div : public BinaryExpression{
public:
    Div(Expression* left,Expression* right);
    virtual double calculate();
    ~Div(){}
};

#endif //DIV_H
