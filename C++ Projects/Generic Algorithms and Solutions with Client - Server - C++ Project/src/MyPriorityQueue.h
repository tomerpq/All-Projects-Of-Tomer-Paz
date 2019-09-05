#ifndef MILESTONE2_MPQ_H
#define MILESTONE2_MPQ_H
#include <string>
#include <list>
#include "State.h"
/*interface for different queues */
template<class T>
class MyPriorityQueue {
protected:
    int size;
    std::list<State<T>*> queue;
public:
    MyPriorityQueue(){
      size = 0;
    }
    virtual ~MyPriorityQueue(){}

    State<T>* poll(){
      size--;
      State<T>* temp = queue.back();
      queue.pop_back();
      return temp;
    }
    virtual void push(State<T>* i){
      this->size++;
      double iCost = f(i);
      if(queue.size() == 0){
          queue.emplace_back(i);

      }else {
          auto it = queue.begin();
          State<T>* Temp = *it;
          double otherCost = f(Temp);
          it++;
          while(otherCost < iCost && it != queue.end()){
              otherCost = f(Temp);
              it++;
          }
          queue.insert(it,i);
      }
    }
    virtual double f(State<T>* i) = 0;
    int getCount(){
      return size;
    }
    void clearList(){
      size = 0;
      while(!queue.empty()){
        queue.pop_back();
      }
    }
};


#endif