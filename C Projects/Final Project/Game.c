/*Game Module:
 *game represented as 2d double array.cell in the 2d array :- number.x when:
 *x = 0 - normal cell
 *x = 1 - fixed cell
 *x = 2 - erroneous cell
 *number is the cell itself. 0 if blank
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Game.h"

/* */
int userMove(double** game,int m,int n,int* mode,int* markErrors,int* move,int* integerArguments,char* stringArguments){
	int N = m*n,offset;/*for calculations*/
	int error;/*to check if board is erroneous and upadte it when needed*/
	int size = N*N;/*all cells in the board*/
	double cell;/*for calculation*/
	FILE* file;/*some FILE pointer for checkings*/
	if(*move == 0){/*any other user input that doesn't match the other moves possible, or any other input error.*/
		printf("ERROR: invalid command\n");
		return 0;
	}
	else if(*move == 1){/*solve X - starts the move here - continues in mainAux. if possible will return 1, otherwise will return 0 after printing the invalid message*/
		file = fopen(stringArguments,"r");/* X == stringArguments */
		if(!file){/*case not possible - the file source is bad.*/
			printf("Error: File doesn't exist or cannot be opened\n");
			return 0;
		}
		/*else - continue in MainAux and update the mode. close the file here, open there again.*/
		*mode = 1;
		assert(fclose(file) == 0);
		return 1;
	}
	else if(*move == 2){/*edit [x] - starts the move here - continues in MainAux*/
		if(!stringArguments){/*in case there is no X*/
			*mode = 2;
			return 1;/*continue in mainAux with 9X9 empty board*/
		}/*else - there is a filePath:*/
		file = fopen(stringArguments,"r");/* X == stringArguments */
		if(!file){/*case not possible - the file source is bad.*/
			printf("Error: File doesn't exist or cannot be opened\n");
			return 0;
		}
		/*else - continue in MainAux and update the mode. close the file here (if exist), open there again(if exist).*/
		*mode = 2;
		assert(fclose(file) == 0);
		return 1;
	}
	else if(*move == 3){/*mark_errors X*/
		if(*mode != 1){/*if not in solve mode*/
			printf("ERROR: invalid command\n");
			return 0;
		}
		if(!integerArguments[0]){/*X is not 0 or 1]*/
			printf("Error: the value should be 0 or 1\n");
			return 0;
		}
		/*everything is ok:*/
		*markErrors = integerArguments[1];/*integerArguments[1] == X */
		return 1;
	}
	else if(*move == 4){/*print_board*/
		if(*mode == 0){/*avaliable not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		updateErroneous(game,m,n);
		printBoard(game,m,n,*mode,*markErrors);
		return 1;
	}
	else if(*move == 5){/*set X Y Z - start here and continue in MainAux*/
		if(*mode == 0){/*avaliable not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		if(!integerArguments[0]){/*X Y or Z are invalid*/
			printf("Error: value not in range 0-%d\n",N);
			return 0;
		}
		cell = game[integerArguments[2]][integerArguments[1]];
		offset = getOffset1(cell);
		if(offset == 1){/*the cell is fixed-no execute*/
			printf("Error: cell is fixed\n");
			return 0;
		}
		return 1;/*continue in mainAux*/
	}
	else if(*move == 6){/*validate - starts the move here - continues in mainAux. if possible will return 1, otherwise will return 0 after printing the invalid message*/
		if(*mode == 0){/*avaliable not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		error = updateErroneous(game,m,n);
		if(error){/*if board is erroneous*/
			printf("Error: board contains erroneous values\n");
			return 0;
		}
		/*we can try to solve it now:*/
		return 1;/*continues from here trying to solve with ILP the board in MainAux*/
	}
	else if(*move == 7){/*generate X Y - continues in MainAux*/
		if(*mode != 2){/*available only in edit mode*/
			printf("ERROR: invalid command\n");
			return 0;
		}
		if((!integerArguments[0]) || (integerArguments[1] > size) || (integerArguments[2] > size)){/*X or Y are invalid or they are valid and either one of them is bigger then the number of cells in the board.*/
			printf("Error: value not in range 0-%d\n",size);/*size is E*/
			return 0;
		}
		if(emptyCellsNumber(game,m,n) != size){/*if the board is not empty*/
			printf("Error: board is not empty\n");
			return 0;
		}
		return 1;
	}
	else if(*move == 8){/*undo - starts here and continues in MainAux for further checking if possible and if so - execute.*/
		if(*mode == 0){/*available not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		return 1;/*continues in mainAux*/
	}
	else if(*move == 9){/*redo - starts here and continues in MainAux for further checking if possible and if so - execute.*/
		if(*mode == 0){/*available not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		return 1;/*continues in mainAux*/
	}
	else if(*move == 10){/*save X - we start here and continue in MainAux*/
		if(*mode == 0){/*available not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		if(*mode == 2){/*if edit mode*/
			error = updateErroneous(game,m,n);
			if(error){/*if erroneous*/
				printf("Error: board contains erroneous values\n");
				return 0;
			}
		}
		return 1;/*continue checking\executing in mainAux*/
	}
	else if(*move == 11){/*hint X Y - we start here and continue in MainAux*/
		if(*mode != 1){/*available only in solve mode*/
			printf("ERROR: invalid command\n");
			return 0;
		}
		if(!integerArguments[0]){/*X or Y are invalid*/
			printf("Error: value not in range 1-%d\n",N);
			return 0;
		}
		error = updateErroneous(game,m,n);
		if(error){/*if erroneous*/
			printf("Error: board contains erroneous values\n");
			return 0;
		}
		cell = game[integerArguments[2]][integerArguments[1]];
		offset = getOffset1(cell);
		if(offset == 1){/*the cell is fixed-no execute*/
			printf("Error: cell is fixed\n");
			return 0;
		}
		if(((int)cell) != 0){/*cell isn't fixed and checking if it has value*/
			printf("Error: cell already contains a value\n");
			return 0;
		}
		return 1;/*continue in mainAux*/
	}
	else if(*move == 12){/*num_solutions - starts here, continue in MainAux*/
		if(*mode == 0){/*available not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		error = updateErroneous(game,m,n);
		if(error){/*if erroneous*/
			printf("Error: board contains erroneous values\n");
			return 0;
		}
		return 1;/*continues in mainAux*/
	}
	else if(*move == 13){/*autofill - starts here, continue in MainAux*/
		if(*mode != 1){/*available only in solve mode*/
			printf("ERROR: invalid command\n");
			return 0;
		}
		error = updateErroneous(game,m,n);
		if(error){/*if erroneous*/
			printf("Error: board contains erroneous values\n");
			return 0;
		}
		return 1;/*continue in mainAux*/
	}
	else if(*move == 14){/*reset - starts here, continue in MainAux*/
		if(*mode == 0){/*available not in init mode */
			printf("ERROR: invalid command\n");
			return 0;
		}
		return 1;/*continue in MainAux*/
	}
	else if(*move == 15){/*exit - continues in MainAux*/
		return 1;/*this command is always available - now will continue in MainAux*/
	}
	return 0;/*won't happen,but if happens that the parser didn't not rule out all the "bad" inputs we return 0.*/
}

/*checks if the cell is erroneous and update him as so if he is. returns 1 iff he was erroneous */
int checkAndUpdateCellToErroneous(double** game,int m,int n,int i,int j){
	int a,b,cell,ourCell = (int)game[i][j],N = m*n;
	for(a = 0; a < N; a++){/*checks row for same number*/
		if(a == j)/*we don't compare to the same cell*/
			continue;
		cell = (int)game[i][a];
		if(ourCell == cell){
			game[i][j] = ((double)ourCell) + 0.2;
			return 1;
		}
	}
	for(a = 0; a < N; a++){/*checks column for same number*/
		if(a == i)/*we don't compare to the same cell*/
			continue;
		cell = (int)game[a][j];
		if(ourCell == cell){
			game[i][j] = ((double)ourCell) + 0.2;
			return 1;
		}
	}
	/*checks in the block:*/
	/*goes forward in the rows:*/
	a = i;
	do{
		b = j;
		do{/*goes forwards in the columns for that line*/
			if(i == a && j == b){
				b++;
				continue;
			}
			cell = (int)game[a][b];
			if(ourCell == cell){
				game[i][j] = ((double)ourCell) + 0.2;
				return 1;
			}
			b++;
		}while(b%n != 0);
		b = j;
		while(1){/*goes backwards in the columns for that line*/
			if(i == a && j == b){
				if(b%n == 0)
					break;
				b--;
				continue;
			}
			cell = (int)game[a][b];
			if(ourCell == cell){
				game[i][j] = ((double)ourCell) + 0.2;
				return 1;
			}
			if(b%n == 0)
				break;
			b--;
		}
		a++;
	}while(a%m != 0);
	/*goes backwards in the rows:*/
	a = i;
	while(1){
		b = j;
		do{/*goes forwards in the columns for that line*/
			if(i == a && j == b){
				b++;
				continue;
			}
			cell = (int)game[a][b];
			if(ourCell == cell){
				game[i][j] = ((double)ourCell) + 0.2;
				return 1;
			}
			b++;
		}while(b%n != 0);
		b = j;
		while(1){/*goes backwards in the columns for that line*/
			if(i == a && j == b){
				if(b%n == 0)
					break;
				b--;
				continue;
			}
			cell = (int)game[a][b];
			if(ourCell == cell){
				game[i][j] = ((double)ourCell) + 0.2;
				return 1;
			}
			if(b%n == 0)
				break;
			b--;
		}
		if(a%m == 0)
			break;
		a--;
	}
	return 0;
}

/*update every cell that is not fixed to erroneous iff its erroneous */
int updateErroneous(double** game,int m,int n){
	int i,j,N = m*n,error = 0;
	for(i = 0; i < N; i++){
		for(j = 0; j < N; j++){
			if(getOffset1(game[i][j]) != 1 && ((int)game[i][j] != 0)){/*we don't check fixed or empty cells*/
				if(checkAndUpdateCellToErroneous(game,m,n,i,j)){/*update the cell if needed and update error to 1 if it the cell was updated and there wasn't errors yet*/
					if(!error){
						error = 1;
					}
				}
				else{
					game[i][j] = (double)((int)game[i][j]);
				}
			}
		}
	}
	return error;/*returns 1 iff there was errors*/
}

/*get the offset of double (after .)*/
int getOffset1(double x){
	int temp = (int) (x * 10);
	return temp % 10;
}

/*prints cell of the table as required in index <i,j> according to mode and markErrors.*/
void printCell(double** game,int i,int j,int mode,int markErrors){
	int cell = (int)game[i][j];
	int offset = getOffset1(game[i][j]);
	printf(" ");
	if(cell == 0){
		printf("  ");
	}
	else{
		printf("%2d",cell);
	}
	if(offset == 0){
		printf(" ");
	}
	else if(offset == 1){
		printf(".");
	}
	else if(offset == 2){
		if(mode == 2){
			printf("*");
		}
		else{
			if(markErrors == 1){
				printf("*");
			}
			else{
				printf(" ");
			}
		}
	}
}
/*print separator row with newLine in the end if required.*/
void printSap(int m,int n,int withNewLine){
	int i, N = m*n;
	for(i = 0; i < 4*N +m + 1 ; i++){
		printf("-");
	}
	if(withNewLine)/*choose newLine at the end according to the boolean withNewLine*/
		printf("\n");
}
/*prints the board with block size mXn. and the mode 0 for init 1 for solve 2 for edit, and depending on markErrors-to show them.*/
void printBoard(double** game,int m,int n,int mode,int markErrors){
	int i,j,z,w; /*z and w for the row (m*n traverse)*/
	for(i = 0; i < n; i++){
		printSap(m,n,1);
		for(j = 0; j < m; j++){/*one row*/
			for(z = 0; z < m; z++){
				printf("|");
				for(w = 0; w < n; w++){
					printCell(game,i*m +j,z*n +w,mode,markErrors);/*there's (m*n)*(m*n) cells and thats their order in the loops.*/
				}
			}
			printf("|\n");
		}
	}
	printSap(m,n,0);
	printf("\n");
}
/*returns the number of empty cells in the board*/
int emptyCellsNumber(double** game,int m,int n){
	int i,j,N = m*n,counter = 0;
	for(i = 0; i < N; i++){
		for(j = 0; j < N; j++){
			if(((int)game[i][j]) == 0)
				counter++;
		}
	}
	return counter;
}
/*gets the obvious value of the cell(in place i,j)-(if its the last missing in the row *or* column *or* block); return it(as integer) or 0 if it is not obvious.
 * this function will get non erroneous board, and cells that are empty.
 */
int obviousCell(double** game,int m,int n,int i,int j){
	int x,y,N,val;
	/*iterate over all the possible values. if there is only one possible val this is the last one
	* return this value. otherwise- return 0;*/
	y=0;
	N=m*n;
	for (x=1;x<N+1;x++){
		if(is_legal2(game,m,n,i,j,x)){
			y++;
			val=x;
		}
	}
	if (y==1){
		return val;
	}
	else{
		return 0;
	}
}
/*resets the board to empty board*/
void cleanBoard(double** game,int m,int n){
	int i,j,N = m*n;
	for(i = 0; i < N; i++){
		for(j = 0; j < N; j++){
			game[i][j] = 0.0;
		}
	}
}
/*fills the cell (in place i,j)- with random legal value if possible; if so-returns 1,else return 0. */
int fillLegalRandom(double** game,int m,int n,int i,int j){
	int N = m*n,z,randCell,success = 0;/*randCell for calculation, success when getting legal random value*/
	int* options = (int*)malloc(N*sizeof(int));/*in index i(>=0) will be 1 iff the number i+1 chosen (randomly) already.*/
	assert(options);
	for(z = 0; z < N; z++)
		options[z] = 0;
	for(z = 0; z < N; z++){/*we will iterate on all options for random number in range 1-N*/
		while(1){
			randCell = rand() % N;
			if(!options[randCell]){
				options[randCell] = 1;
				randCell++;
				break;
			}
		}
		game[i][j] = (double)randCell;
		if(!checkAndUpdateCellToErroneous(game,m,n,i,j)){
			success = 1;
			break;
		}
		else{
			game[i][j] = 0.0;
		}
	}
	free(options);
	return success;
}
/*cheking if val is legal in the (i,j) place*/
int is_legal2(double **board, int m, int n, int i, int j, double val){
	int block, r_c;
	block=is_legal_in_block(board, m, n, i, j, val);
	r_c=is_legal_r_c(board, m, n, i, j, val);
	if ((block)&&(r_c)){
		return 1;
	}
	else{
		return 0;
	}
}
