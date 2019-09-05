#include "conditionParser.h"

#ifndef FIRST_STEP_LOOPCOMMAND_H
#define FIRST_STEP_LOOPCOMMAND_H

/** For loop command we call to this class **/
class loopCommand : public conditionParser {

public:
    virtual int execute(std::string* order,int startIndex);
};


#endif //FIRST_STEP_LOOPCOMMAND_H