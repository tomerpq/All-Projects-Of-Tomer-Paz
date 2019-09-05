using namespace std;
#include "BinaryExpression.h"
BinaryExpression::BinaryExpression(Expression *left, Expression *right) {
    m_left = left;
    m_right = right;
}
BinaryExpression::~BinaryExpression() {
    delete m_right;
    delete m_left;
}