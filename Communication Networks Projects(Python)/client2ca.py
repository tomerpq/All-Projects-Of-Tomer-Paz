from socket import socket, AF_INET, SOCK_DGRAM
import time
#udp client

s = socket(AF_INET, SOCK_DGRAM)
dest_ip = '127.0.0.1'
dest_port = 58000

print("Creating the 15.000 a string")
msg = 'A'

for x in range(1, 15000):
    msg += 'A'

s.sendto(msg.encode(), (dest_ip, dest_port))#send to server 15000*a

data, sender_info = s.recvfrom(1024)
print("Server sent:", data.decode())

s.close()
