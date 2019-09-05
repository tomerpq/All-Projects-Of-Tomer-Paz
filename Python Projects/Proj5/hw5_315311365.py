#Skeleton file for HW5 - Fall 2017 - extended intro to CS

#Add your implementation to this file

#You may add other utility functions to this file,
#but you may NOT change the signature of the existing ones.

#Change the name of the file to your ID number (extension .py).


############
# QUESTION 1
############
class Binary():
    def __init__(self, s):
        """ represent a binary number as a string """
        assert type(s)==str
        assert s.count("0") + s.count("1") == len(s)
        self.s = s

    def __repr__(self):
        return "0b" + self.s

    def length(self):
        return len(self.s)

    def __eq__(self, other):
        return self.s == other.s

    def __lt__(self, other):
        """ operator < """
        assert isinstance(other, Binary)
        if len(self.s) < len(other.s):
            return True
        if len(self.s) == len(other.s):
            for i in range(len(self.s)):
                if self.s[i] > other.s[i]:
                    break
                if self.s[i] < other.s[i]:
                    return True
        return False

    def __add__(self, other):
        """ operator + """
        assert isinstance(other,Binary)
        summ = ''
        carry = 0
        lens, leno = len(self.s), len(other.s)
        minlength = min(lens,leno)
        for i in range(minlength):
            if self.s[:lens-i][-1] == '0' and other.s[:leno-i][-1] == '0':
                if carry == 0:
                    summ = '0' +summ
                else:
                    summ = '1' +summ
                    carry = 0
            if (self.s[:lens-i][-1] == '0' and other.s[:leno-i][-1] == '1') \
               or (self.s[:lens-i][-1] == '1' and other.s[:leno-i][-1] == '0'):
                if carry == 0:
                    summ = '1' +summ
                else:
                    summ = '0' +summ
            if self.s[:lens-i][-1] == '1' and other.s[:leno-i][-1] == '1':
                if carry == 0:
                    summ = '0' +summ
                    carry = 1
                else:
                    summ = '1' +summ
        if lens == leno:
            if carry == 1:
                summ = '1' +summ
        else:
            if lens > leno:
                if carry == 0:
                    summ = self.s[:lens-leno] +summ
                else:
                    carry2 = 1
                    for i in range(lens-leno):
                        if carry2 == 0:
                            summ = self.s[:lens-leno-i] +summ
                            break
                        else:
                            if self.s[:lens-leno-i][-1] == '0':
                                summ = '1' +summ
                                carry2 = 0
                            else:
                                summ = '0' +summ
                    if carry2 == 1:
                        summ = '1' +summ
            if lens < leno:
                if carry == 0:
                    summ = other.s[:leno-lens] +summ
                else:
                    carry3 = 1
                    for i in range(leno-lens):
                        if carry3 == 0:
                            summ = other.s[:leno-lens-i] +summ
                            break
                        else:
                            if other.s[:leno-lens-i][-1] == '0':
                                summ = '1' +summ
                                carry3 = 0
                            else:
                                summ = '0' +summ
                    if carry3 == 1:
                        summ = '1' +summ
        return Binary(summ)
            
            
            
                

    def is_power_of_two(self):
        """ True if self is a power of 2, else - False """
        k = 0
        for i in range(len(self.s)):
            if self.s[i] == '1':
                k += 1
        return k == 1

    def largest_power_of_two(self):
        """ return the largest power of 2 which is <= self """
        if self.s == '0':
            return -float("inf")
        return 2**(len(self.s) -1)
            

    def div3(self):
        """ Returns remainder of self divided by 3 """
        cnt = Binary("0")
        while cnt < self:
            cnt = cnt + Binary("11")
        div = 0
        if self + Binary("1") == cnt:
            div = 2
        if self + Binary("10") == cnt:
            div = 1
        return div

    def div3_new(self):
        """ Returns remainder of self divided by 3 """
        if self.s[0] == '0':
            div = 0
        else:
            div = 1
        if len(self.s) > 1:
            for i in range(1,len(self.s)):
                if self.s[i] == '0':
                    if div == 1:
                        div = 2
                    elif div == 2:
                        div = 1
                else:
                    if div == 1:
                        div = 0
                    elif div == 0:
                        div = 1
        return div
    



