

#ifndef BINARYEXPRESSION_H
#define BINARYEXPRESSION_H

#include "Expression.h"

class BinaryExpression : public Expression{
protected:
    //expressions from both sides of the operator
    Expression* m_left;
    Expression* m_right;
public:
    BinaryExpression(Expression* left,Expression* right);
    virtual double calculate() = 0;
    virtual ~BinaryExpression();
};

#endif //BINARYEXPRESSION_H
