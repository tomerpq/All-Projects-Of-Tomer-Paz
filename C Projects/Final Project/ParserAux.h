/* ParserAux header contains  the define for every command for the GeneralParser function and the function declerations */

#ifndef PARSERAUX_H_
#define PARSERAUX_H_
#define TOP 256
#define SOLVE   "solve"				/*cmd num=1*/
#define EDIT    "edit"				/*cmd num=2*/
#define MARK_E  "mark_errors"			/*cmd num=3*/
#define PRINT_B "print_board"			/*cmd num=4*/
#define SET     "set"				/*cmd num=5*/
#define VALI	"validate"			/*cmd num=6*/
#define GEN     "generate"			/*cmd num=7*/
#define UNDO	"undo"				/*cmd num=8*/
#define REDO	"redo"				/*cmd num=9*/
#define SAVE	"save"				/*cmd num=10*/
#define HINT	"hint"				/*cmd num=11*/
#define NUM_S   "num_solutions"			/*cmd num=12*/
#define AUTO	"autofill"			/*cmd num=13*/
#define RESET	"reset"				/*cmd num=14*/
#define EXIT	"exit"				/*cmd num=15*/
#include "INFO.h"




void GeneralParser( INFO *info, int m, int n, char *buf);
int isLegalInt(char *toCheck);
int isDec(char c);
int charToInt (char *c);
void setIntArgs(INFO *info, char *input, int m,  int n, int paramNum );
int my_pow(int a, int exp);
#endif /* PARSERAUX_H_ */
