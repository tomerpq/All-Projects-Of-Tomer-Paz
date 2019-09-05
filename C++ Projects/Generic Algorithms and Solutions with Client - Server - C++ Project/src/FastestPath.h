
#ifndef MILESTONE2_FASTESTPATH_H
#define MILESTONE2_FASTESTPATH_H

#include <string>
#include <vector>
#include <list>
#include <sstream>
#include "State.h"
#include "ISearchable.h"
/* our problem in even derech 2 implementation as searchable to be searched by searching algorithm*/
class FastestPath : public ISearchable<std::string>{
    std::vector<std::vector<std::string>> matrix;
    std::list<State<std::string>*> statesToDelete;
    State<std::string>* InitialState;
    State<std::string>* GoalState;
public:
    State<std::string> *getInitialState() override;

    State<std::string> *getGoalState() override;

    std::list<State<std::string>*> getAllPossibleStates(State<std::string> *s) override;

    FastestPath(const std::vector<std::vector<std::string>> &matrix);

    virtual ~FastestPath();

    std::string to_string() override;
};

State<std::string> *FastestPath::getInitialState() {
    return InitialState;
}

State<std::string> *FastestPath::getGoalState() {
    return GoalState;
}

std::list<State<std::string> *> FastestPath::getAllPossibleStates(State<std::string> *s) {

    //list to be returned
    std::list<State<std::string>*> PossibleStates;
    //checking matrix bounderies
    int matrixLastRow = matrix.size()-3;
    int matrixLastColumn = matrix.at(0).size()-1;
    //costs
    double dadCost = s->getCost();
    double dadManhattenDistance = s->getHeuristicDistance();
    //putting the information in stream to make it easier to work with
    std::string myStatesState = *s->getState();
    std::istringstream iss(myStatesState);
    std::istringstream iss3(*GoalState->getState());
    //initializing location data in matrix
    int myStateRow, myStateColumn, goalStateRow, goalStateColumn;
    iss >> myStateRow;
    iss >> myStateColumn;
    iss3 >> goalStateRow;
    iss3 >> goalStateRow;

    //indicators for the edges checks
    bool rightExist = false, leftExist = false ,upExist = false ,downExist = false;
    //lots of if and else to check edges of matrix
    if(myStateRow == 0)
    {
        if(myStateColumn == 0){// need to return R,D
            rightExist = true;
            downExist = true;
        }else if(myStateColumn == matrixLastColumn){// need to return L,D
            leftExist = true;
            downExist = true;
        }else{// need to return L,D,R
            leftExist = true;
            downExist = true;
            rightExist = true;
        }
    }
    else if(myStateColumn == 0)
    {
        if(myStateRow == matrixLastRow){//need to return U,R
            rightExist = true;
            upExist = true;
        }else{//need to return U,R,D
            rightExist = true;
            upExist = true;
            downExist = true;
        }
    }
    else if(myStateRow == matrixLastRow)
    {
        if(myStateColumn == matrixLastColumn){//need to return U,L
            upExist = true;
            leftExist = true;
        }else{//need to return U,L,R
            leftExist = true;
            rightExist = true;
            upExist = true;
        }
    }else if(myStateColumn == matrixLastColumn){//need to return L,U,D
        upExist = true;
        leftExist = true;
        downExist = true;

    }else{//need to return L,U,D,R
        upExist = true;
        leftExist = true;
        downExist = true;
        rightExist = true;
    }

    //RIGHT
    if(rightExist && (stod(matrix[myStateRow][myStateColumn + 1])!= -1))
    {
        double RightCost = stod(matrix[myStateRow][myStateColumn + 1]) + dadCost;
        std::string* rightString = new std::string(std::to_string(myStateRow) + " " + std::to_string(myStateColumn+1));
        double RightHuristicDistance = abs(myStateRow - goalStateRow) + abs((myStateColumn + 1) - goalStateColumn);
        State<std::string> *Right = new State<std::string>(rightString, RightCost, RightHuristicDistance, s);
        //adding the pointer to be deleted after we finish
        statesToDelete.emplace_back(Right);
        PossibleStates.emplace_back(Right);
    }
    //LEFT
    if(leftExist && (stod(matrix[myStateRow][myStateColumn - 1]) != -1)){
        double LeftCost = stod(matrix[myStateRow][myStateColumn - 1]) + dadCost;
        std::string* leftString = new std::string(std::to_string(myStateRow) + " " + std::to_string(myStateColumn-1));
        double LeftHuristicDistance = abs(myStateRow - goalStateRow) + abs((myStateColumn - 1) - goalStateColumn);
        State<std::string> *Left = new State<std::string>(leftString, LeftCost, LeftHuristicDistance, s);
        //adding the pointer to be deleted after we finish
        statesToDelete.emplace_back(Left);
        PossibleStates.emplace_back(Left);
    }
    //UP
    if(upExist && (stod(matrix[myStateRow - 1][myStateColumn]) != -1)){
        double UpCost = stod(matrix[myStateRow - 1][myStateColumn]) + dadCost;
        std::string* upString = new std::string(std::to_string(myStateRow - 1) + " " + std::to_string(myStateColumn));
        double UpHuristicDistance = abs((myStateRow - 1) - goalStateRow) + abs(myStateColumn - goalStateColumn);
        State<std::string> *Up = new State<std::string>(upString, UpCost, UpHuristicDistance, s);
        //adding the pointer to be deleted after we finish
        statesToDelete.emplace_back(Up);
        PossibleStates.emplace_back(Up);
    }
    //DOWN
    if(downExist && (stod(matrix[myStateRow + 1][myStateColumn]) != -1)){
        double DownCost = stod(matrix[myStateRow + 1][myStateColumn]) + dadCost;
        std::string* downString = new std::string(std::to_string(myStateRow + 1) + " " + std::to_string(myStateColumn));
        double DownHuristicDistance = abs((myStateRow + 1) - goalStateRow) + abs(myStateColumn - goalStateColumn);
        State<std::string> *Down = new State<std::string>(downString, DownCost, DownHuristicDistance, s);
        //adding the pointer to be deleted after we finish
        statesToDelete.emplace_back(Down);
        PossibleStates.emplace_back(Down);
    }
    //returning possible states
    return PossibleStates;
}
//to string method
std::string FastestPath::to_string() {
    std::string FastestPathString;
    for(auto row : matrix){
        for(auto column : row){
            //adding value to the string
            FastestPathString = FastestPathString + column + " ";
        }
        // adding /n to indicate next row
        FastestPathString = FastestPathString + "\n";
    }
    return FastestPathString;
}

