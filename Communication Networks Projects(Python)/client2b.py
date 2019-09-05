import socket, time
#tcp client
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
dest_ip = '127.0.0.1'
dest_port = 58000
s.connect((dest_ip, dest_port))
msg = 'A'

s.send(msg.encode())#send a then send a again.
s.send(msg.encode())

print("Waiting two seconds")
time.sleep(2)
#send a then send a again 10 times + get respone b after 2 a's:
for i in range(0, 10):
    s.send(msg.encode())
    s.send(msg.encode())
    data = s.recv(1)
    print("Server sent:", data.decode())

s.close()