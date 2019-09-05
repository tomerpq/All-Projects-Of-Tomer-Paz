/** Tomer Paz 315311365 */

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

int placeOfTetris[3];//the place in index 0 will be 0 for lying tetris, and 1 for standing. in index 1 will be the column for standing and row for lying, and in the 2 index will be the first row from above for standing and first column from left for lying tetris.
char buf[1];//we read a char into it from the pipe
int isQ = 0;//0 for still running app
char** tetrisBoard;//our board
pthread_mutex_t lock1;//so the board won't be dropped in parallel in 's' command
pthread_mutex_t lock2;//so the board won't be dropped in parallel in 's' command

/*print error and exit incase of failed system call */
void printErr(){
    write(2,"Error in system call\n",sizeof("Error in system call\n")-1);
    exit(1);
}
/*if can, rotate the tetris to the right. return succ 1 iff we succeded */
int rotateTetrisIfCan(){
    int tmp;
    int succ = 0;//if we succeded rotating its 1.
    if(placeOfTetris[0] == 0) {//lying tetris
        if(((placeOfTetris[1]-1) >= 0) && ((placeOfTetris[1]+1) <= 18)){//we can flip right:
            succ = 1;
            placeOfTetris[0] = 1;//now its standing one
            tmp = placeOfTetris[1];
            placeOfTetris[1] = placeOfTetris[2]+1;
            placeOfTetris[2] = tmp-1;
        }
    }
    else{//standing tetris
        if(((placeOfTetris[1]-1) >= 1) && ((placeOfTetris[1]+1) <= 18)){//we can flip right:
            succ = 1;
            placeOfTetris[0] = 0;//now its lying one
            tmp = placeOfTetris[1];
            placeOfTetris[1] = placeOfTetris[2]+1;
            placeOfTetris[2] = tmp-1;
        }
    }
    return succ;
}
/*fill the board with values and the tetris too */
void fillBoard(){
    int i,j;
    for(i = 0; i < 20; i++){
        if(i == 19){
            for(j = 0; j < 20; j++){
                tetrisBoard[i][j] = '*';
            }
            continue;
        }
        tetrisBoard[i][0] = '*';
        for(j = 1; j < 19; j++){
            tetrisBoard[i][j] = ' ';
        }
        tetrisBoard[i][19] = '*';
    }
    if(placeOfTetris[0] == 0){//lying tetris
        tetrisBoard[placeOfTetris[1]][placeOfTetris[2]] = '-';
        tetrisBoard[placeOfTetris[1]][placeOfTetris[2]+1] = '-';
        tetrisBoard[placeOfTetris[1]][placeOfTetris[2]+2] = '-';
    }
    else{//standing tetris
        tetrisBoard[placeOfTetris[2]][placeOfTetris[1]] = '-';
        tetrisBoard[placeOfTetris[2]+1][placeOfTetris[1]] = '-';
        tetrisBoard[placeOfTetris[2]+2][placeOfTetris[1]] = '-';
    }
}
/*print board to stdout */
void printBoard(){
    int i;
    for(i = 0; i < 20; i++){
        write(1,tetrisBoard[i],20);
        write(1,"\n",1);
    }
}

/*drop the trtris each second */
void dropTetrisEachSecond(int sig){
    pthread_mutex_lock(&lock1);
    if(isQ == 1){
        pthread_mutex_unlock(&lock1);
        return;
    }
    system("clear");
    //first drop a row the tetris:
    if(placeOfTetris[0] == 0){//lying tetris
        if(placeOfTetris[1] == 18){//at bottom already
            //restart tetris shape at starting place:
            placeOfTetris[0] = 0;//start lying
            placeOfTetris[1] = 0;//start first row
            placeOfTetris[2] = 9;//start colmun from the left for lying tetris as in the example
        }
        else{
            placeOfTetris[1]++;
        }
    }
    else{//standing tetris
        if((placeOfTetris[2]+2) == 18){//at bottom already
            //restart tetris shape at starting place:
            placeOfTetris[0] = 0;//start lying
            placeOfTetris[1] = 0;//start first row
            placeOfTetris[2] = 9;//start colmun from the left for lying tetris as in the example
        }
        else{
            placeOfTetris[2]++;
        }
    }
    fillBoard();
    printBoard();
    if(isQ == 0){//only when not done yet(got q from pipe) - we continue getting signals
        signal(SIGALRM, dropTetrisEachSecond);
        alarm(1);
    }
    pthread_mutex_unlock(&lock1);
}