FastestPath::FastestPath(const std::vector<std::vector<std::string>> &matrix) {
    //saving the matrix size
    int entranceRow = matrix.size()-2;
    int exitRow = matrix.size()-1;
    //creating the goal and initial state
    std::string* entranceString = new std::string(matrix.at(entranceRow).at(0) + " " + matrix.at(entranceRow).at(1));
    std::string* exitString = new std::string(matrix.at(exitRow).at(0) + " " + matrix.at(exitRow).at(1));
    int rowEntrance = std::stoi(matrix.at(entranceRow).at(0));
    int columnEntrance = std::stoi(matrix.at(entranceRow).at(1));
    int rowExit = std::stoi(matrix.at(exitRow).at(0));
    int columnExit = std::stoi(matrix.at(exitRow).at(1));
    //calculating manhatten distance
    int entranceHuristicDistance = abs(rowEntrance - rowExit) + abs(columnEntrance - columnExit);

    InitialState = new State<std::string>(entranceString, 0.0, entranceHuristicDistance, nullptr);

    GoalState = new State<std::string>(exitString, std::stod(matrix[rowExit][columnExit]), 0.0, nullptr);

    //initializing matrix
    this->matrix = matrix;
}

FastestPath::~FastestPath() {
    delete InitialState;
    delete GoalState;
    //using clear to delete all the states we created so far
    for(auto d : statesToDelete){
        delete d;
    }
}


#endif //MILESTONE2_FASTESTPATH_H
