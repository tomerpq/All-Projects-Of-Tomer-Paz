/* nim */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "main_aux.h"
#define maxSize 32 /* of the heaps */
/* ask how many heaps and size of each */
int askHeapsNumberAndSizes(int heaps[]){
	int N;/*heaps number */
	int i;/*for loop */
	int size;/*size for heap */
	printf("Enter the number of heaps:\n");
	scanf("%d",&N);
	checkN(N);
	printf("Enter the heap sizes:\n");
	for(i = 0; i < N; i++){
		scanf("%d",&size);
		if(!checkPos(size)){
			printf("Error: the size of heap %d should be positive.\n",i+1);
			exit(0);
		}
		heaps[i] = size;
	}
	return N;
}
/* checks if there's a winner now */
void checkIfWin(int heaps[],int n,int userTurn){
	int i;
	for(i = 0; i < n; i++){
		if(heaps[i] > 0)
			return;
	}
	if(!userTurn)
		printf("You win!\n");
	else
		printf("Computer wins!\n");
	exit(0);
}
/*the method that mains operates in user turn */
void userTurnMethod(int heaps[],int n){
	int noContinue = 1;/*for while loop until a valid input is entered */
	int IandR[2];/*index and number to remove */
	printf("Your turn: please enter the heap index and the number of removed objects.\n");
	while(noContinue){
		noContinue = 0;
		scanf("%d",&IandR[0]);
		scanf("%d",&IandR[1]);
		if(checkObjToRemoveBad(heaps,n,IandR[0],IandR[1])){
			printf("Error: Invalid input.\nPlease enter again the heap index and the number of removed objects.\n");
			noContinue = 1;
		}
		else{
			printf("You take %d objects from heap %d.\n",IandR[1],IandR[0]);
			heaps[IandR[0]-1] -= IandR[1];
		}
	}
}
/* the methos that main operates in computer turn */
void computerTurnMethod(int heaps[],int n){
	int nimsum = heaps[0];/*for nim sum calc*/
	int i;
	int iTag;/*i' */
	int take = 1;;/* how many computer removes */
	for(i = 1; i < n; i++)
		nimsum ^= heaps[i];
	if(nimsum != 0){
		for(iTag = 0; iTag < n; iTag++)
			if(heaps[iTag] > (heaps[iTag]^nimsum))
				break;
		heaps[iTag] = heaps[iTag]^nimsum;
		take = heaps[iTag] -(heaps[iTag]^nimsum);
		if(take < 0)
			take = -1*take;
		printf("Computer takes %d objects from heap %d.\n",take,iTag+1);
	}
	else{
		for(iTag = 0; iTag < n; iTag++)
			if(heaps[iTag] > 0)
				break;
		heaps[iTag] -= 1;
		take = 1;
		printf("Computer takes %d objects from heap %d.\n",take,iTag+1);
	}
}
