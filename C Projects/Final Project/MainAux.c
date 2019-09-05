/*MainAux module that combines all the modules. has the play function that activates the game.
 *this module is also responsible for memory arrangments(free,malloc),and has the 4 main parameters that is "ruling" the
 *whole game. */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <time.h>
#include "Lists.h"
#include "FILEInputOutput.h"
#include "Game.h"
#include "Parser.h"
#include "INFO.h"
#include "ILPSolver.h"
#include "EXBackTracking.h"
#include "MainAux.h"


int gameMode = 0; /*0 for Init(default), 1 for Solve, 2 for Edit*/
int markErrors = 1; /*erroneous values show iff 1 and thats default */
double** originalBoard;/*the original game board loaded from some file */
LIST* list;/*a list object that we will use for undo\redo */

/* */
void play(int defaultRow,int defaultColumn){
	int i,j,z,m = 0,n = 0,N = m*n;/*m and n are the block sizes of the game. at first 0 means we don't have the original board loaded from file yet.*/
	int error;/*to check if there was errors after checkingErreneous values*/
	int cell,cell2;/*for calculations*/
	int numSolutions;/*for numOfSolutions calculation*/
	int executed;/*1 if the command from user executed, 0 else.*/
	int success = 0,successX = 1,successY;/*for generate-if we succedded making the board*/
	int randR,randC;/*for random value*/
	int boardExist = 0;/*1 if there's board already, 0 else.*/
	int numOfChanges;/*for entering the list number of changed cells*/
	int* mnArr;/*array with the sizes of the block getting from loading sudoku file. index 0 has m and index 1 has n.*/
	/*for changing cells entering the list:*/
	int* newCell;
	int* oldCell;
	int* placeChangeI;
	int* placeChangeJ;
	double** game = originalBoard;/*our precise game table pointer.*/
	/*some double** pointer*/
	double** tmpPtr;
	double** tmpPtr2;
	FILE* file;/*file pointer for some uses(checks)*/
	INFO* info;/*the struct we get from parse module*/
	info=malloc(sizeof(INFO)*1);/*allocate it */
	assert(info!=NULL);
	printf("Sudoku\n------\n");/*game starts here*/
	while(1){/*asks commands and executes them, starting in Game module; works until exit command*/
		printf("Enter your command:\n");/*command asking*/
		Parser(info,m,n);/*updating the instruction from the user console to info*/
		executed = userMove(game,m,n,&gameMode,&markErrors,&info->moveNumber,info->integerArguments,info->stringArguments);/*executing the input from the user*/
		if(info->moveNumber == 1 && executed){/*continue the solve X instruction*/
			if(boardExist){
				/*destroy old table*/
				for(i = 0; i < N; i++)
					free(originalBoard[i]);
				free(originalBoard);
				list = destroyListFromPointer(list,1,N);/*destroys old list*/
			}/*building board:*/
			mnArr = loadFromFileMN(info->stringArguments);
			m = mnArr[0];
			n = mnArr[1];
			free(mnArr);/*we saved our values, we don't need this array anymore*/
			N = m*n;
			originalBoard = makeTable(N);
			game = originalBoard;/*the game is now in the starting board again*/
			loadFromFile(info->stringArguments,originalBoard,gameMode,N);
			boardExist = 1;
			updateErroneous(originalBoard,m,n);
			printBoard(game,m,n,gameMode,markErrors);
		}
		else if(info->moveNumber == 2 && executed){/*continue the edit [X] instruction*/
			if(boardExist){
				/*destroy old table*/
				for(i = 0; i < N; i++)
					free(originalBoard[i]);
				free(originalBoard);
				list = destroyListFromPointer(list,1,N);/*destroys old list*/
			}/*building board:*/
			if(!info->stringArguments){/*no filePath*/
				m = defaultRow;
				n = defaultColumn;
				N = m*n;
				originalBoard = makeTable(N);/*makes default board with empty cells*/
				game = originalBoard;/*the game is now in the starting board again*/
			}
			else{/*build board from file*/
				mnArr = loadFromFileMN(info->stringArguments);
				m = mnArr[0];
				n = mnArr[1];
				free(mnArr);/*we saved our values, we don't need this array anymore*/
				N = m*n;
				originalBoard = makeTable(N);
				game = originalBoard;/*the game is now in the starting board again*/
				loadFromFile(info->stringArguments,originalBoard,gameMode,N);
				updateErroneous(originalBoard,m,n);
			}
			printBoard(game,m,n,gameMode,markErrors);
			boardExist = 1;
		}
		else if(info->moveNumber == 5 && executed){/*continue the set X Y Z instruction*/
			cell = (int)game[info->integerArguments[2]][info->integerArguments[1]];/*old cell*/
			list = destroyListFromPointer(list,0,N);/*0 -> not from start-but from pointer*/
			/*the changes:*/
			oldCell = (int*)malloc(sizeof(int)*1);
			newCell = (int*)malloc(sizeof(int)*1);
			placeChangeI = (int*)malloc(sizeof(int)*1);
			placeChangeJ = (int*)malloc(sizeof(int)*1);
			oldCell[0] = cell;
			newCell[0] = info->integerArguments[3];
			placeChangeI[0] = info->integerArguments[2];
			placeChangeJ[0] = info->integerArguments[1];
			tmpPtr = makeTable(N);
			list = insertMove(list,tmpPtr,oldCell,newCell,placeChangeI,placeChangeJ,1);/*insert to list*/
			copyTable(tmpPtr,game,N);
			game = getTable(list);/*pointing the game to the current table*/
			game[info->integerArguments[2]][info->integerArguments[1]] = (double)info->integerArguments[3];/*updating new cell*/
			error = updateErroneous(game,m,n);
			printBoard(game,m,n,gameMode,markErrors);
			if(gameMode == 1 && (emptyCellsNumber(game,m,n) == 0)){
				validateForSet(game,m,n,error);
			}
		}
		else if(info->moveNumber == 6 && executed){/*continues validate - trys to solve non errneous table*/
			tmpPtr = ILPSolve(game,m,n);
			if(tmpPtr){
				if(!emptyBoard(tmpPtr,m,n)){
					printf("Validation passed: board is solvable\n");
					destroyTable(tmpPtr,N);/*no need for solution*/
				}else{
					printf("Validation failed: board is unsolvable\n");
				}
			}else{	/*gorubi failed- null returned*/
				printf("GORUBI failure\n");
			}
		}
		else if(info->moveNumber == 7 && executed){/*continues generate execution*/
					tmpPtr = makeTable(N);
					copyTable(tmpPtr,game,N);
					success = 0;
					for(i = 0; i < 1000; i++){/*generate trys*/
						successX = 1;
						for(j = 0; j < info->integerArguments[1]; j++){/*X random fills*/
							while(1){/*one fill*/
								randR = rand() % N;
								randC = rand() % N;
								if(((int)tmpPtr[randR][randC]) == 0){
									if(fillLegalRandom(tmpPtr,m,n,randR,randC) == 0)
										successX = 0;
									break;
								}
							}
							if(!successX){
								cleanBoard(tmpPtr,m,n);
								break;
							}
						}
						if(!successX){/*trys again filling X cells*/
							continue;
						}
						/*checks with ILP after succusesful filling X cells*/
						tmpPtr2 = ILPSolve(tmpPtr,m,n);
						if(tmpPtr2){
							if(!emptyBoard(tmpPtr2,m,n)){
								success = 1;
								copyTable(tmpPtr,tmpPtr2,N);
								destroyTable(tmpPtr2,N);/*no need for solution*/
							}
						}
						if(success){
							break;
						}
						cleanBoard(tmpPtr,m,n);
					}
					if(success){
						list = destroyListFromPointer(list,0,N);/*0 -> not from start-but from pointer*/
						/*the changes:,maximum allocating needed.*/
						oldCell = (int*)malloc(sizeof(int)*(N*N));
						newCell = (int*)malloc(sizeof(int)*(N*N));
						placeChangeI = (int*)malloc(sizeof(int)*(N*N));
						placeChangeJ = (int*)malloc(sizeof(int)*(N*N));
						list = insertMove(list,tmpPtr,oldCell,newCell,placeChangeI,placeChangeJ,info->integerArguments[2]);/*insert to list*/
						/*marking Y random cells:*/
						for(i = 0; i < info->integerArguments[2]; i++){
							while(1){
								successY = 1;
								randR = rand() % N;
								randC = rand() % N;
								for(j = 0; j < i; j++){
									if(randR == placeChangeI[j] && randC == placeChangeJ[j]){
										successY = 0;
										break;
									}
								}
								if(!successY)
									continue;
									/*saving old\new cells:*/
								oldCell[i] = 0;
								newCell[i] = ((int)tmpPtr[randR][randC]);
								placeChangeI[i] = randR;
								placeChangeJ[i] = randC;
								break;
							}
						}
						/*removing all but Y random marked cells:*/
						for(i = 0; i < N; i++){
							for(j = 0; j < N; j++){
								if(info->integerArguments[2] == 0){
									tmpPtr[i][j] = 0.0;
								}
								for(z = 0; z < info->integerArguments[2]; z++){
									if(i == placeChangeI[z] && j == placeChangeJ[z])
										break;
									if(z ==  (info->integerArguments[2] -1)){
										tmpPtr[i][j] = 0.0;
									}
								}
							}
						}
						game = getTable(list);/*pointing the game to the current table*/
						updateErroneous(game,m,n);
						printBoard(game,m,n,gameMode,markErrors);
					}
					else{
						printf("Error: puzzle generator failed\n");
						destroyTable(tmpPtr,N);
					}
				}
		else if(info->moveNumber == 8 && executed){/*continue the undo move if its executed*/
			if(moveBackward(list)){/*can undo(and did so):*/
				if(!getTable(list)){/*we went back to originalBoard-update accordingly*/
					game = originalBoard;
				}
				else{
					game = getTable(list);/*update game board in case its not original*/
				}
				updateErroneous(game,m,n);
				printBoard(game,m,n,gameMode,markErrors);
				moveForward(list);/*"undo" the undo - to get the new\old\location of the cells needed*/
				numOfChanges = getNumOfChanges(list);
				for(i = 0; i < numOfChanges; i++){/*print for each change in the order they did - generic solution for set,autofill,generate.*/
					printf("Undo %d,%d: from ",getPlaceChangeJ(list,i)+1,getPlaceChangeI(list,i)+1);/*print place of change*/
					/*prints cells changed:*/
					/*prints Z1:*/
					if(newCellEmpty(list,i))
						printf("_ ");
					else
						printf("%d ",getNewCell(list,i));
					printf("to ");
					/*prints Z2:*/
					if(oldCellEmpty(list,i))
						printf("_");
					else
						printf("%d",getOldCell(list,i));
					printf("\n");
				}
				moveBackward(list);/*actually undo*/
			}
			else{
				printf("Error: no moves to undo\n");
			}
		}
		else if(info->moveNumber == 9 && executed){/*continue the redo move if its executed*/
			if(moveForward(list)){/*can redo(and did so):*/
				game = getTable(list);/*update game board-couldn't be original*/
				updateErroneous(game,m,n);
				printBoard(game,m,n,gameMode,markErrors);
				numOfChanges = getNumOfChanges(list);
				for(i = 0; i < numOfChanges; i++){/*print for each change in the order they did - generic solution for set,autofill,generate.*/
					printf("Redo %d,%d: from ",getPlaceChangeJ(list,i)+1,getPlaceChangeI(list,i)+1);/*print place of change*/
					/*prints cells changed:*/
					/*prints Z1:*/
					if(oldCellEmpty(list,i))
						printf("_ ");
					else
						printf("%d ",getOldCell(list,i));
					printf("to ");
					/*prints Z2:*/
					if(newCellEmpty(list,i))
						printf("_");
					else
						printf("%d",getNewCell(list,i));
					printf("\n");
				}
			}
			else{
				printf("Error: no moves to redo\n");
			}
		}
		else if(info->moveNumber == 10 && executed){/*continues save X execution*/
			if(gameMode == 2){/*if edit mode*/
				tmpPtr = ILPSolve(game,m,n);
				if(tmpPtr){
					if(!emptyBoard(tmpPtr,m,n)){	
						destroyTable(tmpPtr,N);/*no need for solution*/
						/*trying to save to file:*/
						file = fopen(info->stringArguments,"w");
						if(!file){
							printf("Error: File cannot be created or modified\n");
						}
						else{
							assert(fclose(file) == 0);/*close file before saving proedure*/
							saveToFile(info->stringArguments,game,m,n,gameMode);
							printf("Saved to: %s\n",info->stringArguments);
						}
					}else{
						printf("Error: board validation failed\n");
					}
				}
				else{
					printf("GORUBI failure\n");
				}
			}
			else{/*solve mode*/
				file = fopen(info->stringArguments,"w");
				if(!file){
					printf("Error: File cannot be created or modified\n");
				}
				else{
					assert(fclose(file) == 0);/*close file before saving proedure*/
					saveToFile(info->stringArguments,game,m,n,gameMode);
					printf("Saved to: %s\n",info->stringArguments);
				}
			}
		}
		else if(info->moveNumber == 11 && executed){/*continues hint X Y execution*/
			tmpPtr = ILPSolve(game,m,n);
			if(tmpPtr != NULL){
				if(!emptyBoard(tmpPtr,m,n)){	
					cell = (int)tmpPtr[info->integerArguments[2]][info->integerArguments[1]];
					printf("Hint: set cell to %d\n",cell);
					destroyTable(tmpPtr,N);/*don't need it anymore*/
				}else{
					printf("Error: board is unsolvable\n");
				}
			}else{
				printf("GORUBI failure\n"); 
			}
		}
		else if(info->moveNumber == 12 && executed){/*continues num_solutions execution*/
			numSolutions = EXBacktracking(game,m,n);
			printf("Number of solutions: %d\n",numSolutions);
			if(numSolutions == 1){
				printf("This is a good board!\n");
			}
			else if(numSolutions > 1){
				printf("The puzzle has more than 1 solution, try to edit it further\n");
			}
		}
		else if(info->moveNumber == 13 && executed){/*continues autofill execution*/
			numOfChanges = 0;/*starting the count of changes from 0*/
			list = destroyListFromPointer(list,0,N);/*0 -> not from start-but from pointer*/
			/*the changes:,maximum allocating needed.*/
			oldCell = (int*)malloc(sizeof(int)*(N*N));
			newCell = (int*)malloc(sizeof(int)*(N*N));
			placeChangeI = (int*)malloc(sizeof(int)*(N*N));
			placeChangeJ = (int*)malloc(sizeof(int)*(N*N));
			list = insertMove(list,makeTable(N),oldCell,newCell,placeChangeI,placeChangeJ,1);/*insert to list*/
			tmpPtr = getTable(list);
			copyTable(tmpPtr,game,N);
			for(i = 0; i < N; i++){/*autofilling all cells*/
				for(j = 0; j < N; j++){
					cell = (int)game[i][j];
					if(cell == 0){
						tmpPtr[i][j] = (double)obviousCell(game,m,n,i,j);/*autofilling cell*/
						cell2 = (int)tmpPtr[i][j];/*now adding to changes arrays if changed*/
						if(cell2 != 0){/*cell has changed with obvious value!*/
							oldCell[numOfChanges] = cell;
							newCell[numOfChanges] = cell2;
							placeChangeI[numOfChanges] = i;
							placeChangeJ[numOfChanges] = j;
							numOfChanges++;
							printf("Cell <%d,%d> set to %d\n",j+1,i+1,cell2);
						}
					}
				}
			}
			setNumberOfChanges(list,numOfChanges);
			game = getTable(list);/*pointing the game to the current table*/
			error = updateErroneous(game,m,n);
			printBoard(game,m,n,gameMode,markErrors);
			if(gameMode == 1 && (emptyCellsNumber(game,m,n) == 0)){
				validateForSet(game,m,n,error);
			}
		}
		else if(info->moveNumber == 14 && executed){/*continues reset execution*/
			/*not in init mode - so originalBoard must exist*/
			list = destroyListFromPointer(list,1,N);/*destroys list*/
			game = originalBoard;/*the game is now in the starting board again*/
			printf("Board reset\n");
		}
		else if(info->moveNumber == 15 && executed){/*continues exit command*/
			printf("Exiting...\n");
			/*free this stuff: we will do it now because we break the loop and miss the free later*/
			freeINFO(info);
			break;/*we exit now the infinity iterated loop that asks for commands and free all allocated memory, then the program will terminate*/
		}
		/*before the end ofiteration: free info. then we will allocate it again and initialize its pointers to NULL to prepare next iteration */
		freeINFO(info);
		info=malloc(sizeof(INFO)*1);
		assert(info!=NULL);
		createINFO(info);
	}
	/*free the table and list variables:*/
	list = destroyListFromPointer(list,1,N);
	if(originalBoard)
		destroyTable(originalBoard,N);
}
/*this will validate the board for set method if it is in the solve mode and if the last cell is inserted*/
void validateForSet(double** game,int m,int n,int error){
	int N = m*n;
	double** ptr;
	if(error){
		printf("Puzzle solution erroneous\n");
		return;/*no validating*/
	}
	ptr = ILPSolve(game,m,n); /*not NULL iff the board is solveable(and if so-its the solution)*/
	if(ptr){
		if(!emptyBoard(ptr,m,n)){	
			printf("Puzzle solved successfully\n");
			destroyTable(ptr,N);/*no need for solution*/
			gameMode = 0;/*going to init mode*/
		}else{
			printf("Puzzle solution erroneous\n");
		}
	}else{
		printf("GORUBI failure\n");	
	}
}
/*return 1 iff all cells is 0, */
int emptyBoard(double **board, int m, int n){
	int ret, i,j,N;
	N=m*n;
	ret=1;
	for (i=0; i<N;i++){
		for(j=0; j<N; j++){
			if (board[i][j]!=0){
				ret=0;
			}
		}
	}return ret;
}
