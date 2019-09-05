#ifndef MILESTONE2_ASTAR_H
#define MILESTONE2_ASTAR_H

#include "Searcher.h"

/*AStar algorithm */
template<class T>
class AStar : public Searcher<T>{
public:
    ISolutionable<T>* search(ISearchable<T>* searchable);
    ~AStar(){
        delete this->hashSet;
        delete this->IS;
    }
    AStar(ISolutionable<T>* IS,HashSetI<State<T>>* HS){
        this->hashSet = HS;
        this->IS = IS;
    }
};


template<class T>
ISolutionable<T>* AStar<T>::search(ISearchable<T> *searchable) {
  this->pushHeuristicForAStar(searchable->getInitialState());
  this->pushHashSet(searchable->getInitialState());
  while(this->heuristicForAStarSize() > 0){
    State<T>* m = this->popHeuristicForAStar();
    if(*m == *(searchable->getGoalState())){
      this->IS->setMember(m);
      this->clearheuristicForAStar();
      this->hashSet->clearSet();
      return this->IS;
    }
    std::list<State<T>*> successors = searchable->getAllPossibleStates(m);
    for(State<T>* state : successors){
      if(!(this->containsHashSet(state))){
        this->pushHashSet(state);
        this->pushHeuristicForAStar(state);
      }
    }
  }
  return nullptr;//no solution!(bug)
}

#endif