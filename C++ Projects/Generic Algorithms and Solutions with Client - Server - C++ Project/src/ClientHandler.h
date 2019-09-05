#ifndef MILESTONE2_CLIENTHANDLER_H
#define MILESTONE2_CLIENTHANDLER_H

#include <iosfwd>

/*our clientHandler interface */

template<class Problem,class Solution>
class ClientHandler{
public:
    virtual void handleClient(int newsockfd) = 0;
    virtual ~ClientHandler(){}
};

#endif
