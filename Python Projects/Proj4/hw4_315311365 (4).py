#Skeleton file for HW4 - Spring 2015-2016 - Extended Intro to CS

#Add your implementation to this file

#You may add other utility functions to this file,
#but you may NOT change the signature of the existing ones.

#Change the name of the file to hw4_IDnumber (extension .py).

import random

############
# QUESTION 1
############

def max22(L, left, right):
    if (left == right):
        return L[left]
    mid = (left + right)// 2
    l = max22(L,left,mid)
    r = max22(L,mid+1,right)
    return max(l,r)

def max_list22(L):
    return max22(L, 0, len(L)-1)

############
# QUESTION 2
############

def profit(value, size):
    lst = []
    if(size == 0):
        return 0
    for i in range(size):
        p = profit(value,size-1-i) + value[i]
        lst.append(p)
    return max(lst)

    return 
def profit2(value, size):
    d = {}
    return profit_mem(value,size,d)

def profit_mem(value, size, d):
    lst = []
    if(size == 0):
        return 0
    if size in d:
        return d[size]
    for i in range(size):
        p = profit_mem(value,size-i-1,d) + value[i]
        lst.append(p)
    d[size] = max(lst)
    return max(lst)


############
# QUESTION 3
############

# replace each "replace string with code" string with appropriate code
# do not modify the code's structure or change any of the existing code

def comp(s1,s2):
    if((len(s1)==0) and (len(s2)==0)):
        return True
    if((len(s1)==0) or (len(s2)==0)):
        return False
    if(s1[0] != s2[0]):
        return False
    return comp(s1[1:],s2[1:])

def comp_ext(s1,s2):
    if((len(s1)==0) and (len(s2)==0)):
        return True
    if((len(s1)==0) or (len(s2)==0)):
        return False
    if(s1[0]== "+"):
        return comp_ext(s1[1:],s2[1:])
    if(s1[0]=="*"):
        return comp_ext(s1[1:],s1[1:]) or comp_ext(s1,s2[1:])
    if(s1[0] != s2[0]):
        return False
    return comp_ext(s1[1:],s2[1:])



############
# QUESTION 5
############

#The first five lines of code are given and should not be changed
def choose_sets(lst, k):
    if k <= 0:
        return [[]]
    elif lst == []:    # k is non negative int
        return []
    else:
        newLst = choose_sets(lst[1:],k-1)
        for set_k in newLst:
            set_k.append(lst[0])
        return newLst + choose_sets(lst[1:],k)
            

############
# QUESTION 6
############

def naive(num_of_coins):
    """
    naive strategy:
    uniformly pick a non-empty pile
    uniformly pick the number of coins to remove
    """
    while(True):
        placeToPick = random.randrange(len(num_of_coins))
        if(num_of_coins[placeToPick] > 0):
            num_of_coins[placeToPick] -= random.randrange(1,num_of_coins[placeToPick] +1)
            break
            
    
    
def compete(num_of_piles, init_num_of_coins, s1, s2):
    """
    simulates a single game
    """
    game = [init_num_of_coins for pile in range(num_of_piles)]
    endGame = [0 for pile in range(num_of_piles)]
    symbolS1, symbolS2 = 0, 1
    startSymbol = random.randint(symbolS1,symbolS2)
    while(game != endGame):
        if(symbolS1 == startSymbol):
            s1(game)
        else:
            s2(game)
        if(symbolS1 == startSymbol):
            startSymbol = symbolS2
        else:
            startSymbol = symbolS1
    if(symbolS1 == startSymbol):
        return symbolS2
    return symbolS1
    
def compare(num_of_piles, init_num_of_coins, s1, s2, num_of_games):
    """
    compares strategies over multiple games
    """
    symbolS1, symbolS2 = 0, 1
    s1Win, s2Win = 0, 0
    for game in range(num_of_games):
        score = compete(num_of_piles,init_num_of_coins,s1,s2)
        if(score == symbolS1):
            s1Win += 1
        else:
            s2Win += 1
    return (s1Win,s2Win)

        
