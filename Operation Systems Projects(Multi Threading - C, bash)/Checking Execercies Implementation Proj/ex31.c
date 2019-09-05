/* Tomer Paz 315311365 */

#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>

int main(int argc,char* argv[]) {
    int rtr = 1;//will be changed to 3 or to 2 in the worst case(the files are different) in case it will be changed to 2 - we will exit loop and won't waste any more time
    int arg1, arg2, readStat = 3;//the file arguments to be open, readStat to determine the status of current reading: 3 for both, 2 for arg2 only, 1 for arg1 only, 0 for none
    char buf1[10];
    char buf2[10];
    char a,b;//tmps
    if(argc != 3){//checking arguments
        write(2,"Error in system call: Wrong number of arguments!\n",sizeof("Error in system call: Wrong number of arguments!\n")-1);
        exit(4);
    }
    //opening:
    arg1 = open(argv[1],O_RDONLY);
    if(arg1 < 0){
        write(2,"Error in system call: Problem in opening first file argument!\n",sizeof("Error in system call: Problem in opening first file argument!\n")-1);
        exit(4);
    }
    arg2 = open(argv[2],O_RDONLY);
    if(arg2 < 0){
        write(2,"Error in system call: Problem in opening second file argument!\n",sizeof("Error in system call: Problem in opening second file argument!\n")-1);
        exit(4);
    }
    //our main code for ex31:
    while(readStat > 0){
        if(readStat == 1 || readStat == 3){
            if(read(arg1,buf1,1) <= 0){
                if(readStat == 3){
                    readStat = 2;
                }
                else{//its 1
                    readStat = 0;
                }
            }
        }
        if(readStat == 2 || readStat == 3){
            if(read(arg2,buf2,1) <= 0){
                if(readStat == 3){
                    readStat = 1;
                }
                else{//its 2
                    readStat = 0;
                }
            }
        }
        //now we have read(or not) from both files, lets check cases:
        if(readStat == 3){//both read a next char
            if(!(buf1[0] == buf2[0])){//may be semi equal from now
                rtr = 3;
                //before we change we save tmps:
                a = buf1[0];
                b = buf2[0];
                if((!(a == ' ')) && (!(a == '\t')) && (!(a == '\n')) && (!(b == ' ')) && (!(b == '\t')) && (!(b == '\n'))){//not spaces for both - check maybe same letter
                    a = (char)(tolower(a));
                    b = (char)(tolower(b));
                    if(a != b){
                        rtr = 2;
                        break;
                    }
                }
                if(buf1[0] == ' ' || buf1[0] == '\n' || buf1[0] == '\t'){
                    while(1){
                        if(read(arg1,buf1,1) <= 0){//reached end of file before not space char
                            if(readStat == 3){
                                readStat = 2;
                            }
                            else if(readStat == 1){
                                readStat = 0;
                            }
                            break;
                        }
                        else{
                            if(buf1[0] == ' ' || buf1[0] == '\n' || buf1[0] == '\t'){//skip spaces:
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
                if(buf2[0] == ' ' || buf2[0] == '\n' || buf2[0] == '\t'){
                    while(1){
                        if(read(arg2,buf2,1) <= 0){//reached end of file before not space char
                            if(readStat == 3){
                                readStat = 1;
                            }
                            else if(readStat == 2){
                                readStat = 0;
                            }
                            break;
                        }
                        else{
                            if(buf2[0] == ' ' || buf2[0] == '\n' || buf2[0] == '\t'){//skip spaces:
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
                if(readStat == 3){
                    a = (char)(tolower(buf1[0]));
                    b = (char)(tolower(buf2[0]));
                    if(a != b){
                        rtr = 2;
                        break;
                    }
                }
                if(readStat == 2 || readStat == 1){
                    rtr = 2;
                    break;
                }
            }
        }
        else if(readStat == 2){//file 2 succeded reading and file 1 not
            rtr = 3;
            b = buf2[0];
            if((!(b == ' ')) && (!(b == '\n')) && (!(b == '\t'))){
                rtr = 2;
                break;
            }
            while(1){
                if(read(arg2,buf2,1) <= 0){//reached end of file before not space char
                    if(readStat == 3){
                        readStat = 1;
                    }
                    else if(readStat == 2){
                        readStat = 0;
                    }
                    break;
                }
                else{
                    if(buf2[0] == ' ' || buf2[0] == '\n' || buf2[0] == '\t'){//skip spaces:
                        continue;
                    }
                    else{
                        break;
                    }
                }
            }
            if(readStat == 2){
                rtr = 2;
                break;
            }
        }
        else if(readStat == 1){//file 1 succeded reading and file 2 not
            rtr = 3;
            a = buf1[0];
            if((!(a == ' ')) && (!(a == '\n')) && (!(a == '\t'))){
                rtr = 2;
                break;
            }
            while(1){
                if(read(arg1,buf1,1) <= 0){//reached end of file before not space char
                    if(readStat == 3){
                        readStat = 2;
                    }
                    else if(readStat == 1){
                        readStat = 0;
                    }
                    break;
                }
                else{
                    if(buf1[0] == ' ' || buf1[0] == '\n' || buf1[0] == '\t'){//skip spaces:
                        continue;
                    }
                    else{
                        break;
                    }
                }
            }
            if(readStat == 1){
                rtr = 2;
                break;
            }
        }
    }
    //closing:
    if(close(arg1) < 0){
        write(2,"Error in system call: Problem in closing first file argument!\n",sizeof("Error in system call: Problem in closing first file argument!\n")-1);
        exit(4);
    }
    if(close(arg2) < 0){
        write(2,"Error in system call: Problem in closing second file argument!\n",sizeof("Error in system call: Problem in closing second file argument!\n")-1);
        exit(4);
    }
    return rtr;
}