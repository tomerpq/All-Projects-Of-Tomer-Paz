/*MainAux Module */
#include "Game.h"
#include "Solver.h"
#include "Parser.h"
#include "MainAux.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* */
/* */
void play(int* lengths){
	int madeDo = 0;/*not to use extra memory*/
	int i,j;/*for loops*/
	int** solvedGame = solverRandWithMake(lengths);
	int** game = initalizationWithMake(solvedGame,lengths);
	int* Do;
	int solveable = 1;
	int restart = 0;
	int* values2 = calloc(lengths[0]*lengths[1],sizeof(int));
	int** temp2 = malloc(lengths[1]*sizeof(int*));/*init 2d game*/
	int* values1 = calloc(lengths[0]*lengths[1],sizeof(int));
	int** temp1 = malloc(lengths[1]*sizeof(int*));/*init 2d game*/
	int * validAndResolve;
	validAndResolve = (int *)malloc(2 * sizeof(int));
	for(i = 0; i< lengths[1]; ++i)
		temp1[i] = values1 + i*lengths[0];
	for(i = 0; i< lengths[1]; ++i)
		temp2[i] = values2 + i*lengths[0];
	validAndResolve[0] = 1;
	while(1){/* the play*/
		if(!madeDo){
			Do = parserWithMake();
			madeDo = 1;
		}
		else{
			Do = parserWithoutMake(Do);
		}
		if(Do[0] == 4)/*check for restart that game modolue doesn't*/
			restart = 1;
		if(restart){
			solvedGame = solverRandWithoutMake(lengths,solvedGame);
			game = initalizationWithoutMake(solvedGame,lengths,game);
			validAndResolve[0] = 1;
			validAndResolve[1] = 0;
			solveable = 1;
			restart = 0;
		}
		if(Do[0] == 2){/*validate*/
			if(validAndResolve[0]){/*if valid*/
				temp1 = solverDetWithoutMake(game,lengths,temp2);
				if(temp1 == NULL)
					solveable = 0;
				else{
					solveable = 1;
					for(i = 0; i < lengths[0]; i++)
						for(j = 0; j < lengths[1]; j++)
							temp2[i][j] = temp1[i][j];
				}
				if(!solveable){/*input checked in mainAux if we can solve*/
					printf("Validation failed: board is unsolvable\n");
					validAndResolve[1] = 0;
				}
				else{
					printf("Validation passed: board is solvable\n");
					validAndResolve[1] = 1;
				}
				if(validAndResolve[1]){/*update the solution*/
					for(i = 0; i < lengths[0]; i++)
						for(j = 0; j < lengths[1]; j++)
							solvedGame[i][j] = temp2[i][j];
				}
			}
			else{/*not valid*/
				invalid();
				validAndResolve[1] = 0;
			}
		}
		if(Do[0] != 2 && Do[0] != 4)/*other options-Game Modolue will check*/{
			validAndResolve = gamePlay(game,solvedGame,lengths,Do,validAndResolve);
		}
	}
	destroyGame(temp1);
	destroyGame(temp2);
}
