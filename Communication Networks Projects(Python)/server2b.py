import socket, threading
#tcp server
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))
server.listen(5)

client_socket, client_address = server.accept()
print("Connection from: ", client_address)

data = client_socket.recv(2)#get 2 a's
#get 2 a's then send one b for each of them, 10 times:
for i in range(0, 10):

    data = client_socket.recv(1)
    data = client_socket.recv(1)
    print ("Package number ", i, " received")
    response = 'B'
    client_socket.send(response.encode())

print("Client disconnected")
client_socket.close()