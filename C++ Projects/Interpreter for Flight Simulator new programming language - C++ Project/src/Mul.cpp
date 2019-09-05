

#include "Mul.h"
Mul::Mul(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double Mul::calculate() {
    return m_left->calculate() * m_right->calculate();
}