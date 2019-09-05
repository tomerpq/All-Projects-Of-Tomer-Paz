/*Solver Module*/

/*solverDet and SolverRand is for the user to use without seeing the algorithm behind
 * the algorithm behind will be in Rec methods*/
/*lengths[0] is the #rows , length[1] is the #columns*/
/*
 * solver method:
 * input is game, we don't change it, we return solved 2d array of sodoku
 * from the state of the game. if there is no solution-return NULL!!
 *NULL for validate */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "Solver.h"

/*return absolut value of x*/
int abs(int x) {
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
/*gets a sudoku puzzle and generate a clone of it.
if this puzlle can be solved the detrministic solution is returned.
NULL otherwise*/

int ** solverDetWithMake(int **game,int* lengths){
	int i,j;
	int* values = calloc(lengths[0] * lengths[1], sizeof(int));
	int** cloneGame = malloc(9 * sizeof(int*));
	for (i = 0; i < 9; ++i)
		cloneGame[i] = values + i * 9;
	for(i = 0; i < lengths[0]; i++){
		for(j = 0; j < lengths[1]; j++){
			if(game[i][j] < 0){
				cloneGame[i][j] = (-1)*game[i][j];
			}
			else{
				cloneGame[i][j] = game[i][j];
		}
	}
	}
	if (DetSolver(cloneGame, 0, 0)) {
		return cloneGame;
	}
	return NULL;
}
/*same as with make but doesn't use more memory  */
int ** solverDetWithoutMake(int **game,int* lengths,int** cloneGame){
	int i,j;
	for(i = 0; i < lengths[0]; i++)
		for(j = 0; j < lengths[1]; j++)
			cloneGame[i][j] = 0;
	for(i = 0; i < lengths[0]; i++){
		for(j = 0; j < lengths[1]; j++){
			if(game[i][j] < 0){
				cloneGame[i][j] = (-1)*game[i][j];
			}
			else{
				cloneGame[i][j] = game[i][j];
		}
	}
	}
	if (DetSolver(cloneGame, 0, 0)) {
		return cloneGame;
	}
	return NULL;
}
/* generate puzzle
 * makes new solved board,the output will help the initalization of the sodoku game
  */
int ** solverRandWithMake(int* lengths) {
	int i;
	int* values = calloc(lengths[0] * lengths[1], sizeof(int));
	int** game = malloc(9 * sizeof(int*));
	for (i = 0; i < 9; ++i){
		game[i] = values + i * 9;
	}
	if (RandSolve(game, 0, 0)) {
		return game;
	}return NULL;


}
/* same as with make but doesn't use more memory */
int ** solverRandWithoutMake(int* lengths,int** game) {
	int i,j;
	for(i = 0; i < lengths[0]; i++)
		for(j = 0; j < lengths[1]; j++)
			game[i][j] = 0;
	if (RandSolve(game, 0, 0)) {
		return game;
	}return NULL;
}
/*this function gets a sudoku game  and solve it by determinstic backtrcking
if there is a legal solution the 1 is returned. after return the puzzle contain in every cell
a legal value.
if there is no legal solution, 0 is returned and the puzzle is equals to
the puzzle situation before calling DetdSolver.
*/
int DetSolver(int **game, int i, int j) {
	int val;
	if (j == 9) {
		j = 0;
		if (++i == 9) {
			return 1;
		}

	}
	if (game[i][j] != 0) {
		return DetSolver(game, i, j + 1);
	}
	for (val = 1; val < 10; ++val) {
		if (isLegal(game, i, j, val)) {
			game[i][j] = val;
			if (DetSolver(game, i, j + 1))
				return 1;
		}
	}

	game[i][j] = 0;
	return 0;
}

/*gets a sudoku puzzle (game), a spesific cell (game[row][col]), and a value(val).
return 1 if val  can be seted in this cell, 0 otherwise*/
int isLegal(int ** game, int row, int col, int val) {
	int k, m, mrow, mcol;

	/*check rows*/
	for (k = 0; k < 9; k++) {
		if (abs(game[row][k]) == (abs(val))) {
			return 0;
		}
	}
	/*check cols*/
	for (k = 0; k < 9; k++) {
		if (abs(game[k][col]) == (abs(val))) {
			return 0;
		}
	}/*check box*/
	mcol = col - (col % 3);
	mrow = row - (row % 3);
	for (k = mrow; k < mrow + 3; k++) {
		for (m = mcol; m < mcol + 3; m++) {
			if (abs(game[k][m]) == (abs(val))) {

				return 0;
			}
		}
	}
	return 1;
}

/*return 1 iff val is contained by the given arry*/

int wasExclude(int *arr, int val, int len) {
	int i;
	for (i = 0; i < len; i++) {
		if (arr[i] == val) {
			return 1;
		}
	}return 0;
}
/*this function gets a sudoku game with and solve it by randomized backtrcking
if there is a legal solution the 1 is returned. after return the puzzle contain in every cell 
a legal value.
if ther is no legal solution, 0 is returned and the puzzle is equals to
the puzzle situation before calling RandSolve.
*/

int RandSolve(int ** game, int i, int j) {
	int *PreOptVals;
	int *OptVals;
	int l, n, cnt,k, randVal;
	int ExcludeVals[]={0,0,0,0,0,0,0,0,0};
	if (j == 9) {
		j = 0;
		if (++i == 9) {
			return 1;
		}
	}
	if (game[i][j] != 0) {
		return RandSolve(game, i, j + 1);
	}
	PreOptVals = calloc(9, sizeof(int));
	n = 0;
	for (l = 1; l<10; l++) {
		if (isLegal(game, i, j, l)) {
			PreOptVals[n] = l;
			n++;
		}
	}
	OptVals = malloc(n * sizeof(int));
	for ( k = 0; k<n; k++) {
		OptVals[k] = PreOptVals[k];
	}
       
	for (cnt = 0; cnt<n; cnt++) {
		randVal = OptVals[rand() % n];
		while (wasExclude(ExcludeVals, randVal, n)) {
			randVal = OptVals[rand() % n];
		}
		if (isLegal(game, i, j, randVal)) {
			game[i][j] = randVal;
			if (RandSolve(game, i, j + 1)) {
				free(PreOptVals);
				free(OptVals);
				return 1;
			}
		}
		ExcludeVals[cnt] = randVal;

	}
	game[i][j] = 0;
	free(PreOptVals);
	free(OptVals);
	return 0;
}


