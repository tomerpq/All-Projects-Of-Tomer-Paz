

#ifndef MINUS_H
#define MINUS_H

#include "BinaryExpression.h"
class Minus : public BinaryExpression{
public:
    Minus(Expression* left,Expression* right);
    virtual double calculate();
    ~Minus(){}
};

#endif //MINUS_H
