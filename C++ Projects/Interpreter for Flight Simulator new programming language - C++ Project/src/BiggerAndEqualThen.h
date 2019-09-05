
#ifndef BIGGERANDEQUALTHEN_H
#define BIGGERANDEQUALTHEN_H

#include "BinaryExpression.h"
class BiggerAndEqualThen : public BinaryExpression{
public:
    BiggerAndEqualThen(Expression* left,Expression* right);
    virtual double calculate();
    ~BiggerAndEqualThen(){}
};

#endif //BIGGERANDEQUALTHEN_H
