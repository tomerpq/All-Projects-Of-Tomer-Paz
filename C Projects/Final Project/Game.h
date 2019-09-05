/*Game Header - just the copy of the declarations. */

#ifndef GAME_H_
#define GAME_H_
void printCell(double** game,int i,int j,int mode,int markErrors);
void printSap(int m,int n,int withNewLine);
void printBoard(double** game,int m,int n,int mode,int markErrors);
int getOffset1(double x);
int updateErroneous(double** game,int m,int n);
int checkAndUpdateCellToErroneous(double** game,int m,int n,int i,int j);
int userMove(double** game,int m,int n,int* mode,int* markErrors,int* move,int* integerArguments,char* stringArguments);
int emptyCellsNumber(double** game,int m,int n);
int obviousCell(double** game,int m,int n,int i,int j);
int fillLegalRandom(double** game,int m,int n,int i,int j);
void cleanBoard(double** game,int m,int n);
int is_legal2(double **board, int m, int n, int i, int j, double val);

#endif /* GAME_H_ */
