/*
 *this module manage the STACK sturct used by exback function. the STACK strocture implements a stack 
 *which holds a NODE struct. the NODE stimulets a cell in a puzzle while the i,j values represent the cell and val it's value.
 *the next attribute point to the next NODE in the SATCK
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Stack.h"
#include <assert.h>

/*gets a pointer to NODE and assign its attributes*/

void node_init(int i, int j, double val, NODE *n){
	n->i=i;
	n->j=j;
	n->val=val;
	n->next=NULL;

}
/*gets a pointer to STACK and initiate it*/

void stk_init( STACK *stk , int N){
	stk->max =N*N;
	stk->node_cnt = 0;
	stk->top = NULL;
}
/*push an exist to stack*/
void push (NODE *n, STACK *s){
	NODE *p=NULL;
	p=malloc(sizeof(NODE));			/*allocate new node*/
	if(p==NULL){
		printf("malloc fail while push\n");
	}
	node_init(n->i,n->j,n->val, p);         /*copy the values of the exist node to the new node*/
	p->next=s->top;				/*push*/
	s->top=p;
	s->node_cnt++;				/*increment stack counter*/	
}
/*return the top node in the stack*/
NODE *top(STACK *s){
	return s->top;
}
/*pop the top of the stack and free it*/
void pop(STACK *s){
	NODE *n=NULL;
	n=s->top;
	s->top=s->top->next;
	s->node_cnt--;                          /*decrease stack counter*/
	free(n);


}
/*return 1 iff stack is empty*/
int is_empty(STACK *s){
	return (s->node_cnt==0); 
}
