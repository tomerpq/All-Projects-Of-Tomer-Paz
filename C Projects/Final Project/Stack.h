/*stack header. contains the struct NODE and STACK requiered by the EXBacktracking module and the function declaration*/

#ifndef STACK_H_
#define STACK_H_

typedef struct NODE
{
	int i;
	int j;
	double val;
	struct NODE *next;
}NODE;

typedef struct STACK
{
	int max;
	int node_cnt;
	NODE *top;
}STACK;

void stk_init( STACK *stk , int N);

void node_init(int i, int j, double val, NODE *n);
void push (NODE *n, STACK *s);
NODE *top(STACK *s);
void pop(STACK *s);
int is_empty(STACK *s);
#endif /* STACK_H_ */
