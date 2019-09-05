#ifndef MILESTONE2_HSST_H
#define MILESTONE2_HSST_H
#include "HashSetI.h"
#include "State.h"
/*implements an hashSet to hash strings */
class HashSetStringState : public HashSetI<State<std::string>>{
public:
    std::string to_string(State<std::string>* state){
      std::string* s = state->getState();
      return *s;
    }
    ~HashSetStringState(){}
};
#endif