def silly(num_of_coins):
    """
    a silly strategy
    """
    while(True):
        placeToPick = random.randrange(len(num_of_coins))
        if(num_of_coins[placeToPick] > 1):
            num_of_coins[placeToPick] = 1
            break
        elif(num_of_coins[placeToPick] == 1):
            num_of_coins[placeToPick] = 0
            break
            
    
    
def winning(num_of_coins):
    """
    winning strategy
    """
    
    assert sum(num_of_coins)!=0
    
    num_of_piles = len(num_of_coins)
    # get binary representations of bins heights
    binary_reps = [bin(e)[2:] for e in num_of_coins]
    # pad with zeros to achieve equal representation lengths
    maxlen = max([len(e) for e in binary_reps])
    binary_reps = ["0"*(maxlen-len(e))+e for e in binary_reps]
    # compute bitwise xor of pile heights
    xor_of_num_of_coins = "".join([str(sum([int(binary_reps[i][j]) for i in range(num_of_piles)])%2) 
                                    for j in range(maxlen)])
    
    # if bitwise XOR encodes for 0, resort to naive strategy
    if int(xor_of_num_of_coins, 2)==0:
        naive(num_of_coins)
        return
    
    # find index of most significant bit in xor_of_num_of_coins    
    mostsign = xor_of_num_of_coins.index("1")
    # find a pile whose binary representation includes "1" in this index
    pile = [i for i in range(num_of_piles) if binary_reps[i][mostsign]=="1"][0]
    # compute binary representation of the number of coins we should leave in this pile
    xor_of_coins_to_remain = [str((int(a)+int(b))%2) for a,b in zip(binary_reps[pile], xor_of_num_of_coins)]
    # convert to int, modify pile
    coins_to_remain = int("".join(xor_of_coins_to_remain), 2)
    num_of_coins[pile] = coins_to_remain

    assert all(e>=0 for e in num_of_coins), "number of coins turned negative"
    

########
# Tester
########

def test():

    # Q1 basic tests
    if max_list22([1,20,3]) != 20:
        print("error in max22()")
    if max_list22([1,20,300,400]) != 400:
        print("error in max22()")

    # Q2 basic tests
    if profit([1, 5, 8, 9], 4) != 10:
        print("error in profit()")
    if profit([2, 3, 7, 8, 9], 5) != 11:
        print("error in profit()")
    if profit2([1, 5, 8, 9], 4) != 10:
        print("error in profit2()")
    if profit2([2, 3, 7, 8, 9], 5) != 11:
        print("error in profit2()")
    
    
    # Q3 basic tests
    if comp("ab", "ab")!=True:
        print("error in comp()")
    if comp("", "")!=True:
        print("error in comp()")
    if comp("a", "ab")!=False:
        print("error in comp()")
    
    if comp_ext("ab", "ab")!=True:
        print("error in comp_ext()")
    if comp_ext("", "")!=True:
        print("error in comp_ext()")
    if comp_ext("a", "ab")!=False:
        print("error in comp_ext()")
    if comp_ext("+", "a")!=True:
        print("error in comp_ext()")
    if comp_ext("+", "")!=False:
        print("error in comp_ext()")
    if comp_ext("a+b", "axb")!=True:
        print("error in comp_ext()")
    if comp_ext("a+b", "axxb")!=False:
        print("error in comp_ext()")
    if comp_ext("a*xyz", "abcxyz")!=True:
        print("error in comp_ext()")
    if comp_ext("a*", "a")!=False:
        print("error in comp_ext()")
   
    # Q5 basic tests
    if choose_sets([1,2,3,4], 0) != [[]]:
        print("error in choose_sets()")
    tmp = choose_sets(['a','b','c','d','e'], 4)
    if tmp == None:
        print("error in choose_sets()")
    else:
        tmp = sorted([sorted(e) for e in tmp])
        if tmp != [['a', 'b', 'c', 'd'], ['a', 'b', 'c', 'e'], ['a', 'b', 'd', 'e'], ['a', 'c', 'd', 'e'], ['b', 'c', 'd', 'e']]:
            print("error in choose_sets()")

