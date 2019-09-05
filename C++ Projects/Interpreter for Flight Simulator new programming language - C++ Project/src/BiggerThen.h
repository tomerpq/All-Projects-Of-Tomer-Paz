

#ifndef BIGGERTHEN_H
#define BIGGERTHEN_H

#include "BinaryExpression.h"
class BiggerThen : public BinaryExpression{
public:
    BiggerThen(Expression* left,Expression* right);
    virtual double calculate();
    ~BiggerThen(){}
};

#endif //BIGGERTHEN_H
