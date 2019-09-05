
#include "BiggerAndEqualThen.h"
BiggerAndEqualThen::BiggerAndEqualThen(Expression *left, Expression *right) : BinaryExpression(left,right) {

}
double BiggerAndEqualThen::calculate() {
    if(m_left->calculate() >= m_right->calculate()){
        return 1.0;
    }
    return 0.0;
}