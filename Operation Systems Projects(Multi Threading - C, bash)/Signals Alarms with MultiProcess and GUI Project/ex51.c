/** Tomer Paz 315311365 */

#include <stdio.h>
#include <unistd.h>
#include <termios.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>


/*get the char from the user from stdin without the user clicks enter! */
char getch() {
    char buf = 0;
    struct termios old = {0};
    if (tcgetattr(0, &old) < 0)
        perror("tcsetattr()");
    old.c_lflag &= ~ICANON;
    old.c_lflag &= ~ECHO;
    old.c_cc[VMIN] = 1;
    old.c_cc[VTIME] = 0;
    if (tcsetattr(0, TCSANOW, &old) < 0)
        perror("tcsetattr ICANON");
    if (read(0, &buf, 1) < 0)
        perror("read()");
    old.c_lflag |= ICANON;
    old.c_lflag |= ECHO;
    if (tcsetattr(0, TCSADRAIN, &old) < 0)
        perror("tcsetattr ~ICANON");
    return (buf);
}

/*print error and exit incase of failed system call */
void printErr(){
    write(2,"Error in system call\n",sizeof("Error in system call\n")-1);
    exit(1);
}

/*ex51 main */
int main() {
    int fd[2];//our pipe
    pid_t pid;//for forking
    char** args;//for the execvp in son process
    char buf[1];//to write a char to pipe.
    int isQ = 0;//its 0 if user didn't enter q and we still work
    char c;//for getting char from user
    args = malloc(sizeof(char*));
    args[0] = malloc(sizeof(char));
    strcpy(args[0],"./draw.out");
    args[1] = NULL;
    /* Creating a pipe */
    if (pipe(fd) < 0)
        printErr();
    //forking:
    pid = fork();
    if(pid < 0){//fork problem..
        printErr();
    }
    else if(pid == 0) {//son process:
        dup2(fd[0],0);//redirect pipe to Standard Input
        close(fd[1]);//we close the write fd
        execvp(args[0],args);//run "draw.out"
        printErr();
    }
    else {//pid > 0 - father process:
        dup2(fd[1],1);//redirect Standard Output to pipe
        close(fd[0]);//we close the read fd
        while(isQ == 0){//write to pipe and signal afterward. if entered q its the last loop!
            c = getch();//wait for - and get user input
            //write it to pipe:
            buf[0] = c;
            write(1,buf,1);
            kill(pid,SIGUSR2);//alert the son process we wrote in the pipe
            if(c == 'q'){
                isQ = 1;
            }
        }
        close(fd[1]);//we close the write fd
        //free memory:
        free(args[0]);
        free(args);
    }
    return 0;
}