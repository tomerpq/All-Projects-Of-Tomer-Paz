#ifndef MILESTONE2_DFS_H
#define MILESTONE2_DFS_H

#include "Searcher.h"
/*DFS algorithm */
template<class T>
class DFS : public Searcher<T>{
private:
    ISolutionable<T>* searchHelper(State<T>* start,State<T>* goal,ISearchable<T> *searchable);
public:
    ISolutionable<T>* search(ISearchable<T>* searchable);
    ~DFS(){
        delete this->hashSet;
        delete this->IS;
    }
    DFS(ISolutionable<T>* IS,HashSetI<State<T>>* HS){
        this->hashSet = HS;
        this->IS = IS;
    }
};
template<class T>
ISolutionable<T>* DFS<T>::search(ISearchable<T> *searchable){
    return searchHelper(searchable->getInitialState(),searchable->getGoalState(),searchable);
}
template<class T>
ISolutionable<T>* DFS<T>::searchHelper(State<T> *start, State<T> *goal,ISearchable<T> *searchable) {
    this->pushHashSet(start);
    if(*start == *goal){
        this->IS->setMember(start);
        this->hashSet->clearSet();
        return this->IS;
    }
    for(State<T>* state : searchable->getAllPossibleStates(start)){
        if(!(this->containsHashSet(state))){
            ISolutionable<T>* is = searchHelper(state,goal,searchable);
            if(is != NULL){
                return is;
            }
        }
    }
    return NULL;//not a bug. only if we get here at the first DFS recursive call - then its a bug.
}

#endif