############
# QUESTION 2
############

### Tree node class - code from lecture ###

class Tree_node():
    def __init__(self,key,val):
        self.key=key
        self.val=val
        self.left=None
        self.right=None
      
    def __repr__(self):
        return "[" + str(self.left) + \
               " (" + str(self.key) + "," + str(self.val) + ") " \
               + str(self.right) + "]"

### Binary search tree  ###
class BSearch_tree():
    def __init__(self):
        self.root = None

    def insert(self, key, val):
        def insert_help(node,key,val):
            if node == None:
                return Tree_node(key,val)
            if key == node.key:
                node.val = val
            elif key < node.key:
                node.left = insert_help(node.left,key,val)
            else:
                node.right = insert_help(node.right,key,val)
            return node
        self.root = insert_help(self.root,key,val)

    def lookup(self,key):
        def lookup_help(node,key):
            if node == None:
                return None
            if key == node.key:
                return node.val
            elif key < node.key:
                return lookup_help(node.left,key)
            else:
                return lookup_help(node.right,key)
        return lookup_help(self.root,key)

    def sum(self):
        def sum_help(node):
            if node == None:
                return 0
            return node.key + sum_help(node.left)+ sum_help(node.right)
        return sum_help(self.root)

    def find_closest_key(self, search_key):
        def find_closest_key_help(node,search_key):
            if node == None:
                return None
            closekey = node.key
            if search_key < closekey:
                maybeCloser = find_closest_key_help(node.left,search_key)
            if search_key > closekey:
                maybeCloser = find_closest_key_help(node.right,search_key)
            if maybeCloser != None and abs(maybeCloser -search_key) < abs(closekey - search_key):
                closekey = maybeCloser
            return closekey
        return find_closest_key_help(self.root,search_key)

    def is_balanced(self):
        def is_balanced_help(node):
            if node == None:
                return True, 0 #must all be true toghether and sum lengths -1
            lE, lH = is_balanced_help(node.left)
            rE, rH = is_balanced_help(node.right)
            symbol, depth = lE and rE, max(lH,rH) +1
            balanced = symbol and abs(lH-rH) <= 1
            return balanced, depth
        balanced, depth = is_balanced_help(self.root)
        return balanced




############
# QUESTION 3
############

class Polynomial():
    def __init__(self, coeffs_lst):
        self.coeffs = coeffs_lst
        
    def __repr__(self):
        terms = [" + ("+str(self.coeffs[k])+"*x^" + \
                 str(k)+")" \
                 for k in range(len(self.coeffs)) \
                 if self.coeffs[k]!=0]
        terms = "".join(terms)
        if terms == "":
            return "0"
        else:
            return terms[3:] #discard leftmost '+'

    def degree(self):
        return len(self.coeffs) -1

    def evaluate(self, x):
        summ = 0
        p = 1
        for a in self.coeffs:
           summ += a*p
           p = x*p
        return summ

    def derivative(self):
        Poli = Polynomial([])
        if len(self.coeffs) == 1:
            Poli.coeffs.append(0)
            return Poli
        cnt = 1
        for a in self.coeffs[1:]:
            Poli.coeffs.append(cnt*a)
            cnt += 1
        return Poli

    def __eq__(self, other):
        assert isinstance(other, Polynomial)
        if len(self.coeffs) != len(other.coeffs):
            return False
        for i in range(len(self.coeffs)):
            if self.coeffs[i] != other.coeffs[i]:
                return False
        return True

    def __add__(self, other):
        assert isinstance(other, Polynomial)  
        Poli = Polynomial([])
        for i in range(min(len(self.coeffs),len(other.coeffs))):
            Poli.coeffs.append(self.coeffs[i] + other.coeffs[i])
        if len(self.coeffs) > len(other.coeffs):
            for i in range(min(len(self.coeffs),len(other.coeffs)),len(self.coeffs)):
                Poli.coeffs.append(self.coeffs[i])
        if len(self.coeffs) < len(other.coeffs):
            for i in range(min(len(self.coeffs),len(other.coeffs)),len(other.coeffs)):
                Poli.coeffs.append(other.coeffs[i])
        return Poli

    def __neg__(self):
        Poli = Polynomial([])
        for i in range(len(self.coeffs)):
            Poli.coeffs.append(-1*self.coeffs[i])
        return Poli

    def __sub__(self, other):
        assert isinstance(other, Polynomial)
        Poli = Polynomial([])
        negO = -other
        Poli = self + negO
        return Poli

    def __mul__(self, other):
        assert isinstance(other, Polynomial)
        Poli = Polynomial([0 for i in range(other.degree()+self.degree()+1)])
        cnt = 0
        st = 0
        for a in self.coeffs:
            for b in other.coeffs:
                Poli.coeffs[cnt] += a*b
                cnt += 1
            st += 1
            cnt = st
        return Poli
        

    def find_root(self):
         return NR(lambda x: self.evaluate(x),lambda x: self.derivative().evaluate(x))


