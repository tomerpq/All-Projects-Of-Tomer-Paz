import socket

#tcp server:
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))
server.listen(5)

while True:#server listens to clients
    client_socket, client_address = server.accept()
    print("Connection from: ", client_address)
    data = client_socket.recv(1024)
    while not data.decode() == "":
        print("Received: ", data.decode())#data from client
        client_socket.send(data.upper())#send it uppercase
        data = client_socket.recv(1024)

    print("Client disconnected")

    client_socket.close()