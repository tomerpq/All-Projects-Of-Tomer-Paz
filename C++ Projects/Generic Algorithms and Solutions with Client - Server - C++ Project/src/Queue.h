#ifndef MILESTONE2_QUEUE_H
#define MILESTONE2_QUEUE_H
#include "MyPriorityQueue.h"
/*open list based on normal queue */
template<class T>
class Queue : public MyPriorityQueue<T>{
public:
    void push(State<T>* i);
    double f(State<T>* i){return 0.0;}//dummy
    ~Queue(){}
};
template<class T>
void Queue<T>::push(State<T>* i) {
  this->size++;
  this->queue.emplace_back(i);
}

#endif