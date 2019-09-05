/*EXBacktrackin header. contains the function declarations*/

#ifndef EXBACKTRACKING_H_
#define EXBACKTRACKING_H_

int EXBacktracking( double **board, int m ,int n);
int is_legal_in_block(double **board, int m, int n, int i, int j, double val);
int is_legal_r_c(double **board, int m, int n, int i, int j, double val);
int is_legal(double **board, int m, int n, int i, int j, double val);


#endif /* EXBACKTRACKING_H_ */
