#ifndef MILESTONE2_ASOS_H
#define MILESTONE2_ASOS_H

#include "Searcher.h"
#include "Solver.h"
/*our adapter that implements Solve-that means it will be a solver and will solve a Isearchable probmlem with searching algorithm and will return a solution by this searching algorithm*/
template<class T>
class AdapterSolverToIsearcher : public Solver<ISearchable<T>,ISolutionable<T>>{
private:
    ISearcher<ISolutionable<T>,T>* iSearcher;//
public:

    ~AdapterSolverToIsearcher(){
        delete iSearcher;
    }

    AdapterSolverToIsearcher(ISearcher<ISolutionable<T>,T>* iSearcher){
        this->iSearcher = iSearcher;
    }

    ISolutionable<T>* solve(ISearchable<T>* problem){
        return iSearcher->search(problem);
    }
};

#endif