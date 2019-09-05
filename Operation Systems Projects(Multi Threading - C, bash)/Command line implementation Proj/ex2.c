#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <wait.h>
#include <string.h>
#include <memory.h>
#define MaxSize 512

typedef struct{
    char j_name[MaxSize];
    pid_t j_pid;
}job;

int main() {
    int i,counter,tmpSize,argsSize,jpidlength,waitSon = 1,jobPlace = 0;
    pid_t pid;
    job* jobs = malloc(sizeof(job)*MaxSize);
    char command[MaxSize]; //command string to get from the user
    char tmp[MaxSize];
    char* word;
    char* forCd;
    char* tmpChar;
    char cwd[MaxSize];
    char lastCwd[MaxSize];
    char** args;//input from user as an argument to exec/buffer of strings
    char gersh[3] = {0};
    gersh[0] = '"';
    gersh[1] = '\n';
    gersh[2] = '\0';
    getcwd(cwd,MaxSize);
    getcwd(lastCwd,MaxSize);
    printf("> ");
    fgets(command,512,stdin);
    strcpy(tmp,command);
    word = strtok(command," \t\n");
    counter = 1;
    while(word){
        counter ++;
        word = strtok(NULL," \t\n");
    }
    argsSize = counter;
    args = malloc(sizeof(char*)*counter);
    strcpy(command,tmp);
    word = strtok(command," \t\n");
    counter = 0;
    while(word){
        tmpSize = sizeof(word);
        args[counter] = malloc(sizeof(char)*tmpSize);
        strcpy(args[counter],word);
        counter ++;
        word = strtok(NULL," \t\n");
    }
    args[counter] = NULL;
    if(args[argsSize-2][(strlen(args[argsSize-2])-1)] == '&'){//check if background command
        waitSon = 0;//no need to wait son when we will fork - it will run in the background
        //add job name:
        //adding the command name without '&' to job's name
        strcpy(jobs[jobPlace].j_name,"");
        jpidlength = 0;
        for(i = 0; i < argsSize-1; i++){
            if(i != (argsSize-2)){
                strcat(jobs[jobPlace].j_name,args[i]);
                strcat(jobs[jobPlace].j_name," ");
                jpidlength += strlen(args[i]);
            }
            else{
                if(strlen(args[i]) != 1){
                    tmpChar = malloc(sizeof(char)*MaxSize);
                    strcpy(tmpChar,args[i]);
                    tmpChar[strlen(args[i])-1] = 0;
                    strcat(jobs[jobPlace].j_name,tmpChar);
                    free(tmpChar);
                }
            }
        }
        //remove & from command so it can enter execvp:
        if(strlen(args[argsSize-2]) == 1){
            free(args[argsSize-2]);
            args[argsSize-2] = NULL;
            argsSize --;
        }
        else{
            args[argsSize-2][(strlen(args[argsSize-2])-1)] = 0;
        }
    }
    while(strcmp(args[0],"exit") != 0){//while not exit command
        if(strcmp(args[0],"jobs") == 0){//our job command
            for(i = 0; i < jobPlace; i++){
                if(waitpid(jobs[i].j_pid,NULL,WNOHANG) == 0){//process still alive:
                    printf("%d %s\n",((int)(jobs[i].j_pid)),jobs[i].j_name);
                }
            }
        }
        else if(strcmp(args[0],"cd") == 0){//our cd command, not forking - done by our father
            printf("%d\n",(int)getpid());
            //cases: -, .., ~, "good with space", kelet maybe with spaces-must remove
            if(strcmp(args[1],"-") == 0){
                chdir(lastCwd);
            }
            else if(strcmp(args[1],"~") == 0){
                chdir(getenv("HOME"));
            }
            else if(strcmp(args[1],"..") == 0) {
                chdir("..");
            }
            else if(args[1][0] == '"'){
                forCd = malloc(sizeof(char)*MaxSize);
                strcpy(forCd,"");
                strtok(tmp,gersh);
                word = strtok(NULL,gersh);
                while(word) {
                    strcat(forCd, word);
                    word = strtok(NULL, gersh);
                    printf("word = %s\n", word);
                };
                chdir(forCd);
                free(forCd);
            }
            else{//normal cd - delete spaces
                forCd = malloc(sizeof(char)*MaxSize);
                strcpy(forCd,"");
                strtok(tmp," \t\n");
                word = strtok(NULL," \t\n");
                while(word){
                    strcat(forCd,word);
                    word = strtok(NULL," \t\n");
                }
                chdir(forCd);
                free(forCd);
            }
            strcpy(lastCwd,cwd);
            getcwd(cwd,MaxSize);
        }
        else{//not cd or exit or jobs
            pid = fork();
            if(pid < 0){//fork problem..
                fprintf(stderr,"Error in system call\n");
            }
            else if(pid == 0) {//son process:
                printf("%d\n",(int)getpid());
                execvp(args[0],args);
                fprintf(stderr,"Error in system call\n");
                exit(1);
            }
            else{//pid > 0 - father process:
                if(waitSon == 1){//the father will wait for son in case of not background job
                    if(waitpid(pid,NULL,0) != pid)
                        fprintf(stderr,"Error in system call\n");
                }
                else{
                    jobs[jobPlace].j_pid = pid;
                    jobPlace ++;
                    waitSon = 1;
                }
            }
        }
        for(i = 0; i < argsSize-1; i++){
            free(args[i]);
        }
        free(args);
        printf("> ");
        fgets(command,512,stdin);
        strcpy(tmp,command);
        word = strtok(command," \t\n");
        counter = 1;
        while(word){
            counter ++;
            word = strtok(NULL," \t\n");
        }
        argsSize = counter;
        args = malloc(sizeof(char*)*counter);
        strcpy(command,tmp);
        word = strtok(command," \t\n");
        counter = 0;
        while(word){
            tmpSize = sizeof(word);
            args[counter] = malloc(sizeof(char)*tmpSize);
            strcpy(args[counter],word);
            counter ++;
            word = strtok(NULL," \t\n");
        }
        args[counter] = NULL;
        if(args[argsSize-2][(strlen(args[argsSize-2])-1)] == '&'){//check if background command
            waitSon = 0;//no need to wait son when we will fork - it will run in the background
            //add job name:
            //adding the command name without '&' to job's name
            strcpy(jobs[jobPlace].j_name,"");
            jpidlength = 0;
            for(i = 0; i < argsSize-1; i++){
                if(i != (argsSize-2)){
                    strcat(jobs[jobPlace].j_name,args[i]);
                    strcat(jobs[jobPlace].j_name," ");
                    jpidlength += strlen(args[i]);
                }
                else{
                    if(strlen(args[i]) != 1){
                        tmpChar = malloc(sizeof(char)*MaxSize);
                        strcpy(tmpChar,args[i]);
                        tmpChar[strlen(args[i])-1] = 0;
                        strcat(jobs[jobPlace].j_name,tmpChar);
                        free(tmpChar);
                    }
                }
            }
            //remove & from command so it can enter execvp:
            if(strlen(args[argsSize-2]) == 1){
                free(args[argsSize-2]);
                args[argsSize-2] = NULL;
                argsSize --;
            }
            else{
                args[argsSize-2][(strlen(args[argsSize-2])-1)] = 0;
            }
        }
    }
    //exit here:
    printf("%d\n",(int)getpid());
    //free memory and jobs:
    //jobs:
    free(jobs);
    //other memory:
    for(i = 0; i < argsSize-1; i++){
        free(args[i]);
    }
    free(args);
    exit(0);
}
