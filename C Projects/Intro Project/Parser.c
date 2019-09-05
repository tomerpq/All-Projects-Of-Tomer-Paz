/*Parser Module*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Parser.h"
/*the get_input  function get the input from user and return_arr int array according to the next keys:
* return_arr[0]->contain the commnad type: set=0, hint=1, validate=2, 
*exit=3, restart=4 invalid command=5, newline(enter button)=6.
* return_arr[1 to 3]- >cells coordinates  (at set & hint commands)
* return_arr[4] -> contain extra input if exist. (to be ignore )
*/
int * parserWithMake() {
	int str1, i;
	char set[] = "set";
	char hint[] = "hint";
	char validate[] = "validate";
	char exit_cmd[] = "exit";
	char restart[] = "restart";
	char str[1024];
	char * str2;
	int * return_arr;
	return_arr = (int *)malloc(5 * sizeof(int));
	if (return_arr == NULL) {
		printf("Error: malloc has failed\n");
		exit(0);
	}
	/*recive input from user*/
	if (fgets(str, 1024, stdin) != NULL) {
		/*ignore newline */
		if ((strcmp(str, " ") == 0) || (strcmp(str, "\n") == 0) || (strcmp(str, "") == 0)) {
			return_arr[0] = 6;
			return return_arr;
		}
		if(strcmp(str, "EOF")==0){
			return_arr[0]=3;

		}
		/*break input to tokens and check command type*/
		str2 = strtok(str, " \t\n\r");
		if (strcmp(str2, set) == 0) {
			return_arr[0] = 0;
			}
		else if (strcmp(str2, hint) == 0) {
			return_arr[0] = 1;
			}
		else if (strcmp(str2, validate) == 0) {
			return_arr[0] = 2;
			}
		else if (strcmp(str2, exit_cmd) == 0) {
			return_arr[0] = 3;
			}
		else if (strcmp(str2, restart) == 0) {
			return_arr[0] = 4;
			}
		/*invalid command case*/
		else {
			return_arr[0] = 5;
		}
		str2 = strtok(NULL, " \t\n\r");
		i = 0;
		while (str2 != NULL){
			i++;
			str1 = str2[0] - 48;
			if (i < 4) {
				return_arr[i] = str1;
			}
			else {
				return_arr[4] = str1;
			}

			str2 = strtok(NULL, " ");
		}
		/* too few arg for hint or set*/
		if (((return_arr[0] == 0) && i<3) || ((return_arr[0] == 1) && i < 2)) {
			return_arr[0] = 5;
		}
		return return_arr;
	}
	else {
		printf("Error: fgets has failed\n");
		exit(0);
	}
}
/*same as with make but doesn't use more memory */
int * parserWithoutMake(int* return_arr) {
	int str1, i;
	char set[] = "set";
	char hint[] = "hint";
	char validate[] = "validate";
	char exit_cmd[] = "exit";
	char restart[] = "restart";
	char str[1024];
	char * str2;
	return_arr[0] = 0;
	return_arr[1] = 0;
	return_arr[2] = 0;
	return_arr[3] = 0;
	return_arr[4] = 0;
	/*recive input from user*/
	if (fgets(str, 1024, stdin) != NULL) {
		/*ignore newline */
		if ((strcmp(str, " ") == 0) || (strcmp(str, "\n") == 0) || (strcmp(str, "") == 0)) {
			return_arr[0] = 6;
			return return_arr;
		}
		if(strcmp(str, "EOF")==0){
			return_arr[0]=3;

		}
		/*break input to tokens and check command type*/
		str2 = strtok(str, " \t\n\r");
		if (strcmp(str2, set) == 0) {
			return_arr[0] = 0;
			}
		else if (strcmp(str2, hint) == 0) {
			return_arr[0] = 1;
			}
		else if (strcmp(str2, validate) == 0) {
			return_arr[0] = 2;
			}
		else if (strcmp(str2, exit_cmd) == 0) {
			return_arr[0] = 3;
			}
		else if (strcmp(str2, restart) == 0) {
			return_arr[0] = 4;
			}
		/*invalid command case*/
		else {
			return_arr[0] = 5;
		}
		str2 = strtok(NULL, " \t\n\r");
		i = 0;
		while (str2 != NULL){
			i++;
			str1 = str2[0] - 48;
			if (i < 4) {
				return_arr[i] = str1;
			}
			else {
				return_arr[4] = str1;
			}

			str2 = strtok(NULL, " ");
		}
		/* too few arg for hint or set*/
		if (((return_arr[0] == 0) && i<3) || ((return_arr[0] == 1) && i < 2)) {
			return_arr[0] = 5;
		}
		return return_arr;
	}
	else {
		printf("Error: fgets has failed\n");
		exit(0);
	}
}
