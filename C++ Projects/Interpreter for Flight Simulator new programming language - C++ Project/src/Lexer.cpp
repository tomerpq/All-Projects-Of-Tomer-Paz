using namespace std;
/*Lexer- lex function implemenataion:*/
#include "Lexer.h"
/*constructor that saves the file name in member:*/
Lexer::Lexer(string fileName) {
    this->fileName = fileName;
}
/*getter:*/
int Lexer::getNum() {
    return num;
}
/*return the string array with the commands:*/
string* Lexer::lex() {
    string* commands;
    string line;
    int index = 0;
    ifstream file(this->fileName);
    if(file.is_open()){
        //getting number of strings to be lexed:
        while(getline(file,line)){
            bool addStr = false;/*indicates if we need to add the next string to the array*/
            string str = "";//string to add
            while(line.size() > 0){//searching for strings(addresses)
                if(line[0] == '"'){
                    line = line.substr(1);
                    line = line.substr(line.find('"')+1);
                    index++;
                }
                else if(line[0] == ' '){//continuing the search for spaces
                    if(str.compare("") != 0){
                        addStr = true;
                    }
                    line = line.substr(1);
                }
                else{
                    if(line[0] == '=' || line[0] == '+' || line[0] == '-' ||
                       line[0] == '*' || line[0] == '/' || line[0] == '<' || line[0] == '>' || line[0] == '{' || line[0] == '}'
                       || line[0] == '!' || line[0] == '(' || line[0] == ')'){
                        if(str.compare("") != 0) {//add the recent string.
                            str = "";
                            index++;
                            addStr = false;
                        }
                        // and now we take care of operator string:
                        if((line.substr(1).size() > 0) && (line[0] == '=' || line[0] == '+' || line[0] == '-' ||
                                                           line[0] == '*' || line[0] == '/' || line[0] == '<' || line[0] == '>'
                                                           || line[0] == '!') &&
                           (line[1] == '=' || line[1] == '+' || line[1] == '-' ||
                            line[1] == '*' || line[1] == '/' || line[1] == '<' || line[1] == '>'
                            || line[1] == '!')){//checking for double operator(like "==" , "++"...
                            line = line.substr(2);
                            index++;
                        }
                        else{//1 string operator
                            line = line.substr(1);
                            index++;
                        }
                    }
                    else{
                        str.append(line.substr(0,1));
                        line = line.substr(1);
                    }
                }
                if(addStr){//incase we had string and then space
                    addStr = false;
                    str = "";
                    index++;
                }
            }
            if(str.compare("") != 0){//incase we had string and no spaces at all
                index++;
            }
        }
        //rewind file:
        file.clear();
        file.seekg(0);
        commands = new string[index];
        index = 0;
        //putting the commands in the new string array:
        while(getline(file,line)){
            bool addStr = false;/*indicates if we need to add the next string to the array*/
            string str = "";//string to add
            while(line.size() > 0){
                if(line[0] == '"'){//searching for strings(addresses)
                    commands[index] = line.substr(0,line.substr(1).find('"')+2);
                    line = line.substr(1);
                    line = line.substr(line.find('"')+1);
                    index++;
                }
                else if(line[0] == ' '){//continuing the search for spaces
                    if(str.compare("") != 0){
                        addStr = true;
                    }
                    line = line.substr(1);
                }
                else{
                    if(line[0] == '=' || line[0] == '+' || line[0] == '-' ||
                       line[0] == '*' || line[0] == '/' || line[0] == '<' || line[0] == '>' || line[0] == '{' || line[0] == '}'
                       || line[0] == '!' || line[0] == '(' || line[0] == ')'){
                        if(str.compare("") != 0) {//add the recent string.
                            commands[index] = str;
                            str = "";
                            index++;
                            addStr = false;
                        }
                        // and now we take care of operator string:
                        if((line.substr(1).size() > 0) && (line[0] == '=' || line[0] == '+' || line[0] == '-' ||
                                                           line[0] == '*' || line[0] == '/' || line[0] == '<' || line[0] == '>'
                                                           || line[0] == '!') &&
                           (line[1] == '=' || line[1] == '+' || line[1] == '-' ||
                            line[1] == '*' || line[1] == '/' || line[1] == '<' || line[1] == '>'
                            || line[1] == '!')){//checking for double operator(like "==" , "++"...
                            commands[index] = line.substr(0,2);
                            line = line.substr(2);
                            index++;
                        }
                        else{//1 string operator
                            commands[index] = line.substr(0,1);
                            line = line.substr(1);
                            index++;
                        }
                    }
                    else{
                        str.append(line.substr(0,1));
                        line = line.substr(1);
                    }
                }
                if(addStr){//incase we had string and then space
                    commands[index] = str;
                    addStr = false;
                    str = "";
                    index++;
                }
            }
            if(str.compare("") != 0){//incase we had string and no spaces at all
                commands[index] = str;
                index++;
            }
        }
        this->num = index;
        file.close();
    }
    else throw invalid_argument("File cannot be opened!");
    return commands;
}