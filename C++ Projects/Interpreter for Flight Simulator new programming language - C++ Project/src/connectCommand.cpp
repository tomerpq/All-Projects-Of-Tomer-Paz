#include "connectCommand.h"
#include <iostream>
using namespace std;

connectCommand::connectCommand() : portno(0), address("0"), sockfd(0) {
    
}

int connectCommand::execute(std::string *order, int startIndex) {
    address = order[startIndex+1];
    portno = stoi(order[startIndex+2]);
    int n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[256];

    /* Create a socket point */
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0) {
        perror("ERROR opening socket");
        exit(1);
    }

    server = gethostbyname(address.c_str());

    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = htons(portno);

    /* Now connect to the server */
    if (connect(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
        perror("ERROR connecting");
        exit(1);
    }
    return 3;
}

void connectCommand::set(std::string setAddress, double value) {
    int n;
    char buffer[256];

    /* Now ask for a message from the user, this message
       * will be read by server
    */

    string str = "set " + setAddress.substr(1,(setAddress.length()-2)) + " " + to_string(value) + "\r\n";

    /* Send message to the server */
    n = write(sockfd, str.c_str(), str.length());

    if (n < 0) {
        perror("ERROR writing to socket");
        exit(1);
    }

    /* Now read server response */
    bzero(buffer,256);
    n = read(sockfd, buffer, 255);

    if (n < 0) {
        perror("ERROR reading from socket");
        exit(1);
    }
}