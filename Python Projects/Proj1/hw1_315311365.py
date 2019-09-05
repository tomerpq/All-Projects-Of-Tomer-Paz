#Skeleton file for HW1 - Winter 2016 - extended intro to CS

#Add your implementation to this file

#you may NOT change the signature of the existing ones.

#Change the name of the file to include your ID number (hw1_ID.py).


#Question 3
in_file = "our_input.txt"
out_file = "output.txt"
#Add the rest of your code here.
in_file = open(in_file, 'r')
out_file = open(out_file, 'w')
lines = in_file.readlines()
length = len(lines)
if(length > 1):
    for line in lines[0:length-1]:
        out_file.write(str(len(line.split())) + '\n')
    out_file.write(str(len(lines[-1].split())))
elif(length == 1):
    out_file.write(str(len(lines[-1].split())))
out_file.close()
in_file.close()
#Assume the file in_file exists in the same folder as the current file






#**************************************************************
#Question 5
k = 1
n = 100
#Add the rest of your code here.
nEnd = 1
while(nEnd <= n):
    if((nEnd%k == 0) or (str(k) in str(nEnd))):
        if((nEnd%k == 0) and (str(k) in str(nEnd))):
            print('boom-boom!')
        else:
            print('boom!')
    else:
        print(nEnd)
    nEnd = nEnd + 1
#**************************************************************
#Question 6
num = int(input("Please enter a positive integer: "))
#Add the rest of your code here.
#It should handle any positive integer num
#at the end, the variables named 'length', 'start' and 'seq'
#should hold the answers and be printed below
length = 0
lengthTemp = 0
start = -1
seq = None
cntSeq = 0
counterIndex = 0
seqMax = ''
numTemp = num
strNum = str(num)
if(num != 0):
    while(numTemp > 0):
        if(numTemp%2 != 0):
            lengthTemp = lengthTemp + 1
        if(lengthTemp > length):
            length = lengthTemp
        if (numTemp%2 == 0):
            lengthTemp = 0
        numTemp = numTemp//10
    for digit in strNum:
        if((int(digit))%2 != 0):
            seqMax = seqMax + digit
            cntSeq = cntSeq + 1
            if(cntSeq == 1):
                start = counterIndex
        else:
            if(cntSeq == length):
                break
            else:
                seqMax = ''
                cntSeq = 0
        counterIndex = counterIndex + 1
    if(length >= 1):
        seq = seqMax
print("The maximal length is", length)
print("Sequence starts at", start)
print("Sequence is", seq)
