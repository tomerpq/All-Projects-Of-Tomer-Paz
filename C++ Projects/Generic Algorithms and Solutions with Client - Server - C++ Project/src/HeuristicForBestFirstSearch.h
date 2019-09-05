#ifndef MILESTONE2_HFBFS_H
#define MILESTONE2_HFBFS_H
#include "MyPriorityQueue.h"

/*implements a queue for bestfirstsearch algorithm */
template<class T>
class HeuristicForBestFirstSearch : public MyPriorityQueue<T>{
public:
    double f(State<T>* i){return i->getCost();}
    ~HeuristicForBestFirstSearch(){}
};

#endif