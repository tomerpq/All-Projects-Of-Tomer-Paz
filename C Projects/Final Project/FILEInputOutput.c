/*File module: this module reads sudoku from appropriate file to an 2d array and
 * writes sudoku to files too(from 2d array).
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "FILEInputOutput.h"

/*get the offset of a double (after .)*/
int getOffset2(double x){
	int temp = (int) (x * 10);
	return temp % 10;
}

/*saves the sudoku from the 2d array to the file in format: m space n -and then newline with each of the
 * sudoku rows. dot for fixed cell(or everytime in edit mode) and 0 for empty. */
void saveToFile(char* fileName,double** table,int m,int n,int mode){
	int i,j,N = m*n,cell,offset;/*first for loops, and others for getting the cell value from the 2d array*/
	char space = ' ';
	char newLine = '\n';
	char dot = '.';
	FILE* fout = fopen(fileName,"w");
	assert(fout);
	assert(fprintf(fout,"%d%c%d",m,space,n) >= 0);
	for(i = 0; i < N; i++){/*write lines*/
		assert(fprintf(fout,"%c",newLine) >= 0);
		cell = (int)table[i][0];/*write first value */
		offset = getOffset2(table[i][0]);
		assert(fprintf(fout,"%d",cell) >= 0);
		if(offset == 1 || (mode == 2 && cell != 0))/*saving all cells as fixed in edit mode, or if its fixed in other mode*/
			assert(fprintf(fout,"%c",dot) >= 0);
		for(j = 1; j < N; j++){/*write next values*/
			assert(fprintf(fout,"%c",space) >= 0);
			cell = (int)table[i][j];/*column depends on j(>=1), from now. */
			offset = getOffset2(table[i][j]);
			assert(fprintf(fout,"%d",cell) >= 0);
			if(offset == 1 || (mode == 2 && cell != 0))
				assert(fprintf(fout,"%c",dot) >= 0);
		}
	}
	assert(fclose(fout) == 0);
}

/*loads the sudoku from the file to the 2d array, same as the format of the save but opposite-load, but here there could be
 * "bad" spaces (more then one between each numbers, one line for two lines of the board, etc.
 * array returned: arr[0] = m, arr[1] = n*/
int* loadFromFileMN(char* fileName){
	int input, *arr;/*first for loops, input for the current read from the file, checkDot for boolean if its fixed cell. arr as explained. */
	arr = (int*)malloc(sizeof(int)*2);
	assert(arr);
	FILE* fin = fopen(fileName,"r");
	assert(fin);
	assert(fscanf(fin,"%d",&input) != EOF);
	arr[0] = input;
	assert(fscanf(fin,"%d",&input) != EOF);
	arr[1] = input;
	assert(fclose(fin) == 0);
	return arr;
}
/*this continues loadFromFileMN:*/
void loadFromFile(char* fileName,double** table,int mode,int N){
	int i,j,input,checkDot = 0;
	char dot;
	FILE* fin = fopen(fileName,"r");
	assert(fin);
	assert(fscanf(fin,"%d",&input) != EOF);
	assert(fscanf(fin,"%d",&input) != EOF);
	for(i = 0; i < N; i++){/*filling the board with the values from the file*/
		for(j = 0; j < N; j++){
			assert(fscanf(fin,"%d",&input) != EOF);
			dot = fgetc(fin);
			if(dot == '.')
				checkDot = 1;
			if(mode != 2 && checkDot){/*boolean for fixed cell,loading fixed cells not in edit mode. */
				checkDot = 0;
				table[i][j] = ((double)input) + 0.1;
			}
			else{
				table[i][j] = (double)input;
			}
		}
	}
	assert(fclose(fin) == 0);
}
/*makes new empty board, with empty cells. */
double** makeTable(int N){
	int i,j;
	double** board = (double**)malloc(sizeof(double*)*N);
	assert(board);
	for(i = 0; i < N; i++){
		board[i] = (double*)malloc(sizeof(double)*N);
		assert(board[i]);
	}
	for(i = 0; i < N; i++){
		for(j = 0; j < N; j++){
			board[i][j] = 0.0;
		}
	}
	return board;
}
/*to be used in mainAux-copys table values to another*/
void copyTable(double** toTable,double** fromTable,int N){
	int i,j;
	for(i = 0; i < N; i++)
		for(j = 0; j < N; j++)
			toTable[i][j] = fromTable[i][j];
}
