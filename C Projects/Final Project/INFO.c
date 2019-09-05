/*this module contain the functions which manage the INFO struct which in use in the parser module */


#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "ParserAux.h"
#include <assert.h>
#include "INFO.h"


/*get an initialized INFO and initiate its  pointers to null*/
void createINFO(INFO* info){
	info->stringArguments=NULL;
	info->integerArguments=NULL;
}
/*get INFO and free its pointer and the INFO itself*/
void freeINFO(INFO *info){
	if(info->stringArguments){
		free(info->stringArguments);
	}
	if(info->integerArguments){
		free(info->integerArguments);
	}
	free(info);

}
