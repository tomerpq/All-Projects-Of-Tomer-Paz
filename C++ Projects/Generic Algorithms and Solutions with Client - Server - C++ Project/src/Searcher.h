#ifndef MILESTONE2_SEARCHER_H
#define MILESTONE2_SEARCHER_H
#include "ISearcher.h"
#include "Queue.h"
#include "HeuristicForBestFirstSearch.h"
#include "HeuristicForAStar.h"
#include "HashSetI.h"
#include "ISolutionable.h"

/*an abstract class that represents a searching algorithm */
template<class T>
class Searcher : public ISearcher<ISolutionable<T>,T> {
//tools for search algorithms:
protected:
    ISolutionable<T>* IS;//that needs to be implemented for showing the solution(maybe printing)
    MyPriorityQueue<T> *openList;//openlist queue
    MyPriorityQueue<T> *heuristicForBest;//queue based on heuristic distance for bestfirstsearch algorithm
    MyPriorityQueue<T> *heuristicForAStar;//queue based on heuristic distnace for AStar algorithm
    HashSetI<State<T>>* hashSet;//every instance will have one hashSet of its desired type to work with
    /*helper methods for all different searching algorithms:*/
	int evaluatedNodes;
    State<T>* popOpenList();
    bool containsHashSet(State<T>* state);
    void pushOpenList(State<T>* state);
    void pushHashSet(State<T>* state);
    void pushHeuristicForBest(State<T>* state);
    State<T>* popHeuristicForBest();
    void pushHeuristicForAStar(State<T>* state);
    State<T>* popHeuristicForAStar();
    void clearOpenList();
    void clearHeuristicForBest();
    void clearheuristicForAStar();


public:
    virtual ~Searcher();
    Searcher();
    int openListSize();
    int heuristicForBestSize();
    int heuristicForAStarSize();
    int getNumberOfNodesEvaluated();
    virtual ISolutionable<T>* search(ISearchable<T>* searchable) = 0;
};


template<class T>
Searcher<T>::Searcher() {
    evaluatedNodes = 0;
  openList = new Queue<T>();
  heuristicForBest = new HeuristicForBestFirstSearch<T>();
  heuristicForAStar = new HeuristicForAStar<T>();
}
template<class T>
Searcher<T>::~Searcher() {
    delete openList;
    delete heuristicForBest;
    delete heuristicForAStar;
}
template<class T>
State<T> * Searcher<T>::popOpenList() {
  evaluatedNodes++;
  return openList->poll();
}
template<class T>
State<T>* Searcher<T>::popHeuristicForBest() {
  evaluatedNodes++;
  return heuristicForBest->poll();
}
template<class T>
State<T>* Searcher<T>::popHeuristicForAStar() {
  evaluatedNodes++;
  return heuristicForAStar->poll();
}
template<class T>
bool Searcher<T>::containsHashSet(State<T> *state) {
    return hashSet->contains(state);
}
template<class T>
void Searcher<T>::pushHeuristicForBest(State<T> *state) {
  heuristicForBest->push(state);
}
template<class T>
void Searcher<T>::pushHeuristicForAStar(State<T> *state) {
  heuristicForAStar->push(state);
}
template<class T>
void Searcher<T>::pushOpenList(State<T> *state) {
    openList->push(state);
}
template<class T>
void Searcher<T>::pushHashSet(State<T> *state) {
    hashSet->insertToHashSet(state);
}
template<class T>
int Searcher<T>::getNumberOfNodesEvaluated() {
  return evaluatedNodes;
}
template<class T>
int Searcher<T>::openListSize() {
  return openList->getCount();
}
template<class T>
int Searcher<T>::heuristicForBestSize() {
  return heuristicForBest->getCount();
}
template<class T>
int Searcher<T>::heuristicForAStarSize() {
  return heuristicForAStar->getCount();
}
template<class T>
void Searcher<T>::clearOpenList() {
  openList->clearList();
}
template<class T>
void Searcher<T>::clearHeuristicForBest() {
  heuristicForBest->clearList();
}
template<class T>
void Searcher<T>::clearheuristicForAStar() {
  heuristicForAStar->clearList();
}
#endif