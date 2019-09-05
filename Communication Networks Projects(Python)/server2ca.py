from socket import socket, AF_INET, SOCK_DGRAM
#udp server
server = socket(AF_INET, SOCK_DGRAM)
server_ip = '0.0.0.0'
server_port = 58000
server.bind((server_ip, server_port))
print("Client connected")

data, sender_info = server.recvfrom(16384)#recieve 15000*a

response = 'B'#send b back
server.sendto(response.encode(), sender_info)

print("Client disconnected")
server.close()
