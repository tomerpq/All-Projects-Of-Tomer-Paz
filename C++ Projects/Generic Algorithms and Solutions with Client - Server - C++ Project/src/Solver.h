#ifndef MILESTONE2_SOLVER_H
#define MILESTONE2_SOLVER_H

/*the class that solves a generic problem and returns generic solution */

template<class Problem,class Solution>
class Solver{
public:
    virtual Solution* solve(Problem* problem) = 0;
    virtual ~Solver(){}
};

#endif