import socket, threading
#tcp server
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))
server.listen(5)


while True:
    client_socket, client_address = server.accept()
    print("Connection from: ", client_address)
    data = client_socket.recv(16384)#recieve 15000*a

    while not data.decode() == '':
        print("Message received: as expected ... 15000 times letter A")

        response = 'B'
        client_socket.send(response.encode())#send B
        data = client_socket.recv(1024)

    print("Client disconnected")
    client_socket.close()