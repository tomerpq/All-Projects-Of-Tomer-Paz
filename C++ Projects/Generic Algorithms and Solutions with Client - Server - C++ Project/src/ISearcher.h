#ifndef MILESTONE2_ISEARCHER_H
#define MILESTONE2_ISEARCHER_H

#include "ISearchable.h"
/*this is an interfacae of a searcher algorithm.  */
template<class Solution,class T>
class ISearcher{
public:
    virtual Solution* search(ISearchable<T>* searchable) = 0;
    virtual int getNumberOfNodesEvaluated() = 0;
    virtual ~ISearcher(){}
};

#endif