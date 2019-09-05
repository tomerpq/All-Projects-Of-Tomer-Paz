/*
 * this module continue to parser the input which passed from the user in parser module and parser t to command and parameters
 *
 */
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <ctype.h>
#include <assert.h>
#include "INFO.h"
#include "ParserAux.h"

/*gets the input from the user and break it to token. the command type is extracked and store in info->moveNumber acoording to thr number of 
  the command in the instruction pages. the parameters of the command stored in string/number Arguments. if the command isn't valid move=0 */

void GeneralParser(INFO *info,int m, int n, char *buf){

	int i,tmp,err, N=m*n;
	char *str2;							/*contains the current token in the input*/
		
		
		/*break input to tokens and check command type*/
		str2 = strtok(buf, " \t\n\r");
		if (strcmp(str2, SOLVE) == 0){ 						/*solve cmd case*/
			info->moveNumber=1;
			str2=strtok(NULL, " \t\n\r");					/*advanced to next token*/
			if (str2==NULL){									/*there is no file name*/
				info->moveNumber=0;							/*invalid cmd*/
			}else{
				info->stringArguments=malloc(sizeof(char)*(strlen(str2)+1));
				assert(info->stringArguments!=NULL);
				strcpy(info->stringArguments,str2);			/*set the file name into stringaruments*/
			}

	 	}
		else if (strcmp(str2, EDIT) == 0) {					/*edit cmd case*/
			info->moveNumber=2;
			str2=strtok(NULL, " \t\n\r");					/*advanced to next token*/
			/*assert(info->stringArguments!=NULL);*/
			if (str2!=NULL){
				info->stringArguments=malloc(sizeof(char)*(strlen(str2)+1));
				strcpy(info->stringArguments,str2);			   /*set the file name */
			}
		}
		else if (strcmp(str2, MARK_E)==0){					/*mark_e cmd case*/
			info->moveNumber=3;
			str2=strtok(NULL, " \t\n\r");
			if (str2==NULL){								/*parameter is missing */
				info->moveNumber=0;							/*invalid command*/
			}
			else if (!isLegalInt((str2))|| (charToInt(str2)>1)||(charToInt(str2)<0)){			/*parameter isn't  legal number*/
				info->integerArguments=calloc(4,sizeof(int));
				assert(info->integerArguments!=NULL);
				info->integerArguments[0]=0;
			}
			else{
				info->integerArguments=calloc(4,sizeof(int));
				assert(info->integerArguments!=NULL);
				info->integerArguments[0]=1;
				info->integerArguments[1]=charToInt(str2);	/*set the value in info.intArgs[0]*/
			}
		}
		else if(strcmp(str2, PRINT_B)==0){					/*print_board cmd case*/
			info->moveNumber=4;
		}
		else if(strcmp(str2, SET)==0){						/*set cmd case*/
			info->moveNumber=5;
			str2=strtok(NULL,"");							/*take the rest of the user's input*/
			setIntArgs(info, str2, m,n,4 );					/*set 3 parameters*/
		}
		else if (strcmp(str2, VALI)==0){					/*validate cmd case*/
			info->moveNumber=6;
		}
		else if (strcmp(str2, GEN)==0){						/*genetate cmd case*/
			info->moveNumber=7;
			str2=strtok(NULL, " \t\n\r");
			info->integerArguments=calloc(4,sizeof(int));
			assert(info->integerArguments!=NULL);
			i=1;
			err=0;								/*err indicate if there ia a not in range parameter*/
			while((str2!=NULL)  && (i<=3)){
				if(!isLegalInt(str2)){					/*token doesn't represent number*/
					err=1;
				}else{
					tmp=charToInt(str2);				/*convert string to int number*/
					if ((tmp>(N*N))||(tmp<0)){			/*bigger than the num of empety cells*/
						err=1;
					}else{
						info->integerArguments[0]=1;
						info->integerArguments[i]=tmp;
					}
				}
				i++;
				str2=strtok(NULL,  " \t\n\r");
			}
			if (i<3){									/*missing parameters*/
				info->moveNumber=0;						/*invalid cmd*/
			}else{
				if(err){
					info->integerArguments[0]=0;
				}
			}
		}
		else if (strcmp(str2, UNDO)==0){					/*undo cmd case*/
			info->moveNumber=8;
		}
		else if (strcmp(str2, REDO)==0){					/*redo cmd case*/
			info->moveNumber=9;
		}
		else if (strcmp(str2, SAVE)==0){					/*save cmd case*/
			info->moveNumber=10;
			str2=strtok(NULL, " \t\n\r");					/*advanced to next token*/
			if (str2==NULL){								/*there is no file name*/
				info->moveNumber=0;							/*invalid cmd*/
			}else{
				info->stringArguments=malloc(sizeof(char)*(strlen(str2)+1));
				assert(info->stringArguments!=NULL);
				strcpy(info->stringArguments,str2);			/*set the file name into stringaruments*/
			}

		}
		else if (strcmp(str2, HINT)==0){					/*hint cmd case*/
			info->moveNumber=11;
			str2=strtok(NULL,"");							/*take the rest of the user's input*/
			setIntArgs(info, str2, m,n,3 );					/*set 2 parameters*/

		}
		else if (strcmp(str2, NUM_S)==0){					/*num_solutions cmd case*/
			info->moveNumber=12;
		}
		else if (strcmp(str2, AUTO)==0){					/*autofill cmd case*/
			info->moveNumber=13;
		}
		else if (strcmp(str2, RESET)==0){					/*reset cmd case*/
			info->moveNumber=14;
		}
		else if (strcmp(str2, EXIT)==0){					/*exit cmd case*/
			info->moveNumber=15;
		}
		else {									/*invalid command case*/
			info->moveNumber=0;
		}
		
}



