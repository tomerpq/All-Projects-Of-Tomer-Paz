#Skeleton file for HW6 - Winter 2016-2017 - extended intro to CS

#Add your implementation to this file

#You may add other utility functions to this file,
#but you may NOT change the signature of the existing ones.

#Change the name of the file to include your ID number (hw6_ID.py).

from matrix import *
from huffman import *

############
# QUESTION 1
############

def fingerprint(mat):
    assert isinstance(mat,Matrix)
    k,makesure = mat.dim()
    assert k==makesure

    return sum(mat[i,j] for i in range(k) for j in range(k))

def move_right(mat, i, j, k, fp):
    newfp = fp
    for l in range(i,i+k):
        newfp += mat[l,j+k]
        newfp -= mat[l,j]
    return newfp


def move_down(mat, i, j, k, fp):
    newfp = fp
    for l in range(j,j+k):
        newfp += mat[i+k,l]
        newfp -= mat[i,l]
    return newfp

    
def has_repeating_subfigure(mat, k):
    cfp = fingerprint(mat[0:k,0:k])
    newfp = cfp
    fps = {cfp}
    n,m = mat.dim()
    for i in range(0,):
        
        for j in range(0,m-k):
            newfp = move_right(mat,i,j,k,newfp)
            if newfp in fps:
                return True
        move_down(

def problematic_matrix():
    im = Matrix(4,4)
    #add your code here
    return im


############
# QUESTION 2
############

def weighted_length(D, W):
    pass #remove and add your implementation here

def optimal(D, W):
    pass #remove and add your implementation here

def corpus():
    pass #change to your corpus string


############
# QUESTION 3
############


###################################################
#Lempel Ziv code from lecture
###################################################

def maxmatch(T,p,w=2**12-1,max_length=2**5-1):
   """ finds a maximum match of length k<=2**5-1 in a
   w long window, T[p:p+k] with T[p-m:p-m+k].
   Returns m (offset) and k (match length) """
   assert isinstance(T,str)
   n=len(T)
   maxmatch=0
   offset=0
   for m in range(1,min(p+1,w)):
      k = 0
      while k<min(max_length,n-p)and T[p-m+k] == T[p+k]:
        # at this point, T[p-m:p-m+k]==T[p:p+k]
        k = k+1
      if maxmatch<k:  
         maxmatch=k
         offset=m
   return offset,maxmatch
# returned offset is smallest one (closest to p) among
# all max matches (m starts at 1)


def lz77_compress2(text,w=2**12-1,max_length=2**5-1):
   """LZ77 compression of an ascii text. Produces
      a list comprising of either ascii character
      or by a pair [m,k] where m is an offset and
      k is a match (both are non negative integers)"""
   result = []
   out_string=""
   n=len(text)
   p=0
   while p<n:
      if ord(text[p])>=128: continue
      m,k=maxmatch(text,p,w,max_length)
      if k<3:   # modified from k<2
         result.append(text[p]) #  a single char
         p+=1
      else:
         result.append([m,k])   # three or more chars in match
         p+=k
   return(result)  # produces a list composed of chars and pairs
            

def inter_to_bin(lst,w=2**12-1,max_length=2**5-1):
   """ converts intermediate format compressed list
       to a string of bits"""
   offset_width=math.ceil(math.log(w,2))
   match_width=math.ceil(math.log(max_length,2))
#   print(offset_width,match_width)   # for debugging
   result=[]
   for elem in lst:
      if type(elem)==str:
         result.append("0")
         result.append('{:07b}'.format(ord(elem)))
      elif type(elem)==list:
         result.append("1")
         m,k=elem
         result.append('{num:0{width}b}'.format
                       (num=m, width=offset_width))
         result.append('{num:0{width}b}'.
                       format(num=k, width=match_width))
         
   return "".join(ch for ch in result)
   

###################################################
#End of Lempel Ziv code from lecture
###################################################


def lz77_compress2_no_future(text,w=2**12-1,max_length=2**5-1):
   result = []
   out_string=""
   n=len(text)
   p=0
   while p<n:
      if ord(text[p])>=128: continue
      m,k = maxmatch_new(text,p,w,max_length) #maxmatch_new is called here!
      if k<3:  
         result.append(text[p]) #  a single char
         p+=1
      else:
         result.append([m,k])   # three or more chars in match
         p+=k
   return(result)  
       
def maxmatch_new(T,p,w=2**12-1,max_length=2**5-1):
   assert isinstance(T,str)
   pass #remove and add your implementation


def lz_Q1():
    pass #remove and add your implementation here

def lz_Q2():
    pass #remove and add your implementation here

def lz_Q3():
    pass #remove and add your implementation here
 

############
# QUESTION 4
############

# (1)
def upside_down(im):
    n,m = im.dim()
    im2 = Matrix(n,m)
    pass #replace this with your 3 lines of code
    return im2

# Rest of the question

## Code from the lectures of items, local_operator - DO NOT CHANGE ##

def items(mat):
    '''flatten mat elements into a list'''
    n,m = mat.dim()
    lst = [mat[i,j] for i in range(n) for j in range(m)]
    return lst

def local_operator(A, op, k=1):
    n,m = A.dim()
    res = A.copy()  # brand new copy of A
    for i in range(k,n-k):
        for j in range(k,m-k):
            res[i,j] = op(items(A[i-k:i+k+1,j-k:j+k+1]))
    return res

## end of code from lectures


def segment(im, thrd):
    ''' Binary segmentation of image im by threshold thrd '''
    pass #remove and add your implementation here

def dilate(im, k=1):
    pass #remove and add your implementation here

def diff(A, B):
    assert A.dim()==B.dim()
    pass #remove and add your implementation here


def edges(im, k, thr):
  pass #remove and add your implementation here





########
# Tester
########

def test():
    #Question 1
    im = Matrix.load("./sample.bitmap")
    k = 2
    if fingerprint(im[:k,:k]) != 384 or \
       fingerprint(im[1:k+1,1:k+1]) != 256 or \
       fingerprint(im[0:k,1:k+1]) != 511:
        print("error in fingerprint()")
    if move_right(im, 0, 0, k, 384) != 511:
        print("error in move_right()")
    if move_down(im, 0, 1, k, 511) != 256:
        print("error in move_down()")
    if has_repeating_subfigure(im, k) != True or\
       has_repeating_subfigure(im, k=3) != False:
        print("error in repeating_subfigure()")
   
    #Question 2
    if weighted_length(["0", "11", "10"], [5, 1, 3]) != 13:
        print("error in weighted_length()")
    if optimal(["0", "11", "10"], [5, 1, 3]) != True or\
       optimal(["0", "11", "10"], [3, 1, 5]) != False:
        print("error in optimal()")

    #Question 3
    if lz77_compress2_no_future("aaaaaa") != ['a', 'a', 'a', [3,3]] :
        print("error in maxmatch_new()")

    #Question 4
    m1 = Matrix(4,4,0)
    m1[0,0] = 20
    m1[1,0] = 60
    m2 = segment(m1,10)
    if m2.rows != [[255, 0, 0, 0], [255, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]:
        print("error in segment()")
    m3 = dilate(m2,1)
    if m3.rows != [[255, 0, 0, 0], [255, 255, 0, 0], [0, 255, 0, 0], [0, 0, 0, 0]]:
        print("error in dilate()")
    m4 = diff(m3,m2)
    if m4.rows != [[0, 0, 0, 0], [0, 255, 0, 0], [0, 255, 0, 0], [0, 0, 0, 0]]:
        print("error in diff()")
    if edges(m1,1,10) != m4:
        print("error in edges()")
   
    

