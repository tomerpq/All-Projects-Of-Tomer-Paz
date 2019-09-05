

#ifndef EQUALTHEN_H
#define EQUALTHEN_H

#include "BinaryExpression.h"
class EqualThen : public BinaryExpression{
public:
    EqualThen(Expression* left,Expression* right);
    virtual double calculate();
    ~EqualThen(){}
};

#endif //EQUALTHEN_H
