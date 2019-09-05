#include "Command.h"
#include <mutex>
#include <stdio.h>
#include <stdlib.h>

#include <netdb.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>

#include <string.h>

#ifndef FIRST_STEP_CONNECTCOMMAND_H
#define FIRST_STEP_CONNECTCOMMAND_H

/** For command connect we call to this class **/
/** Connect Command will send to simulator the value we want to modify. It will get the variable address and the value
 * we want the simulator to modify.**/
class connectCommand : public Command {
    int portno;
    std::string address;
    int sockfd;

public:
    connectCommand();
    virtual int execute(std::string order[], int startIndex);
    void set(std::string setAddress, double value);
    virtual ~connectCommand() {}
};

#endif //FIRST_STEP_CONNECTCOMMAND_H
