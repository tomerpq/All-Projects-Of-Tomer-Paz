
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
char* mystrdup(const char* s)
{
    char* p = malloc(strlen(s)+1);
    if (p) strcpy(p, s);
    return p;
}
double pow2(int a, int b){
	double temp = a;
	int i;
	if(b == 0)
		return 1.0;
	else
	{
		for(i = b; i > 1; i--){
			temp = temp * a;
		}
	}
	return temp;
}
int main()
{
    int a,b,i;
	double number10 = 0.0;
	char number[1024];
	int j = 0;
	int intemp;
	char newnum[1024] ="";
	char str[1024] = "";
	char *ach = "A";
	char *bch = "B";
	char *cch = "C";
	char *dch = "D";
	char *ech = "E";
	char *fch = "F";
	char *str2 = "";
	char *tmp = "";
	printf("Please enter the number's base:\n");
	scanf("%d",&a);
	if(a < 2 || a > 16){
		printf("Invalid input base\n");
		exit(0);
	}
	printf("Please enter the desired base:\n");
	scanf("%d",&b);
	if(b < 2 || b > 16){
		printf("Invalid input base\n");
		exit(0);
	}
	printf("Please enter a number in base %d:\n",a);
	/**series of chars == string */
	scanf("%s",number);
	if(strlen(number) == 0){
		printf("Invalid number!\n");
		exit(0);
	}
	for(i = strlen(number) -1; i >= 0; i--){
		if(number[i] == '0'){
		}
		else if(number[i] == '1'){
			number10 += pow2(a,j);
		}
		else if(number[i] == '2'){
			if(a <= 2){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 2*pow2(a,j);
		}
		else if(number[i] == '3'){
			if(a <= 3){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 3*pow2(a,j);
		}
		else if(number[i] == '4'){
			if(a <= 4){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 4*pow2(a,j);
		}
		else if(number[i] == '5'){
			if(a <= 5){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 5*pow2(a,j);
		}
		else if(number[i] == '6'){
			if(a <= 6){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 6*pow2(a,j);
		}
		else if(number[i] == '7'){
			if(a <= 7){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 7*pow2(a,j);
		}
		else if(number[i] == '8'){
			if(a <= 8){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 8*pow2(a,j);
		}
		else if(number[i] == '9'){
			if(a <= 9){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 9*pow2(a,j);
		}
		else if(number[i] == 'A'){
			if(a <= 10){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 10*pow2(a,j);
		}
		else if(number[i] == 'B'){
			if(a <= 11){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 11*pow2(a,j);
		}
		else if(number[i] == 'C'){
			if(a <= 12){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 12*pow2(a,j);
		}
		else if(number[i] == 'D'){
			if(a <= 13){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 13*pow2(a,j);
		}
		else if(number[i] == 'E'){
			if(a <= 14){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 14*pow2(a,j);
		}
		else if(number[i] == 'F'){
			if(a <= 15){
				printf("Invalid number!\n");
				exit(0);
			}
			number10 += 15*pow2(a,j);
		}
		else{
			printf("Invalid number!\n");
			exit(0);
		}
		j++;
	}
	intemp = number10;
	while(intemp != 0){
		int mod = intemp%b;
		intemp = intemp/b;
		if(mod >= 10){
		    if(mod == 10){
		        strcpy(str,ach);
		    }
		    else if(mod == 11){
		    	strcpy(str,bch);
		    }
		    else if(mod == 12){
		    	strcpy(str,cch);
		    }
		    else if(mod == 13){
		    	strcpy(str,dch);
		    }
		    else if(mod == 14){
		    	strcpy(str,ech);
		    }
		    else{
		    	strcpy(str,fch);
		    }
		}
		else{
		    sprintf(str,"%d",mod);
		}
		tmp = mystrdup(newnum);
		strcpy(newnum,str);
		strcat(newnum,tmp);
		strcpy(str,str2);
	}
	printf("The result is:%s\n",newnum);
	free(tmp);
	return 0;
}


