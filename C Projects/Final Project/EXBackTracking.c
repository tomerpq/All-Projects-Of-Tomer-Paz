/*this module return the number of solutions for puzzle by using exbacktracking algorithm */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "Stack.h"
#include "EXBackTracking.h"
#include <assert.h>

/*check if val is exist in the block contains (i, j) cell in the board*/
int is_legal_in_block(double **board, int m, int n, int i, int j, double val){
	int k, l, st1, st2, tmp_val;
	k=(int)i/m;
	l=(int)j/n;
	/*define the most upper-left of the block contains (i,j) cell
	 * st1- index of row, st2=index of col */
	st1=k*m;
	st2=l*n;
	/*iterate over all the cell in the block*/
	for(k=st2; k<st2+n; k++){
		for (l=st1; l<st1+m;l++){
			tmp_val=(int)((board[l][k]*10)/10);/*extract the int  value in the cell*/
			if (tmp_val==((int)val)){			/*this value is already in the block */
				return 0;						/*val is not legal in (i,j)*/
			}
		}
	}
	return 1; 						/*value hasn't found-> val is legal in the box*/
}

/*check if val is exist in the row or col contains (i,j) cell in the board*/
int is_legal_r_c(double **board, int m, int n, int i, int j, double val){
	int size, k, tmp_val;
	size=m*n;    /*size=N*/
	/*check row*/
	for (k=0; k<size; k++){
		tmp_val=(int)((board[i][k]*10)/10);/*extract the int  value in the cell*/
		if (tmp_val==((int)val)){			/*this value is already in the row */
				return 0;
			}
	}
	/*check col*/
	/*printf(" to chek :%d\n",(int)board[3][0]);*/
	for (k=0; k<size; k++){
		tmp_val=(int)((board[k][j]*10)/10);/*extract the int  value in the cell*/
		if (tmp_val==((int)val)){			/*this value is already in the col */
					return 0;
		}
	}
	return 1;								/*value hasn't found nor in row neither in row*/
	}

/*cheking if val is legal in the (i,j) place*/
int is_legal(double **board, int m, int n, int i, int j, double val){
	int block, r_c;
	block=is_legal_in_block(board, m, n, i, j, val);
	r_c=is_legal_r_c(board, m, n, i, j, val);
	if ((block)&&(r_c)){
		return 1;
	}else{
		return 0;
	}
}
/*return the number of optional solution for the given sudoku board, using exbacktracking algorithm*/

int EXBacktracking( double ** board, int m ,int n){
	int cnt, i,j,N;
	double val;
	STACK *stack;
	NODE *tmp_node, *new_node, *old_node;
	cnt=0;
	i=0;
	j=0;
	val=1.0;
	N=m*n;
	stack=malloc(sizeof(STACK));
	assert(stack!=NULL);
	stk_init(stack,N*N);
	tmp_node=malloc(sizeof(NODE));
	assert(tmp_node!=NULL);
	while((0<=j)&&(j<N)){			/*start at the very first cell and progress rightward*/
		if (board[i][j]==0.0){		/*if the cell is empty - try to fill it*/
			while(((int)val<=N)&&!(is_legal(board, m, n,i, j, val))){         /*try to find legal value for this cell
												if fail, increment val*/
				val++;
			}
			if (((int)val)>N){							/*there is no legal value, try to go back*/
				if(!is_empty(stack)){						/*pop the last element and go back*/
					new_node=top(stack);
					i=new_node->i;
					j=new_node->j;
					val=(new_node->val)+1;
					board[i][j]=0.0;
					pop(stack);
				}else{
					j=-1;				/*mark that we arrived to the start again*/
				}
			}else{						/*there is legal value- set it*/
				board[i][j]=val;
				node_init(i,j,val, tmp_node);		/*update STACK*/
				push(tmp_node, stack);			/*continue to move forward. go to new line if necessary*/
				i++;
				val=1.0;				/*restart the val parameter*/
				if (i==N){
					i=0;
					j++;
				}
			}
		}else{							/*cell isnt empty*/
			i++;						/*continue to move forward. go to new line if necessary*/
			if (i==N){
				i=0;
				j++;
			}
		}if(j==N){						/*we have traveresed all the board and  have come to the end */
			cnt++;						/*this is legal solutionn. increment the number of solutions*/
			if(!is_empty(stack)){				/*try to go back and find out another solution*/	
				new_node=top(stack);
				i=new_node->i;
				j=new_node->j;
				board[i][j]=0.0;
				pop(stack);
				if(!is_empty(stack)){			/*reset the last cell, and try to increment the val and set it again*/
					old_node=top(stack); 
					i=old_node->i;
					j=old_node->j;
					board[i][j]=0.0;
					val=old_node->val+1;
				}else{					/*there is only one cell to change, so there is no options*/
					j=-1;
				}
			
			}else{						/*stack is empty. we finished all the options*/
				j=-1;
				}
			}
		}
		free(tmp_node);
		free(stack);
		return cnt;						/*return the number of options for solution*/		
	
	}
