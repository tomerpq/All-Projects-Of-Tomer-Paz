#Skeleton file for HW2 - Winter 2016-2017 - extended intro to CS

#Add your implementation to this file

#You may add other utility functions to this file,
#but you may NOT change the signature of the existing ones.

#Change the name of the file to hw2_<your ID number> (extension .py).

import time

############
# QUESTION 1
############
# 1c
def reverse_sublist(lst,start,end):
    k = 0
    p = end
    if(end % 2 == 0):
        p = p//2
    else:
        p = p//2 +1
    for i in range(start,p):
        temp = lst[i]
        lst[i] = lst[end -k -1]
        lst[end -k -1] = temp
        k = k + 1
# 1d
def divide_list(lst):
    pivot = lst[0]
    smaller = [i for i in lst if i < pivot]
    greater = [i for i in lst if i > pivot]
    return smaller + [pivot] + greater
# 1e
def divide_list_inplace(lst):
    pivot = lst[0]
    tempPlace = 1
    for i in range(1,len(lst)):
        if(lst[i] < pivot):
            temp = lst[tempPlace]
            lst[tempPlace] = lst[i]
            lst[i] = temp
            tempPlace = tempPlace + 1
    if(tempPlace > 1):
        tempPlace = tempPlace - 1
        lst[0] = lst[tempPlace]
        lst[tempPlace] = pivot
############
# QUESTION 2b
############

def power_new(a,b):
    """ computes a**b using iterated squaring """
    result = 1
    b_bin = bin(b)[2:]
    reverse_b_bin = b_bin[: :-1]
    for bit in reverse_b_bin:
        if(bit != '0'):
            result = result * a
        a = a * a
    return result


############
# QUESTION 3b
############

def inc(binary):
    incBinary = ''
    isSummed = False
    carry = (binary[-1] == '1')
    for i in binary[::-1]:
        if((carry) and (i == '1')):
            incBinary += '0'
        elif((carry) or (i == '1')):
            if(carry):
                carry = False
            incBinary += '1'
            isSummed = True
        else:
            incBinary += '0'
    if(not isSummed):
        incBinary += '1'
    incBinary = incBinary[::-1]
    if(binary[-1] == '0'):
        incBinary = binary[:-1] + '1'
    return incBinary
############
# QUESTION 4b
############

# 4a
def sum_divisors(n):
    Sum = 0
    for i in range(1,int(n**0.5 + 1)):
        if((n%i == 0) and (n != i)):
            Sum += i
            Sum2 = n//i
            if((Sum2 < n) and (Sum2 != i)):
                Sum += Sum2
    return Sum

# 4b
def is_finite(n):
    sums = []
    mark = True
    nBefore = n
    while mark:
        sums.append(nBefore)
        nBefore = sum_divisors(nBefore)
        if(nBefore == 0):
            return True
        if(nBefore in sums):
            mark = False
    return mark
#4c
def cnt_finite(limit):
    cnt = 0
    for i in range(1,limit +1):
        if(is_finite(i)):
            cnt += 1
    return cnt

############
# QUESTION 5
############

# 5a
def has_common(s1,s2,k):
    if len(s1)<k or len(s2)<k:
       return False
    something = []
    for i in range(0,len(s1) -k +1):
        something.append(s1[i:i+k])
    for i in range(0,len(s2) -k +1):
        if(s2[i:i+k] in something):
            return True
    return False

def lcs_length_1(s1, s2):
    k=1
    while has_common(s1,s2,k) == True:
        k+=1
    return k-1

#5b
def lcs_length_2(s1, s2):
    if len(s1) == 0 or len(s2) == 0:
        return 0
    m = [[0]*len(s2) for i in range(len(s1))]
    for i in range(len(s1)):
        for j in range(len(s2)):
            if((i == 0) and (s1[i] == s2[j])):
                m[i][j] = 1
            if(s1[i] == s2[j]):
                if((i-1 >= 0) and (j-1 >= 0)):
                    m[i][j] = m[i-1][j-1] +1
    return max([max(l) for l in m])

