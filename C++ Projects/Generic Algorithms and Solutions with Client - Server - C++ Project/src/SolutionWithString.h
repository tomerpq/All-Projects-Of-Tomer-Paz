#ifndef MILESTONE2_SWS_H
#define MILESTONE2_SWS_H
#include <sstream>
#include "ISolutionable.h"
#include "State.h"
/*this class represents a solution to be returned class that represented with strings - to be sent to client. */
class SolutionWithString : public ISolutionable<std::string>{
public:
    std::string returnSolution() override;
    ~SolutionWithString() override;
};

std::string SolutionWithString::returnSolution()
{
    //the solution string
    std::string SolutionString = "";
    //the member we got from the searcher
    State<std::string>* goal = this->member;
    //loop untill we get to the first place in the matrix
    while(goal->getCameFrom() != nullptr)
    {
        //mooving one state back
        std::string temp = *goal->getCameFrom()->getState();
        std::istringstream iss(temp);
        //turning string to location inmdicators
        int CameFromRow, CameFromColumn;
        iss >> CameFromRow;
        iss >> CameFromColumn;
        //turning string to location inmdicators
        std::string temp2 = *goal->getState();
        std::istringstream iss2(temp2);
        int GoalRow, GoalColumn;
        iss2 >> GoalRow;
        iss2 >> GoalColumn;
        //adding to the string accordinly to the plave it came from
        if(GoalColumn == CameFromColumn + 1)
            SolutionString = "Right, " + SolutionString;
        else if(GoalColumn == CameFromColumn - 1)
            SolutionString = "Left, " + SolutionString;
        else if(GoalRow == CameFromRow + 1)
            SolutionString = "Down, " + SolutionString;
        else if(GoalRow == CameFromRow - 1)
            SolutionString = "Up, " + SolutionString;
        //mooving goal one step back
        goal = goal->getCameFrom();
    }

    return SolutionString;
};

SolutionWithString::~SolutionWithString() {

}


#endif

