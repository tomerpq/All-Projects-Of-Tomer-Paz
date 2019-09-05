
#include "BiggerThen.h"
BiggerThen::BiggerThen(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double BiggerThen::calculate() {
    if(m_left->calculate() > m_right->calculate()){
        return 1.0;
    }
    return 0.0;
}