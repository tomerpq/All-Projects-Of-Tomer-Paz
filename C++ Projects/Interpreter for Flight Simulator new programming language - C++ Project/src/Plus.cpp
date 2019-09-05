
#include "Plus.h"

Plus::Plus(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double Plus::calculate() {
    return m_left->calculate() + m_right->calculate();
}