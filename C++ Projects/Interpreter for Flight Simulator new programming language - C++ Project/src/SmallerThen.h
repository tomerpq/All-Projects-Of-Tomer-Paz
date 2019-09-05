#ifndef SMALLTHEN_H
#define SMALLTHEN_H

#include "BinaryExpression.h"

class SmallerThen : public BinaryExpression{
public:
    SmallerThen(Expression* left,Expression* right);
    virtual double calculate();
    ~SmallerThen(){}
};

#endif //SMALLTHEN_H
