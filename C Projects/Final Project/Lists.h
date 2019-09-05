/*Lists header. the structures needed for the list and the functions declartions. */

#ifndef LISTS_H_
#define LISTS_H_

/*node of the list containing the data listed in the "c" file. */
typedef struct listNode{
	double** table;
	int* newCell;/*the new cells value*/
	int* oldCell;/*the old cells value*/
	/*the place of the changes in 2d array terms*/
	int* placeChangeI;
	int* placeChangeJ;
	int numOfChanges;/*the size for the above int arrays(number of changes)*/
	/*next and prev for doubly link */
	struct listNode* next;
	struct listNode* prev;
}LNODE;

/*the list structure. contains head pointing to the start and tail to the end. */
typedef struct List{
	LNODE* head;
	LNODE* tail;
}LIST;

LIST* insertMove(LIST* list,double** table,int* oldCell,int* newCell,int* placeChangeI,int* placeChangeJ,int numOfChanges);
LIST* destroyListFromPointer(LIST* list,int fromStart,int N);
int moveForward(LIST* list);
int moveBackward(LIST* list);
void destroyTable(double** table,int N);
double** getTable(LIST* list);
int getOldCell(LIST* list,int i);
int getNewCell(LIST* list,int i);
int getPlaceChangeI(LIST* list,int i);
int getPlaceChangeJ(LIST* list,int i);
int oldCellEmpty(LIST* list,int i);
int newCellEmpty(LIST* list,int i);
int getNumOfChanges(LIST* list);
void setNumberOfChanges(LIST* list,int numOfChanges);

#endif /* LISTS_H_ */
