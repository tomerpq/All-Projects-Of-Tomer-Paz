/** main_aux */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*check if valid number of heaps */
void checkN(int n){
	if(n > 32 || n < 1){
			printf("Error: the number of heaps must be between 1 and 32.\n");
			exit(0);
		}
}
/*print the heaps and sizes in current turn */
void printHeaps(int heaps[],int n,int turn){
	int i;
	int j; /* j = i + 1 - heap repsentation */
	printf("In turn %d heap sizes are: ",turn);
	for(i = 0; i < n-1; i++){
		j = i + 1;
		printf("h%d=%d ",j,heaps[i]);
	}
	printf("h%d=%d.\n",n,heaps[n-1]);
}
/*checks if input of heap index and how many to remove is not valid */
int checkObjToRemoveBad(int heaps[],int n,int index,int toRemove){
	if(index >= 1 && index <= n && (heaps[index-1] - toRemove >= 0) && toRemove > 0)
		return 0;
	else
		return 1;
}
/*cheks if number is positive */
int checkPos(int c){
	if(c > 0)
		return 1;
	else
		return 0;
}
