/*Lists module:
 * the doubly linked list will save the data:
 * an 2d double array that represents the current table.
 *newCell, oldCell and the place fo change to remember the values we changed and where in the board
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "Lists.h"


int pointToOriginal = 1;/*if this is 1, the game currently is (pointed)playing in the original board(loaded from file)
even if the list is not empty, it may be 1, because its indicating pointer to the original board.
its 0 when the pointer to somewhere in the list-that must be not empty in this case.*/

/*this inserts new node with the move and all the parameters including it as described at first in the module
 * if the list is empty it will build it and allocate it to memory with one node.
 * else it will build new node and point the tail to there. of course when building new node in both cases the lists point to there (by tail)
 * and pointToOriginal is 0 by definition */
LIST* insertMove(LIST* list,double** table,int* oldCell,int* newCell,int* placeChangeI,int* placeChangeJ,int numOfChanges){
	pointToOriginal = 0;
	if(!list){
		LIST* lstTmp = (LIST*)malloc(sizeof(LIST));
		assert(lstTmp);
		lstTmp->head = (LNODE*)malloc(sizeof(LNODE));
		assert(lstTmp->head);
		lstTmp->tail = lstTmp->head;
		lstTmp->head->table = table;
		lstTmp->head->numOfChanges = numOfChanges;
		lstTmp->head->newCell = newCell;
		lstTmp->head->oldCell = oldCell;
		lstTmp->head->placeChangeI = placeChangeI;
		lstTmp->head->placeChangeJ = placeChangeJ;
		lstTmp->head->next = NULL;
		lstTmp->head->prev = NULL;
		return lstTmp;
	}
	else{
		list->tail->next = (LNODE*)malloc(sizeof(LNODE));
		assert(list->tail->next);
		list->tail->next->prev = list->tail;
		list->tail = list->tail->next;
		list->tail->next = NULL;
		list->tail->table = table;
		list->tail->numOfChanges = numOfChanges;
		list->tail->newCell = newCell;
		list->tail->oldCell = oldCell;
		list->tail->placeChangeI = placeChangeI;
		list->tail->placeChangeJ = placeChangeJ;
		return list;
	}
}
/*if fromStart == 0 -destroy the list after not including the current pointer of the list(tail). ofcourse if pointToOriginal == 1
 * destroy all list. if fromStart == 1 also destroy all list(to be used in the end of the game or restart)*/
LIST* destroyListFromPointer(LIST* list,int fromStart,int N){
	LNODE *ptr1,*ptr2,*ptrtmp;
	if(!list)
		return NULL;
	if(pointToOriginal || fromStart){/*for reset*/
		ptr1 = list->head;
		destroyTable(ptr1->table,N);
		free(ptr1->newCell);
		free(ptr1->oldCell);
		free(ptr1->placeChangeI);
		free(ptr1->placeChangeJ);
		ptr2 = ptr1->next;
		while(ptr2){
			destroyTable(ptr2->table,N);
			free(ptr2->newCell);
			free(ptr2->oldCell);
			free(ptr2->placeChangeI);
			free(ptr2->placeChangeJ);
			ptrtmp = ptr2->next;
			free(ptr2);
			ptr2 = ptrtmp;
		}
		free(ptr1);
		free(list);
		pointToOriginal = 1;
		return NULL;
	}
	else{/*case new item will be inserted after some redos. this will happen before inserting the new node*/
		ptr1 = list->tail;
		ptr2 = ptr1->next;
		while(ptr2){
			destroyTable(ptr2->table,N);
			free(ptr2->newCell);
			free(ptr2->oldCell);
			free(ptr2->placeChangeI);
			free(ptr2->placeChangeJ);
			ptrtmp = ptr2->next;
			free(ptr2);
			ptr2 = ptrtmp;
		}
	}
	return list;
}
/*move pointer forward for redo, returns 1 if moved forward or 0 if there is no forward*/
int moveForward(LIST* list){
	if(!list)
		return 0;
	if(pointToOriginal == 1){
		pointToOriginal = 0;
		return 1;
	}
	if(!list->tail->next)
		return 0;
	list->tail = list->tail->next;
	return 1;
}
/*move pointer backward for undo, returns 1 if moved backward or 0 if there is no backward */
int moveBackward(LIST* list){
	if(pointToOriginal == 1)
		return 0;
	if(!list->tail->prev){
		pointToOriginal = 1;
		return 1;
	}
	list->tail = list->tail->prev;
	return 1;
}
/*frees all the memory of the 2d array */
void destroyTable(double** table,int N){
	int i;
	for(i = 0; i < N; i++){
		free(table[i]);
	}
	free(table);
}

/*returns the current game table the list is pointing to (tail). we will check if it is possible to call the funct before
 * so no checks in the funct.(for empty list for example) */
double** getTable(LIST* list){
	if(pointToOriginal)/*in case the game is the originalBoard we don't have here we will know that by getting NULL and then point game to originalBoard in MainAux*/
		return NULL;
	return list->tail->table;
}
/*getters methods:*/

/*returns 1 iff old cell is empty:*/
int oldCellEmpty(LIST* list,int i){
	if(list->tail->oldCell[i] == 0)
		return 1;
	return 0;
}
/*returns 1 iff new cell is empty:*/
int newCellEmpty(LIST* list,int i){
	if(list->tail->newCell[i] == 0)
		return 1;
	return 0;
}
/*if the cell isn't empty:*/
int getOldCell(LIST* list,int i){
	return list->tail->oldCell[i];
}
/*if the cell isn't empty:*/
int getNewCell(LIST* list,int i){
	return list->tail->newCell[i];
}
/*another getters:*/
int getPlaceChangeI(LIST* list,int i){
	return list->tail->placeChangeI[i];
}
int getPlaceChangeJ(LIST* list,int i){
	return list->tail->placeChangeJ[i];
}
int getNumOfChanges(LIST* list){
	return list->tail->numOfChanges;
}
/*set the numberOfChanges*/
void setNumberOfChanges(LIST* list,int numOfChanges){
	list->tail->numOfChanges = numOfChanges;
}
