/* Game Modulue */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "Game.h"

int abs1(int x);
int isLegal1(int ** game, int row, int col, int val);
int ** initalizationWithMake(int** solvedGame,int* lengths);
int* gamePlay(int** game,int** solvedGame,int* lengths,int* Do,int* validAndResolve);
int ** initalizationWithoutMake(int** solvedGame,int* lengths,int** game);
void printSodoku(int** game,int* lengths);
void printDashes(int widthSquare);
int checkSquare(int** game,int* Do,int i,int j);
void invalid();
void destroyGame(int** game);

/*initalize the game with number of fixed cells from solution(made randomly)*/
int ** initalizationWithMake(int** solvedGame,int* lengths){
	int num;
	int i,j;
	int randR,randC;/*for row col of the sudoku to fill rand*/
	int* values = calloc(lengths[0]*lengths[1],sizeof(int));
	int** game = malloc(lengths[1]*sizeof(int*));/*init 2d game*/
	for(i = 0; i< lengths[1]; ++i)
		game[i] = values + i*lengths[0];
	printf("Please enter the number of cells to fill [0-80]:\n");
	scanf("%d",&num);
	while(!(num >= 0 && num <= 80)){
		printf("Error: Invalid number of cells to fill (should be between 0 and 80)\n");
		scanf("%d",&num);
	}
	for(i = 0; i < lengths[0]; i++)
		for(j = 0; j < lengths[1]; j++)
			game[i][j] = 0;
	for(i = 0; i < num; i++){/* fill num squares*/
		while(1){
			randR = rand() % lengths[0];
			randC = rand() % lengths[1];
			if(game[randR][randC] == 0){
				game[randR][randC] = (-1)*solvedGame[randR][randC];
				break;
			}
		}
	}
	printSodoku(game,lengths);
	return game;
}
/*same as with make but doesn't use more memory */
int ** initalizationWithoutMake(int** solvedGame,int* lengths,int** game){
	int num;
	int i,j;
	int randR,randC;
	printf("Please enter the number of cells to fill [0-80]:\n");
	scanf("%d",&num);
	while(!(num >= 0 && num <= 80)){
		printf("Error: Invalid number of cells to fill (should be between 0 and 80)\n");
		scanf("%d",&num);
	}
	for(i = 0; i < lengths[0]; i++)
		for(j = 0; j < lengths[1]; j++)
			game[i][j] = 0;
	for(i = 0; i < num; i++){
		while(1){
			randR = rand() % lengths[0];
			randC = rand() % lengths[1];
			if(game[randR][randC] == 0){
				game[randR][randC] = (-1)*solvedGame[randR][randC];
				break;
			}
		}
	}
	printSodoku(game,lengths);
	return game;
}
/*returns validAndResolve array- index 0 of it is valid(1 iff we can use all methods,0 if we can only restart and exit), second is Resolve for validate-if 1
 * we need to update the solved game.
 * mainAux will check for restart because its not in this module*/
