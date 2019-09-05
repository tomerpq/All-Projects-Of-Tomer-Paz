
#include "Div.h"
Div::Div(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double Div::calculate() {
    return m_left->calculate() / m_right->calculate();
}