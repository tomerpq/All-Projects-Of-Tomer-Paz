#ifndef MILESTONE2_STATE_H
#define MILESTONE2_STATE_H
#include <string>
/*a class representing a state for a algoritthm/Isearchable class to use it */
template<class T>
class State{
private:
    T* state;//if its a string we must allocate dynamically (new) because destructor deletes this member!
    double cost;//cost of the state
    double heuristicDistance;//the heuristic distance that will be implemented here- until this state
    State<T>* cameFrom;//will be set to null if not coming from anywhere!

public:
    State(T *state, double cost, double heuristicDistance, State<T> *cameFrom) : state(state), cost(cost),
                                                                                 heuristicDistance(heuristicDistance),
                                                                                 cameFrom(cameFrom) {}

    ~State(){
        delete state;
    }

    /*getters :*/
    T* getState(){return this->state;}

    double getCost(){return this->cost;}
    double getHeuristicDistance(){return this->heuristicDistance;}

    State<T>* getCameFrom(){return this->cameFrom;}
    friend bool operator ==(const State<T>& s1,const State<T>& s2){
        return *(s1.state) == *(s2.state);
    }
};

#endif