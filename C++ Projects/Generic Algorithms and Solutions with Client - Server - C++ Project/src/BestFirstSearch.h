#ifndef MILESTONE2_BESTFS_H
#define MILESTONE2_BESTFS_H

#include "Searcher.h"

/*BestFirstSearch algorithm */
template<class T>
class BestFirstSearch : public Searcher<T>{
public:
    ISolutionable<T>* search(ISearchable<T>* searchable);
    ~BestFirstSearch(){
        delete this->hashSet;
        delete this->IS;
    }
    BestFirstSearch(ISolutionable<T>* IS,HashSetI<State<T>>* HS){
        this->hashSet = HS;
        this->IS = IS;
    }
};


template<class T>
ISolutionable<T>* BestFirstSearch<T>::search(ISearchable<T> *searchable) {
    this->pushHeuristicForBest(searchable->getInitialState());
    this->pushHashSet(searchable->getInitialState());
    while(this->heuristicForBestSize() > 0){
        State<T>* n = this->popHeuristicForBest();
        if(*n == *(searchable->getGoalState())){
            this->IS->setMember(n);
            this->clearHeuristicForBest();
            this->hashSet->clearSet();
            return this->IS;
        }
        std::list<State<T>*> successors = searchable->getAllPossibleStates(n);
        for(State<T>* state : successors){
            if(!(this->containsHashSet(state))){
                this->pushHashSet(state);
                this->pushHeuristicForBest(state);
            }
        }
    }
    return nullptr;//no solution!(bug)
}

#endif
