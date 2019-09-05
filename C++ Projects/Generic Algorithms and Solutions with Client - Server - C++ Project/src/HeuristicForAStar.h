#ifndef MILESTONE2_HFAS_H
#define MILESTONE2_HFAS_H
#include "MyPriorityQueue.h"

/* implements a queue for AStar algorithm*/
template<class T>
class HeuristicForAStar : public MyPriorityQueue<T>{
public:
    double f(State<T>* i){
      return (i->getCost() + i->getHeuristicDistance());
    }
    ~HeuristicForAStar(){}
};

#endif