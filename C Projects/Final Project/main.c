/*main module for activating the game. has only main method that uses mainAux module. */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Game.h"
#include "MainAux.h"


#define defaultRow 3
#define defaultColumn 3

/*the main function that starts the game for the user. */
int main(void){
	srand(100);
	play(defaultRow,defaultColumn);
	return 0;
}