#5c
import random
def gen_str(n, alphabet):
    return "".join([random.choice(alphabet) for i in range(n)])

###Amir's code:
##n=4000
##alphabet = "abcdefghijklmnopqrstuvwxyz"
##print("two random strings of length", n)
##s1 = gen_str(n, alphabet)
##s2 = gen_str(n, alphabet)
##
##t0 = time.clock()
##res1 = lcs_length_1(s1,s2)
##t1 = time.clock()
##print("lcs_length_1", res1, t1-t0)
##
##t0 = time.clock()
##res2 = lcs_length_2(s1,s2)
##t1 = time.clock()
##print("lcs_length_2", res2, t1-t0)

###Michal's code
##n=4000
##alphabet = "abcdefghijklmnopqrstuvwxyz"
##print("two identical random strings of length", n)
##s1 = gen_str(n, alphabet)
##s2 = s1
##
##t0 = time.clock()
##res1 = lcs_length_1(s1,s2)
##t1 = time.clock()
##print("lcs_length_1", res1, t1-t0)
##
##t0 = time.clock()
##res2 = lcs_length_2(s1,s2)
##t1 = time.clock()
##print("lcs_length_2", res2, t1-t0)


########
# Tester
########

def test():
    
    lst = [1,2,3,4,5]
    reverse_sublist (lst,0,4)
    if lst != [4, 3, 2, 1, 5]:
        print("error in reverse_sublist()")        
    lst = ["a","b"]
    reverse_sublist (lst,0,1)
    if lst != ["a","b"]:
        print("error in reverse_sublist()")        

    lst = [1,2,3,4,5]
    lst_div = divide_list(lst)
    if lst_div == None:
        print("error in divide_list()")
    if lst_div != None and len(lst_div) != 5:
        print("error in divide_list()")
    if lst_div != None and lst_div[0] != 1:
        print("error in divide_list()")
    lst = [3,2,1,4,5]
    lst_div = divide_list(lst)
    if lst_div == None:
        print("error in divide_list()")
    
    if lst_div != None and \
       (lst_div[0] >= 3 or \
       lst_div[1] >= 3 or \
       lst_div[2] != 3 or \
       lst_div[3] <= 3 or \
       lst_div[4] <= 3):
        print("error in divide_list()")
    #verify that the original list did not change
    if lst[0] != 3 or \
       lst[1] != 2 or \
       lst[2] != 1 or \
       lst[3] != 4 or \
       lst[4] != 5:
        print("error in divide_list()")
    

    lst = [1,2,3,4,5]
    divide_list_inplace(lst)
    if lst[0] != 1:
        print("error in divide_list_inplace()")
    lst = [3,2,1,4,5]
    divide_list_inplace(lst)
    if lst[0] >= 3 or \
       lst[1] >= 3 or \
       lst[2] != 3 or \
       lst[3] <= 3 or \
       lst[4] <= 3:
        print("error in divide_list_inplace()")
    

    if power_new(2,3) != 8:
        print("error in power_new()")

    if inc("0") != "1" or \
       inc("1") != "10" or \
       inc("101") != "110" or \
       inc("111") != "1000" or \
       inc(inc("111")) != "1001":
        print("error in inc()")
    
    if sum_divisors(6)!=6 or \
       sum_divisors(4)!=3:        
        print("error in sum_divisors()")

    if is_finite(6) or \
       not is_finite(4):
        print("error in is_finite()")

    if cnt_finite(6) != 5:
        print("error in cnt_finite()")
        
    if has_common("ababc", "dbabca", 5) != False or \
       has_common("ababc", "dbabca", 4) != True or \
       has_common("ababc", "dbabca", 3) != True or \
       has_common("", "dbabca", 2) != False or \
       lcs_length_1("ababc", "dbabca") != 4:
        print("error in has_common()")
    
    if lcs_length_2("ababc", "dbabca") != 4 or \
       lcs_length_2("dbabca", "ababc") != 4 or \
       lcs_length_2("xxx", "ababc") != 0 :
        print("error in lcs_length_2()")



