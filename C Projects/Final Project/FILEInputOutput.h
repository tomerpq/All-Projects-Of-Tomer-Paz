/*File header- just the copy of the declartions. */

#ifndef FILEINPUTOUTPUT_H_
#define FILEINPUTOUTPUT_H_

int getOffset2(double x);
void saveToFile(char* fileName,double** table,int m,int n,int mode);
int* loadFromFileMN(char* fileName);
void loadFromFile(char* fileName,double** table,int mode,int N);
double** makeTable(int N);
void copyTable(double** toTable,double** fromTable,int N);

#endif /* FILEINPUTOUTPUT_H_ */
