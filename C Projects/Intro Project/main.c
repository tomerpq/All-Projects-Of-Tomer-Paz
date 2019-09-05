/* Main Module */
/* sodoku made of 9X9 squares */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "MainAux.h"


#define Length 9
#define Width 9
#define Dimension 2


int main(int argc,char *argv[]){
	unsigned int seed;
	int * lengths;
	lengths = (int *)malloc(Dimension * sizeof(int));
	lengths[0] = Length;
	lengths[1] = Width;
	if(argc < 2)/*no seed input*/
		srand(100);
	else{
		seed = (unsigned int)strtol(argv[1], NULL, 10);
		srand(seed);
	}
	play(lengths);/*play sudoku*/
	return 0;
}