## code for Newton Raphson, needed in find_root ##
from random import *

def diff_param(f,h=0.001):
    return (lambda x: (f(x+h)-f(x))/h)


def NR(func, deriv, epsilon=10**(-8), n=100, x0=None):
    if x0 is None:
        x0 = uniform(-100.,100.)
    x=x0; y=func(x)
    for i in range(n):
        if abs(y)<epsilon:
            #print (x,y,"convergence in",i, "iterations")
            return x
        elif abs(deriv(x))<epsilon:
            #print ("zero derivative, x0=",x0," i=",i, " xi=", x)
            return None
        else:
            #print(x,y)
            x = x- func(x)/deriv(x)
            y = func(x)
    #print("no convergence, x0=",x0," i=",i, " xi=", x)
    return None




############
# QUESTION 4
############

# a

def suffix_prefix_overlap(lst, k):
    tupleLst = []
    for i in range(len(lst)):
        for j in range(len(lst)):
            if i != j and lst[i][-k:] == lst[j][:k]:
                tupleLst.append((i,j))
    return tupleLst
                

# c
#########################################
### Dict class ###
#########################################

class Dict:
    def __init__(self, m, hash_func=hash):
        """ initial hash table, m empty entries """
        self.table = [ [] for i in range(m)]
        self.hash_mod = lambda x: hash_func(x) % m

    def __repr__(self):
        L = [self.table[i] for i in range(len(self.table))]
        return "".join([str(i) + " " + str(L[i]) + "\n" for i in range(len(self.table))])
              
    def insert(self, key, value):
        """ insert key,value into table
            Allow repetitions of keys """
        i = self.hash_mod(key) #hash on key only
        item = [key, value]    #pack into one item
        self.table[i].append(item) 

    def find(self, key):
        """ returns ALL values of key as a list, empty list if none """
        lst = []
        i = self.hash_mod(key)
        for lsts in self.table[i]:
            if lsts[0] == key:
                lst.append(lsts[1])
        return lst


#########################################
### End Dict class ###
#########################################    

# d
def suffix_prefix_overlap_hash1(lst, k):
    newLst = []
    d = Dict(k)
    for i in range(len(lst)):
        d.insert(lst[i][-k:],i)
    for i in range(len(lst)):
        indexLst = d.find(lst[i][:k])
        for place in indexLst:
            if place != i:
                newLst.append((place,i))
    return newLst


# f
def suffix_prefix_overlap_hash2(lst, k):
    newLst = []
    d1 = {}
    d2 = {i:lst[i][:k] for i in range(len(lst))}
    for i in range(len(lst)):
        if lst[i][-k:] not in d1:
            d1[lst[i][-k:]] = [i]
        else:
            d1[lst[i][-k:]].append(i)
    for i in d2:
        if d2[i] in d1:
            for j in d1[d2[i]]:
                if j != i:
                    newLst.append((j,i))
    return newLst


############
# QUESTION 6
############

# a
def next_row(lst):
    newLst = [1]
    for i in range(len(lst)-1):
        newLst.append(lst[i]+lst[i+1])
    newLst.append(1)
    return newLst

# b   
def generate_pascal():
    frow = [1]
    while True:
        yield frow
        frow = next_row(frow)

