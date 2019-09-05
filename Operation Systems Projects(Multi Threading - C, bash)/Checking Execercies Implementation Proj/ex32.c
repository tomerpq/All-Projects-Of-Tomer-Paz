/* Tomer Paz 315311365 */
//fix - delete prints, put open and close for results.csv in every write place and add \n in the end of the write string. fix malloc of getpath
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <ctype.h>
#include <string.h>
#include <dirent.h>
#include <sys/stat.h>
#include <wait.h>

#define  TIMELIMIT 5

int timeout = 0;
int childDone = 0;
char cFilePath[151];
char cFileTmp[151];

int isDir(char* path);
void cFilePathFinder(char* path,int flag);
void alarmHandler();
void childHandler();

/*checks if file is a dir:*/
int isDir(char* path) {
    struct stat dirChecker;
    stat(path, &dirChecker);
    return S_ISDIR(dirChecker.st_mode);
}
/*works by DFS - updtaes the full c file path (with the starting of the line 1 in configuration file for path of student or "" if doesn't exist):*/
void cFilePathFinder(char* path,int flag){
    DIR* dirss;
    struct dirent* dirents;
    if(flag == 1){
        strcpy(cFilePath,"");//may be changed if we find a .c file inside studentPath
        chdir(path);
    }
    if ((dirss = opendir(".")) == NULL) {
        write(2, "Error in system call: problem in opening a directory in finder\n",
              sizeof("Error in system call: problem in opening a directory in finder\n") - 1);
        exit(1);
    }
    while ((dirents = readdir(dirss)) != NULL) {
        if(!(strcmp(dirents->d_name,"..") == 0 || strcmp(dirents->d_name,".") == 0)){
        if(isDir(dirents->d_name)){
            chdir(dirents->d_name);
            cFilePathFinder("",0);
        }
        else{//may be .c file
            getcwd(cFileTmp,151);
            strcat(cFileTmp,"/");
            strcat(cFileTmp,dirents->d_name);
            if(strlen(cFileTmp) >= 2){
                if(cFileTmp[strlen(cFileTmp)-2] == '.'){
                    if(cFileTmp[strlen(cFileTmp)-1] == 'c'){
                        strcpy(cFilePath,cFileTmp);
                    }
                }
            }
        }
    }}
    chdir("..");
    //closing current dir:
    if ((closedir(dirss)) == -1) {
        write(2, "Error in system call: problem in closing the current directory\n",
              sizeof("Error in system call: problem in closing the current directory\n") - 1);
        exit(1);
    }
}
/*for timeout:*/
void childHandler(){
    childDone = 1;
}

void alarmHandler(){
    timeout = 1;
}

