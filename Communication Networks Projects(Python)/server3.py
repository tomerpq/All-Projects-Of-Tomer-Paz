import socket, os, threading

#tcp server
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))
server.listen(5)
while True:
    client_socket, client_address = server.accept()
    data = client_socket.recv(80000)#get info from client(browser)
    tmp = data.decode()
    splitedTmp = tmp.split("\n")#split info for spereated messages(because they are seperated by "\n"
    firstRequest = splitedTmp[0]#the first request from the client
    print("FirstRequest = " + firstRequest)#print first request
    firstRequest = firstRequest.rstrip()
    HttpPlace = firstRequest.find("HTTP/1.1")
    firstRequest = firstRequest[4:HttpPlace-1]#file address
	#print other requests by number:
    for i in range(1,len(splitedTmp)-2):
        request = splitedTmp[i]
        print("Request ", i + 1, " = ", request)
	#check options for the info rquested:
    if(firstRequest == "/redirect"):
        client_socket.send("HTTP/1.1 301 Moved Permanently\nConnection: close\nLocation: /result.html\n".encode())
    else:
        if(firstRequest == "/"):
            file = open("files/index.html", "r")
            s = file.read()
            client_socket.send(("HTTP/1.1 200 OK\nConnection: close\n\n" + s).encode())
        else:
            foundFile = 0
            search = firstRequest[1:]
            if (search.find("/") == -1):
                tmpSearch = search
            else:
                tmpSearch = search[0:search.find("/")]
            searchIn = "files"
			#check if the file with the information requested exists:
            while(1):
                found = 0
                for filename in os.listdir(searchIn):
                    if(tmpSearch == filename):
                        found = 1
                        break
                if(found == 1):
                    if(search.find("/") == -1):
                        foundFile = 1
                        break
                    searchIn = searchIn + "/" + tmpSearch
                    search = search[search.find("/")+1:]
                    if(search.find("/") == -1):
                        tmpSearch = search
                    else:
                        tmpSearch = search[0:search.find("/")]
                else:
                    break
            if(foundFile == 1):
                if firstRequest.endswith('.jpg'):
                    file = open("files/" + firstRequest[1:], "rb")
                    s = file.read()
                    client_socket.send("HTTP/1.1 200 OK\nConnection: close\n\n".encode())
                    client_socket.send(s)
                else:
                    file = open("files/" + firstRequest[1:], "r")
                    s = file.read()
                    client_socket.send(("HTTP/1.1 200 OK\nConnection: close\n\n" + s).encode())
            else:
                client_socket.send(("HTTP/1.1 404 Not Found\nConnection: close").encode())
    client_socket.close()