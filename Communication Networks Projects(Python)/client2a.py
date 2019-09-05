import socket
#tcp client
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
dest_ip = '127.0.0.1'
dest_port = 58000
s.connect((dest_ip, dest_port))

print("Creating the 15.000 a string")
msg = 'A'

for x in range(1, 15000):
    msg += 'A'

s.send(msg.encode())#send 15000*a
data = s.recv(4096)#recieve response (B)
print("Server sent:", data.decode())

s.close()