

#ifndef NOTEQUALTHEN_H
#define NOTEQUALTHEN_H

#include "BinaryExpression.h"
class NotEqualThen : public BinaryExpression{
public:
    NotEqualThen(Expression* left,Expression* right);
    virtual double calculate();
    ~NotEqualThen(){}
};

#endif //NOTEQUALTHEN_H
