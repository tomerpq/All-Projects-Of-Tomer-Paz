int RandSolve(int ** game, int i, int j);
int ** solverDetWithMake(int **game,int* lengths);
int ** solverDetWithoutMake(int **game,int* lengths,int** cloneGame);
int ** solverRandWithMake(int* lengths);
int ** solverRandWithoutMake(int* lengths,int** game);
int DetSolver(int **game, int i, int j);
int isLegal(int **game, int row, int col, int val);
int wasExclude(int *arr, int val, int len);
int abs(int x);
