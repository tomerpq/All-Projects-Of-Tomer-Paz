from socket import socket, AF_INET, SOCK_DGRAM
#udp server:
server = socket(AF_INET, SOCK_DGRAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))

#get 2 a's
data, sender_info = server.recvfrom(1024)
data, sender_info = server.recvfrom(1024)
#get 2 a's and send b for each two gets,10 times.
for i in range(0, 10):

    data, sender_info = server.recvfrom(1024)
    data, sender_info = server.recvfrom(1024)
    print ("Package number ", i, " received")
    response = 'B'
    server.sendto(response.encode(), sender_info)

print("Client disconnected")
server.close()