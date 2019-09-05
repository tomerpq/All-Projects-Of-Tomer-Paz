#ifndef INTERFACE_H
#define INTERFACE_H

#include <string>
#include <map>
#include <list>
#include <stdexcept>
/** Abstract class Command **/
class Command{
public:
    //Execute methods will read the string and return how many index has been reed
    virtual int execute(std::string* commands,int startIndex) = 0;
    virtual ~Command(){}
};

#endif //INTERFACE_H