int main(int argc, char* argv[]) {
    //variables as they are named, no more explanation needed
    pid_t pid;
    DIR *studentsDir;
    struct dirent *studentsDirent;
    struct dirent *studentsDirent2;
    int status, exitStatus,exitStatusTmp;
    int i,j;//for loops
    int cntDirs = 0,cntLoop = 0;
    int argFile, results, inputFile, outputFile;//fds
    char argBuf[451];
    char cwd[151];
    char executableName[151];
    char studentDirectPath[151];
    char *direct;
    char *writeStringAllocate;
    char *executableNameAllocate;
    char *input;
    char *correctOutput;
    char ***args;//for execvp
    char** dirs;//the directories names
    char** names;//the cPaths
    if (argc != 2) {//checking arguments
        write(2, "Wrong number of arguments!\n", sizeof("Wrong number of arguments!\n") - 1);
        exit(1);
    }
    //opening configuration file:
    argFile = open(argv[1], O_RDONLY);
    if (argFile < 0) {
        write(2, "Error in system call: Problem in opening configuration file argument!\n",
              sizeof("Error in system call: Problem in opening configuration file argument!\n") - 1);
        exit(1);
    }
    //reading the lines from the configuration file and splitting:
    read(argFile, argBuf, 450);//all 3 lines toghether are less then 450 chars.
    direct = strtok(argBuf, "\n");
    input = strtok(NULL, "\n");
    correctOutput = strtok(NULL, "\n");
    //closing configuration file:
    if (close(argFile) < 0) {
        write(2, "Error in system call: Problem in closing configuration file argument!\n",
              sizeof("Error in system call: Problem in closing configuration file argument!\n") - 1);
        exit(1);
    }
    //making the results file to be written into:
    results = creat("results.csv", S_IRWXG | S_IRWXO | S_IRWXU);
    if (results < 0) {
        write(2, "Error in system call: Problem in making and opening results file!\n",
              sizeof("Error in system call: Problem in making and opening results file!\n") - 1);
        exit(1);
    }
    //closing results file:
    if (close(results) < 0) {
        write(2, "Error in system call: Problem in closing results file!\n",
              sizeof("Error in system call: Problem in closing results file!\n") - 1);
        exit(1);
    }
    //opening the directory direct and making the student's grades,our main code:
    if ((studentsDir = opendir(direct)) == NULL) {
        write(2, "Error in system call: problem in opening the students directory\n",
              sizeof("Error in system call: problem in opening the students directory\n") - 1);
        exit(1);
    }
    while ((studentsDirent = readdir(studentsDir)) != NULL) {
        if(strcmp(studentsDirent->d_name,".") == 0 || strcmp(studentsDirent->d_name,"..") == 0){
            continue;
        }
        cntDirs++;
    }

    dirs = malloc(sizeof(char*)*cntDirs);
    names = malloc(sizeof(char*)*cntDirs);
    for(i = 0; i < cntDirs; i++){
        dirs[i] = malloc(sizeof(char)*151);
        names[i] = malloc(sizeof(char)*151);
    }
    cntDirs = 0;
    if ((closedir(studentsDir)) == -1) {
        write(2, "Error in system call: problem in closing the students directory\n",
              sizeof("Error in system call: problem in closing the students directory\n") - 1);
        exit(1);
    }
    if ((studentsDir = opendir(direct)) == NULL) {
        write(2, "Error in system call: problem in opening the students directory\n",
              sizeof("Error in system call: problem in opening the students directory\n") - 1);
        exit(1);
    }

    //make the names and c files ready:
    while ((studentsDirent2 = readdir(studentsDir)) != NULL) {
        if(!(strcmp(studentsDirent2->d_name,"..") == 0 || strcmp(studentsDirent2->d_name,".") == 0)) {
            //now recursively search for c file in it and its sub directories:
            strcpy(dirs[cntDirs], studentsDirent2->d_name);
            strcpy(studentDirectPath, direct);
            strcat(studentDirectPath, "/");
            strcat(studentDirectPath, dirs[cntDirs]);
            getcwd(cwd, 151);
            cFilePathFinder(studentDirectPath, 1);
            chdir(cwd);
            if(strcmp(cFilePath,"") == 0){
                free(names[cntDirs]);
                names[cntDirs] = NULL;
            }
            else{
                strcpy(names[cntDirs],cFilePath);
            }
            cntDirs++;
        }
    }
    if ((closedir(studentsDir)) == -1) {
        write(2, "Error in system call: problem in closing the students directory\n",
              sizeof("Error in system call: problem in closing the students directory\n") - 1);
        exit(1);
    }
    args = malloc(sizeof(char**)*(3*cntDirs));
    for(i = 0; i < cntDirs; i++){
        args[(i*3)] = malloc(sizeof(char*)*4);
        for (j = 0; j < 4; j++) {
            args[(i*3)][j] = malloc(sizeof(char) * 151);
        }
        strcpy(args[(i*3)][0], "gcc");
        strcpy(args[(i*3)][1], "-o");
        strcpy(executableName, dirs[i]);
        strcat(executableName, ".out");
        strcpy(args[(i*3)][2], executableName);
        if(names[i]){
            strcpy(args[(i*3)][3], names[i]);
        }
        else{
            strcpy(args[(i*3)][3],"");
        }
        args[(i*3)][4] = NULL;
        args[(i*3 +1)] = malloc(sizeof(char*)*1);
        for (j = 0; j < 1; j++) {
            args[(i*3 +1)][j] = malloc(sizeof(char) * 151);
        }
        strcpy(executableName, "./");
        strcat(executableName, dirs[i]);
        strcat(executableName, ".out");
        strcpy(args[(i*3 +1)][0], executableName);
        args[(i*3 +1)][1] = NULL;
        args[(i*3 +2)] = malloc(sizeof(char*)*3);
        for (j = 0; j < 3; j++) {
            args[(i*3 +2)][j] = malloc(sizeof(char) * 151);
        }
        strcpy(args[(i*3 +2)][0],"./comp.out");
        strcpy(args[(i*3 +2)][1],correctOutput);
        strcpy(args[(i*3 +2)][2],"output.txt");
        args[(i*3 +2)][3] = NULL;
    }
    while (cntLoop < cntDirs) {//for each student:
        //making the write string to our results file:
        if(names[cntLoop]){
            strcpy(cFilePath,names[cntLoop]);
        }
        else{
            strcpy(cFilePath,"");
        }
        if (strcmp(cFilePath,"") == 0) {//grade 0 - no c file found
            writeStringAllocate = malloc(sizeof(char) * 300);
            strcpy(writeStringAllocate, dirs[cntLoop]);
            strcat(writeStringAllocate, ",");
            strcat(writeStringAllocate, "0,NO_C_FILE\n");
            results = open("results.csv",O_WRONLY | O_APPEND);
            if(results < 0){
                write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                exit(1);
            }
            if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) != strlen(writeStringAllocate)) {
                free(writeStringAllocate);
                write(2, "Error in system call: problem in writing one of the students grades to results.csv file\n",
                      sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -
                      1);
                exit(1);
            }
            free(writeStringAllocate);
            //closing results file:
            if (close(results) < 0) {
                write(2, "Error in system call: Problem in closing results file!\n",
                      sizeof("Error in system call: Problem in closing results file!\n") - 1);
                exit(1);
            }
        } else {//there is c file for that student and the full path is cFilePath
            //make args to compile it:


            pid = fork();
            if (pid < 0) {
                for (i = 0; i < 4; i++) {
                    free(args[i]);
                }
                free(args);
                write(2, "Error in system call: problem in making a child process\n",
                      sizeof("Error in system call: problem in making a child process\n") - 1);
                exit(1);
            }
            if (pid == 0) {//child process:
                execvp("gcc", args[(cntLoop*3)]);
                write(2, "Error in system call: problem in compiling system call\n",
                      sizeof("Error in system call: problem in compiling system call\n") - 1);
                exit(2);//1 for compliation error
            }
            if (pid > 0) {//father process:
                //compiling:
                if (waitpid(pid, &status, 0) != pid) {
                    for (i = 0; i < 4; i++) {
                        free(args[i]);
                    }
                    free(args);
                    write(2, "Error in system call: problem in waiting to child process\n",
                          sizeof("Error in system call: problem in waiting to child process\n") - 1);
                    exit(1);
                }
                //finished compiling and son process
                exitStatus = WEXITSTATUS(status);
                if (exitStatus == 1) {//compilation error - grade 20
                    writeStringAllocate = malloc(sizeof(char) * 300);
                    strcpy(writeStringAllocate, dirs[cntLoop]);
                    strcat(writeStringAllocate, ",");
                    strcat(writeStringAllocate, "20,COMPILATION_ERROR\n");
                    results = open("results.csv",O_WRONLY | O_APPEND);
                    if(results < 0){
                        write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                        exit(1);
                    }
                    if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) !=
                        strlen(writeStringAllocate)) {
                        free(writeStringAllocate);
                        write(2,
                              "Error in system call: problem in writing one of the students grades to results.csv file\n",
                              sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -
                              1);
                        exit(1);
                    }
                    //closing results file:
                    if (close(results) < 0) {
                        write(2, "Error in system call: Problem in closing results file!\n",
                              sizeof("Error in system call: Problem in closing results file!\n") - 1);
                        exit(1);
                    }
                    free(writeStringAllocate);
                }
                else {//no compilation error, lets try to run and see if there's timout:



                    pid = fork();
                    if (pid < 0) {
                        for (i = 0; i < 1; i++) {
                            free(args[i]);
                        }
                        free(args);
                        //delete compiled file with unlink:
                        executableNameAllocate = malloc(sizeof(char) * 155);
                        strcpy(executableNameAllocate, dirs[cntLoop]);
                        strcat(executableNameAllocate, ".out");
                        write(2, "Error in system call: problem in making a child process\n",
                              sizeof("Error in system call: problem in making a child process\n") - 1);
                        if (unlink(executableNameAllocate) != 0) {
                            write(2, "Error in system call: problem in deleting runnable file\n",
                                  sizeof("Error in system call: problem in deleting runnable file\n"));
                        }
                        free(executableNameAllocate);
                        exit(1);
                    }
                    if (pid == 0) {//child process:
                        outputFile = creat("output.txt", S_IRWXG | S_IRWXO | S_IRWXU);
                        if (outputFile < 0) {
                            write(2, "Error in system call: Problem in making and opening output file!\n",
                                  sizeof("Error in system call: Problem in making and opening output file!\n") - 1);
                            exit(42);//special for determining if need to delete the output.txt file or not
                        }
                        inputFile = open(input, O_RDONLY);
                        if (inputFile < 0) {
                            write(2, "Error in system call: Problem in opening input file argument!\n",
                                  sizeof("Error in system call: Problem in opening input file argument!\n") - 1);
                            exit(1);
                        }
                        //replace stdin and stdout with input and output files
                        dup2(inputFile, 0);
                        dup2(outputFile, 1);
                        //close fds:
                        if (close(inputFile) < 0) {
                            write(2, "Error in system call: Problem in closing input file!\n",
                                  sizeof("Error in system call: Problem in closing input file!\n") - 1);
                            exit(1);
                        }
                        if (close(outputFile) < 0) {
                            write(2, "Error in system call: Problem in closing output file!\n",
                                  sizeof("Error in system call: Problem in closing output file!\n") - 1);
                            exit(1);
                        }
                        execvp(args[(cntLoop*3 +1)][0], args[(cntLoop*3 +1)]);
                        write(2, "Error in system call: problem in running system call\n",
                              sizeof("Error in system call: problem in running system call\n") - 1);
                        exit(1);
                    }
                    if (pid > 0) {//father process:
                        //running the file:
                        //install alarm for timeout of 5 seconds:
                        signal(SIGALRM, alarmHandler);
                        signal(SIGCHLD, childHandler);
                        alarm(TIMELIMIT);  // install an alarm to be fired after TIME_LIMIT
                        pause();
                        if(timeout){
                            results = open("results.csv",O_WRONLY | O_APPEND);
                            if(results < 0){
                                write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                                exit(1);
                            }
                            writeStringAllocate = malloc(sizeof(char) * 300);
                            strcpy(writeStringAllocate, dirs[cntLoop]);
                            strcat(writeStringAllocate, ",");
                            strcat(writeStringAllocate, "40,TIMEOUT\n");
                            if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) !=
                                strlen(writeStringAllocate)) {
                                free(writeStringAllocate);
                                for (i = 0; i < 1; i++) {
                                    free(args[i]);
                                }
                                free(args);
                                write(2,"Error in system call: problem in writing one of the students grades to results.csv file\n",sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -1);
                                exit(1);
                            }
                            //closing results file:
                            if (close(results) < 0) {
                                write(2, "Error in system call: Problem in closing results file!\n",
                                      sizeof("Error in system call: Problem in closing results file!\n") - 1);
                                exit(1);
                            }
                            free(writeStringAllocate);
                            //unlink output.txt and .out file:
                            executableNameAllocate = malloc(sizeof(char) * 155);
                            strcpy(executableNameAllocate, dirs[cntLoop]);
                            strcat(executableNameAllocate, ".out");
                            if (unlink(executableNameAllocate) != 0) {
                                write(2, "Error in system call: problem in deleting runnable file\n",
                                      sizeof("Error in system call: problem in deleting runnable file\n"));\
                                free(executableNameAllocate);
                                free(writeStringAllocate);
                                exit(1);
                            }
                            free(executableNameAllocate);

                            if (unlink("output.txt") != 0) {
                                free(writeStringAllocate);
                                write(2, "Error in system call: problem in deleting output temp file\n",
                                      sizeof("Error in system call: problem in deleting output temp file\n"));
                                exit(1);
                            }

                        }
                        else if(childDone){
                            if (waitpid(pid, &status, 0) != pid) {
                                for (i = 0; i < 1; i++) {
                                    free(args[i]);
                                }
                                free(args);
                                write(2, "Error in system call: problem in waiting to child process\n",
                                      sizeof("Error in system call: problem in waiting to child process\n") - 1);
                                exit(1);
                            }
printf("dir[%d] = %s\n",cntLoop,dirs[cntLoop]);
                            //delete compiled file with unlink:
                            executableNameAllocate = malloc(sizeof(char) * 155);
                            strcpy(executableNameAllocate, dirs[cntLoop]);
                            strcat(executableNameAllocate, ".out");
                            if (unlink(executableNameAllocate) != 0) {
                                write(2, "Error in system call: problem in deleting runnable file\n",
                                      sizeof("Error in system call: problem in deleting runnable file\n"));\
                                free(executableNameAllocate);
                                exit(1);
                            }
                            free(executableNameAllocate);
                            exitStatus = WEXITSTATUS(status);
                            if (exitStatus != 1 &  exitStatus != 42) {//no problem in son process
                                //lets see how good is the output:


                                pid = fork();
                                if(pid < 0){
                                    for (i = 0; i < 3; i++) {
                                        free(args[i]);
                                    }
                                    free(args);
                                    write(2, "Error in system call: problem in making a child process\n",
                                          sizeof("Error in system call: problem in making a child process\n") - 1);
                                    exit(1);
                                }
                                if(pid == 0){//child process
                                    execvp(args[(cntLoop*3 +2)][0],args[(cntLoop*3 +2)]);
                                    write(2, "Error in system call: problem in running comparison system call\n",
                                          sizeof("Error in system call: problem in running comparison system call\n") - 1);
                                    exit(1);
                                }
                                if(pid > 0){//father process
                                    if (waitpid(pid, &status, 0) != pid) {
                                        for (i = 0; i < 3; i++) {
                                            free(args[i]);
                                        }
                                        free(args);
                                        write(2, "Error in system call: problem in waiting to child process\n",
                                              sizeof("Error in system call: problem in waiting to child process\n") - 1);
                                        exit(1);
                                    }
                                    exitStatusTmp = WEXITSTATUS(status);
                                    if(exitStatusTmp == 4){//problem comparing
                                        for (i = 0; i < 3; i++) {
                                            free(args[i]);
                                        }
                                        free(args);
                                        write(2,"problem in comparing c file\n",sizeof("problem in comparing c file\n")-1);
                                        exit(1);
                                    }
                                    else if(exitStatusTmp == 2){//different
                                        writeStringAllocate = malloc(sizeof(char) * 300);
                                        strcpy(writeStringAllocate, dirs[cntLoop]);
                                        strcat(writeStringAllocate, ",");
                                        strcat(writeStringAllocate, "60,BAD_OUTPUT\n");
                                        results = open("results.csv",O_WRONLY | O_APPEND);
                                        if(results < 0){
                                            write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                                            exit(1);
                                        }
                                        if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) !=
                                            strlen(writeStringAllocate)) {
                                            free(writeStringAllocate);
                                            for (i = 0; i < 3; i++) {
                                                free(args[i]);
                                            }
                                            free(args);
                                            write(2,"Error in system call: problem in writing one of the students grades to results.csv file\n",sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -1);
                                            exit(1);
                                        }
                                        if (close(results) < 0) {
                                            write(2, "Error in system call: Problem in closing results file!\n",
                                                  sizeof("Error in system call: Problem in closing results file!\n") - 1);
                                            exit(1);
                                        }
                                        free(writeStringAllocate);
                                    }
                                    else if(exitStatusTmp == 3){//similar
                                        writeStringAllocate = malloc(sizeof(char) * 300);
                                        strcpy(writeStringAllocate, dirs[cntLoop]);
                                        strcat(writeStringAllocate, ",");
                                        strcat(writeStringAllocate, "80,SIMILAR_OUTPUT\n");
                                        results = open("results.csv",O_WRONLY | O_APPEND);
                                        if(results < 0){
                                            write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                                            exit(1);
                                        }
                                        if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) !=
                                            strlen(writeStringAllocate)) {
                                            free(writeStringAllocate);
                                            for (i = 0; i < 3; i++) {
                                                free(args[i]);
                                            }
                                            free(args);
                                            write(2,"Error in system call: problem in writing one of the students grades to results.csv file\n",sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -1);
                                            exit(1);
                                        }
                                        if (close(results) < 0) {
                                            write(2, "Error in system call: Problem in closing results file!\n",
                                                  sizeof("Error in system call: Problem in closing results file!\n") - 1);
                                            exit(1);
                                        }
                                        free(writeStringAllocate);
                                    }
                                    else if(exitStatusTmp == 1){//identical
                                        results = open("results.csv",O_WRONLY | O_APPEND);
                                        if(results < 0){
                                            write(2,"Error in system call: problem in opening results.csv\n",sizeof("Error in system call: problem in opening results.csv\n")-1);
                                            exit(1);
                                        }
                                        writeStringAllocate = malloc(sizeof(char) * 300);
                                        strcpy(writeStringAllocate, dirs[cntLoop]);
                                        strcat(writeStringAllocate, ",");
                                        strcat(writeStringAllocate, "100,GREAT_JOB\n");
                                        if ((write(results, writeStringAllocate, strlen(writeStringAllocate))) !=
                                            strlen(writeStringAllocate)) {
                                            free(writeStringAllocate);
                                            for (i = 0; i < 3; i++) {
                                                free(args[i]);
                                            }
                                            free(args);
                                            write(2,"Error in system call: problem in writing one of the students grades to results.csv file\n",sizeof("Error in system call: problem in writing one of the students grades to results.csv file\n") -1);
                                            exit(1);
                                        }
                                        //closing results file:
                                        if (close(results) < 0) {
                                            write(2, "Error in system call: Problem in closing results file!\n",
                                                  sizeof("Error in system call: Problem in closing results file!\n") - 1);
                                            exit(1);
                                        }
                                        free(writeStringAllocate);
                                    }
                                }
                            }
                            if(exitStatus != 42){
                                if (unlink("output.txt") != 0) {
                                    write(2, "Error in system call: problem in deleting output temp file\n",
                                          sizeof("Error in system call: problem in deleting output temp file\n"));
                                    exit(1);
                                }
                            }
                        }
                    }
                }
            }
        }

    cntLoop++; timeout = 0; childDone = 0;}
    for(i = 0; i < cntDirs; i++){
        free(dirs[i]);
        if(names[i])
            free(names[i]);
    }
    free(dirs);
    free(names);
    return 0;
}