int* gamePlay(int** game,int** solvedGame,int* lengths,int* Do,int* validAndResolve){
	int i,j;
	int pass;/*iff we can insert = 1*/
	int solved = 0; /* 0 will be solved, add 1 for blank*/
	if(Do[0] == 0){/*set*/
		if(validAndResolve[0]){/*if valid*/
		if(game[Do[2]-1][Do[1]-1] < 0){/*checks fixed*/
			printf("Error: cell is fixed\n");
		}
		else{/*cell is not fixed*/
			if(Do[3] == 0){/*checks insert blank*/
				game[Do[2]-1][Do[1]-1] = 0;
				printSodoku(game,lengths);
			}
			else{/*not blank*/
				pass = isLegal1(game,Do[2] -1,Do[1] -1,Do[3]);
				if(!pass)/*can't insert*/
					printf("Error: value is invalid\n");
				else{/*insert*/
					game[Do[2]-1][Do[1]-1] = Do[3];
					printSodoku(game,lengths);
					for(i = 0; i < lengths[0]; i++){/*checks solved*/
						for(j = 0; j < lengths[1]; j++){
							if(game[i][j] == 0){
								solved ++;
							}
						}
					}
					if(solved == 0){/*checks solved 2 */
						printf("Puzzle solved successfully\n");
						validAndResolve[0] = 0;
						validAndResolve[1] = 0;
						return validAndResolve;
					}
				}
			}
		}
		validAndResolve[1] = 0;
		return validAndResolve;
		}
		else{/*not valid*/
			invalid();
			validAndResolve[1] = 0;
			return validAndResolve;
		}
	}
	else if(Do[0] == 1){/*hint*/
		if(validAndResolve[0]){/*if valid*/
			printf("Hint: set cell to %d\n",solvedGame[Do[2]-1][Do[1]-1]);
			validAndResolve[1] = 0;
			return validAndResolve;
		}
		else{/*not valid*/
			invalid();
			validAndResolve[1] = 0;
			return validAndResolve;
		}
	}
	else if(Do[0] == 3){/*exit*/
		free(Do);
		destroyGame(game);
		destroyGame(solvedGame);
		free(validAndResolve);
		free(lengths);
		printf("Exiting…\n");
		exit(0);
	}
	else if(Do[0] == 6){/*enter is typed*/
		validAndResolve[1] = 0;
		return validAndResolve;
	}
	else if(Do[0] == 5){/*invalid command*/
		invalid();
		validAndResolve[1] = 0;
		return validAndResolve;
	}
	/* only bugs from here, we check restart in mainAux*/
	return validAndResolve;
}
/*prints the current sodoku table*/
void printSodoku(int** game,int* lengths){
	int i,j;
	int squareSize = 3;
	for(i = 1; i <= lengths[0]; i++){
		if(i%3 == 1){
			printDashes(squareSize);
			printf("\n");
		}
		for(j = 1; j <= lengths[1]; j++){
			if(j == 1)
				printf("|");
			if(j != 1 && j % 3 == 1)
				printf(" |");
			if(game[i-1][j-1] < 0){
				printf(" .%d",(-1)*game[i-1][j-1]); /* -for permnant square else + for not permant*/
			}
			if(game[i-1][j-1] > 0){
				printf("  %d",game[i-1][j-1]);
			}
			if(game[i-1][j-1] == 0){/*space for zero-blank*/
				printf("   ");
			}
		}
		printf(" |\n");
	}
	printDashes(squareSize);
	printf("\n");
}
/*print dashes-helps the printSodoku method*/
void printDashes(int widthSquare){
	int i;
	for(i = 0; i < widthSquare; i++)
		printf("-----------"); /*11 dashes per square*/
	printf("-");
}
/* checks if Z in the square*/
int checkSquare(int** game,int* Do,int i,int j){
	int square;
	int k,z;
	for(k = i; k < k+3; k++){
		for(z = j; z < z+3; z++){
			if(k == Do[2]-1 && z == Do[1]-1)
				continue;
			square = game[k][z];
			if(square < 0)
				square = (-1)*square;
			if(square == Do[3])
				return 0;
		}
	}
	return 1;
}
/*invalid message*/
void invalid(){
	printf("Error: invalid command\n");
}
/*free game from mem*/
void destroyGame(int** game)
{
    free(*game);
    free(game);
}
/*gets a sudoku puzzle (game), a spesific cell (game[row][col]), and a value(val).
return 1 if val  can be seted in this cell, 0 otherwise*/
int isLegal1(int ** game, int row, int col, int val) {
	int k, m, mrow, mcol;

	/*check rows*/
	for (k = 0; k < 9; k++) {
		if (abs1(game[row][k]) == (abs1(val))) {
			return 0;
		}
	}
	/*check cols*/
	for (k = 0; k < 9; k++) {
		if (abs(game[k][col]) == (abs1(val))) {
			return 0;
		}
	}/*check box*/
	mcol = col - (col % 3);
	mrow = row - (row % 3);
	for (k = mrow; k < mrow + 3; k++) {
		for (m = mcol; m < mcol + 3; m++) {
			if (abs1(game[k][m]) == (abs1(val))) {

				return 0;
			}
		}
	}
	return 1;
}
/*return absolut value of x*/
int abs1(int x) {
	if (x == 0) {
		return 0;
	}
	if (x<0) {
		return((-1)*x);
	}
	else {
		return x;
	}
}
