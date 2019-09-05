
#ifndef GAME_H_
#define GAME_H_
int abs1(int x);
int isLegal1(int ** game, int row, int col, int val);
int ** initalizationWithMake(int** solvedGame,int* lengths);
int ** initalizationWithoutMake(int** solvedGame,int* lengths,int** game);
int* gamePlay(int** game,int** solvedGame,int* lengths,int* Do,int* validAndResolve);
void printSodoku(int** game,int* lengths);
void printDashes(int widthSquare);
int checkSquare(int** game,int* Do,int i,int j);
void invalid();
void destroyGame(int** game);

#endif /* GAME_H_ */
