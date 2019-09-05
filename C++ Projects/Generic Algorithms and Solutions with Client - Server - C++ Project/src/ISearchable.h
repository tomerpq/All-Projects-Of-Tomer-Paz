#ifndef MILESTONE2_ISEARCHABLE_H
#define MILESTONE2_ISEARCHABLE_H
#include "State.h"
#include <list>
#include <string>
/*this is an interface of a problem that can be searched by searching algorithm */
template<class T>
class ISearchable{
public:
    virtual State<T>* getInitialState() = 0;
    virtual State<T>* getGoalState() = 0;
    virtual std::list<State<T>*> getAllPossibleStates(State<T>* s) = 0;
    virtual std::string to_string() = 0;
    virtual ~ISearchable(){}
};

#endif