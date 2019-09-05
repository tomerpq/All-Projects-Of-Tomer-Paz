/*
 * INFO header contains the INFO struct defenition and the function declarations
 *
 */

#ifndef INFO_H_
#define INFO_H_

/*this structure is helping out parser module- it will include the parameters:
 * moveNumber - according to the number representing each move in the work file pdf. and will be used correspondingly
 * in the other modules.
 * integerArguments - in case the move includes arguments in the type of integer - we will save them seperated in the
 * order the appear in the work file pdf, in the array from index 1; in index 0 we will have for our
 * command(if its valid) 1 if the parameters are ok and 0 if not.
 * stringArguments - in case the move includes argument in the type of String - we will save it in the char array.*/
typedef struct INFO{
	int moveNumber;
	int * integerArguments;
	char * stringArguments;
}INFO;


void createINFO(INFO *info);
void freeINFO(INFO* info);

#endif /* INFO_H_ */
