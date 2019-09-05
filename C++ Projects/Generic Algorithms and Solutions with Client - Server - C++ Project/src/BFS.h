#ifndef MILESTONE2_BFS_H
#define MILESTONE2_BFS_H
#include "Searcher.h"

/*BFS algorithm */
template<class T>
class BFS : public Searcher<T>{
public:
    ISolutionable<T>* search(ISearchable<T>* searchable);
    ~BFS(){
        delete this->hashSet;
        delete this->IS;
    }
    BFS(ISolutionable<T>* IS,HashSetI<State<T>>* HS){
        this->hashSet = HS;
        this->IS = IS;
    }
};


template<class T>
ISolutionable<T>* BFS<T>::search(ISearchable<T> *searchable) {
    this->pushOpenList(searchable->getInitialState());
    this->pushHashSet(searchable->getInitialState());
    while(this->openListSize() > 0){
        State<T>* node = this->popOpenList();
        if(*node == *searchable->getGoalState()){
            this->IS->setMember(node);
            this->clearOpenList();
            this->hashSet->clearSet();
            return this->IS;
        }
        std::list<State<T>*> successors = searchable->getAllPossibleStates(node);
        for(State<T>* state : successors){
            if(!(this->containsHashSet(state))){
                this->pushHashSet(state);
                this->pushOpenList(state);
            }
        }
    }
    return nullptr;//no solution!(bug)
}

#endif