/*return if a string toCheck is represent an integer*/
int isLegalInt(char *toCheck){
	int l,i;
	l=strlen((toCheck));
	for (i=0; i<l; i++){
		if (!isdigit(toCheck[i])){
			return 0;
		}
	}
	return 1;
}

/*return if a char c is represent  a decimaly number according to its ascii val  */
int isDec(char c){
	if (c<48 || c>57){
		return 0;
	}
	return 1;
}


/*convert string with digits only to int number*/
int charToInt (char *c){
	int i,l,ret;
	l=strlen(c);
	ret=0;
	for (i=0; i<l; i++){
		ret=ret+((c[i]-48)*my_pow(10,l-1-i));
	}
	return ret;
}

/*set the parameters for command required parameters (set, hint) */
void setIntArgs(INFO *info, char *input, int m,  int n, int paramNum ){
	char *tok;
	int i, k,tmp, N=m*n;
	tok=strtok(input, " \t\n\r");					/*break the rest of the input to extract the parameters*/
	info->integerArguments=calloc(4,sizeof(int));
	assert(info->integerArguments!=NULL);
	i=1;
	tmp=0;								/*tmp indicate if there isnot in range parameter*/
	while (tok!=NULL && (i<paramNum)){
		if(!isLegalInt(tok)){						/*parameter is not number->not in range msg*/
			tmp=1;
		}else{
			k=charToInt(tok);
			if(i<3){
				if ((k>N)||(k<1)){					/*check if the number in legal range: 1-N for x,y*/
					tmp=1;
					info->integerArguments[0]=0;
					
				}else{								/*legal value*/
					info->integerArguments[0]=1;
					info->integerArguments[i]=k-1;
				}
			}else{									/*cell value can be in 0-N range for z*/
				if ((k>N)||(k<0)){
					info->integerArguments[0]=0;
					return;
				}else{								/*legal value*/
					info->integerArguments[0]=1;
					info->integerArguments[i]=k;

				}
			}
		}
		i++;
		tok=strtok(NULL,  " \t\n\r");
	}if (i<paramNum){								/*too few parameter for the command*/
		info->moveNumber=0;
	}else{
		if(tmp){								/*1 or more parameters not in range*/
			info->integerArguments[0]=0;
		}
	}
}
/*return a^exp*/
int my_pow (int b, int exp){
	int i=1, dup=b;

	if (exp==0){
		return 1;
	}else{
		while(i<exp){
			b=b*dup;
			i++;
		}
	}return b;
}