# c
def generate_bernoulli():
    frow = [1]
    last = 2
    while True:
        yield frow
        frow = next_row(frow)
        frow[len(frow)-1] = last
        last *= 2



########
# Tester
########

def test():

    #Question 1
    
    a = Binary("101")
    b = Binary("10001")
    if (a < b) != True:
        print("error in Binary() class __lt__ function")
    if (a + b) != Binary("10110"):
        print("error in  Binary() class __add__ function") 
    c = Binary("10000")
    if a.is_power_of_two() != False or c.is_power_of_two() != True:
        print("error in Binary() class is_power_of_two function")
    if a.largest_power_of_two() != 4 or c.largest_power_of_two() != 16:
        print("error in Binary() class largest_power_of_two function")
    if a.div3() != 2 or b.div3() != 2:
        print("error in Binary() class div3 function")
    if a.div3_new() != 2 or b.div3_new() != 2:
        print("error in Binary() class div3_new function")

    #Question 2
    
    bin_tree = BSearch_tree() 
    bin_tree.insert(2,"hi")
    bin_tree.insert(4,"bye")
    bin_tree.insert(1,"hello")
    bin_tree.insert(3,"lovely")

    if (bin_tree.sum() != 10):
        print("error in BSearch_Tree")
    if (bin_tree.lookup(3) != "lovely"):
        print("error in BSearch_Tree")
    if (bin_tree.lookup(100) != None):
        print("error in BSearch_Tree")
    if (bin_tree.find_closest_key(5) != 4):
        print("error in BSearch_Tree")
    if (bin_tree.is_balanced() != True):
        print("error in BSearch_Tree")
    bin_tree.insert(5,"dear")
    if (bin_tree.is_balanced() != True):
        print("error in BSearch_Tree")
    bin_tree.insert(6,"tea")
    if (bin_tree.is_balanced() != False):
        print("error in BSearch_Tree")


    #Question 3
        
    q = Polynomial([0, 0, 0, 6])
    if str(q) != "(6*x^3)":
        print("error in Polynomial.__init__ or Polynomial.in __repr__")
    if q.degree() != 3:
        print("error in Polynomial.degree")
    p = Polynomial([3, -4, 1])
    if p.evaluate(10) != 63:
        print("error in Polynomial.evaluate")
    dp = p.derivative()
    ddp = p.derivative().derivative()
    if ddp.evaluate(100) != 2:
        print("error in Polynomial.derivative")
    if not p == Polynomial([3, -4, 1]) or p==q:
        print("error in Polynomial.__eq__")
    r = p+q
    if r.evaluate(1) != 6:
        print("error in Polynomial.__add__")
    if not (q == Polynomial([0, 0, 0, 5]) + Polynomial([0, 0, 0, 1])):
        print("error in Polynomial.__add__ or Polynomial.__eq__")
    if (-p).evaluate(-10) != -143:
        print("error in Polynomial.__neg__")
    if (p-q).evaluate(-1) != 14:
        print("error in Polynomial.__sub__")
    if (p*q).evaluate(2) != -48:
        print("error in Polynomial.__mult__")
    if (Polynomial([0])*p).evaluate(200) != 0:
        print("error in Polynomial class")
    root = p.find_root()
    if root-3 > 10**-7 and root-1 > 10**-7:
        print("error in Polynomial.find_root")

    #Question 4

    s0 = "a"*100
    s1 = "a"*60+"b"*40
    s2 = "a"*10+"b"*40+"c"*50
    lst = [s0,s1,s2]
    k=50
    if suffix_prefix_overlap(lst, k) != [(0, 1), (1, 2)]:
        print("error in suffix_prefix_overlap")
    if suffix_prefix_overlap_hash1(lst, k) != [(0, 1), (1, 2)]:
        print("error in suffix_prefix_overlap_hash1")
    if suffix_prefix_overlap_hash2(lst, k) != [(0, 1), (1, 2)]:
        print("error in suffix_prefix_overlap_hash2")

    #Question 6

    gp = generate_pascal()
    if gp == None:
        print("error in generate_pascal()")
    elif next(gp)!=[1] or next(gp)!=[1,1] or next(gp)!=[1,2,1]:
        print("error in generate_pascal()")
