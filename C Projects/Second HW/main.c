/** main */
#define maxLength 32
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sp_nim.h"
#include "main_aux.h"
#include "SPBufferset.h"
int main(){
	int turnush = 1;/*number of turn*/
	int heaps[maxLength];/*array of heaps and objects within each */
	int N;/* heaps number */
	int userTurn = 1;/*  1 for userturn and 0 for computerturn */
	SP_BUFF_SET();
	N = askHeapsNumberAndSizes(heaps);
	while(1){
		checkIfWin(heaps,N,userTurn);
		printHeaps(heaps,N,turnush);
		if(userTurn){
			userTurnMethod(heaps,N);
			userTurn = 0;
		}
		else{
			computerTurnMethod(heaps,N);
			userTurn = 1;
		}
		turnush++;
	}
	return 0;
}
