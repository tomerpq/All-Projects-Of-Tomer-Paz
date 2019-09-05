#ifndef MILESTONE2_ISOLUTIONABLE_H
#define MILESTONE2_ISOLUTIONABLE_H
#include "State.h"

/*this is a interface of solution to be returned.  */
template<class T>
class ISolutionable {
protected:
    State<T>* member;//the item that represents the solution. to be print implemented  and maybe other stuff.
public:
    void setMember(State<T>* i){
        this->member = i;
    }
    virtual T returnSolution() = 0;
    virtual ~ISolutionable(){}
};

#endif