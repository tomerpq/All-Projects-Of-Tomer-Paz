from socket import socket, AF_INET, SOCK_DGRAM
import time

#udp client:
s = socket(AF_INET, SOCK_DGRAM)
dest_ip = '127.0.0.1'
dest_port = 58000
msg = 'A'
#send 2 a's:
s.sendto(msg.encode(), (dest_ip, dest_port))
s.sendto(msg.encode(), (dest_ip, dest_port))

print("Waiting two seconds")
time.sleep(2)
#send 2 a's and get b for each two sends,10 times.
for i in range(0, 10):

    s.sendto(msg.encode(), (dest_ip, dest_port))
    s.sendto(msg.encode(), (dest_ip, dest_port))
    data, sender_info = s.recvfrom(1024)
    print("Server sent:", data.decode())

s.close()