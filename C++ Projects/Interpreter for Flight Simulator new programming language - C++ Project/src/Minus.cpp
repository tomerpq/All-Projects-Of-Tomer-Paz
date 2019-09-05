

#include "Minus.h"
Minus::Minus(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double Minus::calculate() {
    return m_left->calculate() - m_right->calculate();
}