/*this func will handle the board with the value from the pipe.*/
void signal_hand(int sig){
    char c;
    //we got a char command from our father process, lets read it from the pipe:
    read(0,buf,1);
    c = buf[0];
    if(c == 'a'){//shift tetris left
        pthread_mutex_lock(&lock2);
        //shift left if can:
        if(placeOfTetris[0] == 0) {//lying tetris
            if(!(placeOfTetris[2] == 1)){
                placeOfTetris[2]--;
            }
        }
        else{//standing tetris
            if(!(placeOfTetris[1] == 1)){
                placeOfTetris[1]--;
            }
        }
        system("clear");
        fillBoard();
        printBoard();
        pthread_mutex_unlock(&lock2);
    }
    if(c == 'd'){//shift tetris right
        pthread_mutex_lock(&lock2);
        //shift right if can:
        if(placeOfTetris[0] == 0) {//lying tetris
            if(!((placeOfTetris[2]+2) == 18)){
                placeOfTetris[2]++;
            }
        }
        else{//standing tetris
            if(!(placeOfTetris[1] == 18)){
                placeOfTetris[1]++;
            }
        }
        system("clear");
        fillBoard();
        printBoard();
        pthread_mutex_unlock(&lock2);
    }
    if(c == 's'){//shift tetris down
        pthread_mutex_lock(&lock2);
        //shift down if can(if not restart board):
        if(placeOfTetris[0] == 0){//lying tetris
            if(placeOfTetris[1] == 18){//at bottom already
                //restart tetris shape at starting place:
                placeOfTetris[0] = 0;//start lying
                placeOfTetris[1] = 0;//start first row
                placeOfTetris[2] = 9;//start colmun from the left for lying tetris as in the example
            }
            else{
                placeOfTetris[1]++;
            }
        }
        else{//standing tetris
            if((placeOfTetris[2]+2) == 18){//at bottom already
                //restart tetris shape at starting place:
                placeOfTetris[0] = 0;//start lying
                placeOfTetris[1] = 0;//start first row
                placeOfTetris[2] = 9;//start colmun from the left for lying tetris as in the example
            }
            else{
                placeOfTetris[2]++;
            }
        }
        system("clear");
        fillBoard();
        printBoard();
        pthread_mutex_unlock(&lock2);
    }
    if(c == 'w'){//flip tetris to the right
        pthread_mutex_lock(&lock2);
        //rotate right if can:
        if(rotateTetrisIfCan() == 1){
            system("clear");
            fillBoard();
            printBoard();
        }
        pthread_mutex_unlock(&lock2);
    }
    if(c == 'q'){//quit game
        pthread_mutex_lock(&lock2);
        isQ = 1;
        system("clear");
        write(1,">",1);//because the system clears the arrow for the next command(normal bash command after quit this program)
        pthread_mutex_unlock(&lock2);
    }
    if(isQ == 0)//only when not done yet(got q from pipe) - we continue getting signals
        signal(SIGUSR2,signal_hand);
}

int main(){
    int i;//for calc
    /*maybe send signal to ex51.c and pauses it till then(until we start getting signals here) if we don't get here the signal in the desired time*/
    //create board(tetris will be 3 '-' chars in it, the frame will be '*' char and empty cells will be ' ' chars):
    tetrisBoard = malloc(sizeof(char*)*20);
    for(i = 0; i < 20; i++){
        tetrisBoard[i] = malloc(sizeof(char)*20);
    }
    //init tetris:
    placeOfTetris[0] = 0;//start lying
    placeOfTetris[1] = 0;//start first row
    placeOfTetris[2] = 9;//start colmun from the left for lying tetris as in the example
    //create locks to drop tetris character(1 in sec):
    if(pthread_mutex_init(&lock1,NULL) != 0){
        printErr();
    }
    if(pthread_mutex_init(&lock2,NULL) != 0){
        printErr();
    }
    //first time print board:
    pthread_mutex_lock(&lock2);
    system("clear");
    fillBoard();
    printBoard();
    pthread_mutex_unlock(&lock2);
    //init a signal to get from ex51:
    signal(SIGUSR2,signal_hand);
    //start dropping tetris:
    signal(SIGALRM,dropTetrisEachSecond);
    alarm(1);
    //pause until we get user input(except dropping):
    while(isQ == 0){
        pause();
    }
    //free memory:
    for(i = 0; i < 20; i++){
        free(tetrisBoard[i]);
    }
    free(tetrisBoard);
    if(pthread_mutex_destroy(&lock1) != 0){
        printErr();
    }
    if(pthread_mutex_destroy(&lock2) != 0){
        printErr();
    }
    return 0;
}