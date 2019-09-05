

#ifndef PLUS_H
#define PLUS_H

#include "BinaryExpression.h"
class Plus : public BinaryExpression{
public:
    Plus(Expression* left,Expression* right);
    virtual double calculate();
    ~Plus(){}
};

#endif //PLUS_H
