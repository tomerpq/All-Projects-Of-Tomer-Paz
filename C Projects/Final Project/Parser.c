/* this module responsbile for taking the input from user and parser it  to INFO struct passed from mainaux module*/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "ParserAux.h"
#include <assert.h>
#include "INFO.h"




/*parser function recieve the input from user and store it in the given INFO parameter. if input 
isn't a white line ParserAux function is called and the input and the INFO are passed to there */

void Parser(INFO *ret_info , int m, int n){
	int i,len;
	char buf[258];
	char *p;
	/*first checking if input is not too long or white line*/
	if (fgets(buf, 258, stdin) != NULL) {
		len=strlen(buf);
		if (len>TOP){
			/*too long cmd*/
			ret_info->moveNumber=0;
			/*clean the rest of input upto newline*/
			if(p=strchr(buf, '\n')){                /*check exist newline*/
                	*p = 0;
            		} else {
                		scanf("%*[^\n]");scanf("%*c");/*clear upto newline*/
            		}
			
			return;
		}	
		for (i=0; buf[i]!='\n';i++){
			/*iterate over the input. if there is a "black" char- break*/
			if ((buf[i]!=' ')&&(buf[i]!='\t')&&(buf[i]!='\r')){
				break;
			}
		}
		if (i==(int)(strlen(buf)-1)){
			/*the input is white line- ignore input& ask it from the user again implicitly*/
			ret_info->moveNumber=16;
			ret_info->stringArguments=NULL;
			
		}else{
		/*pass the input to GeneralParser*/
			GeneralParser(ret_info,m,n,buf);
			return;
		}
	}else{			/*eof- exit*/
		ret_info->moveNumber=15;
		
	}

}


