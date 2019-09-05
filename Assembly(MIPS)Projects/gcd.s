.data
msgToUser: .asciiz "\nThe value is "
endNewline: .asciiz "\n"
.text
.globl main
main:
	#getting first inputt (x)
	li $v0,5
	syscall
	move $s0,$v0
	
	#getting second input (y)
	li $v0,5
	syscall
	move $s1,$v0
	
	#setting the arguments for the gcd function
	move $a0,$s0
	move $a1,$s1
	jal gcd
	
	move $t0,$v0 #result from gcd
	#print according to ex3
	li $v0,4 #for printing
	la $a0,msgToUser #load adress of string to print
	syscall #print
	
	#print the gcd returned value
	li $v0,1
	move $a0,$t0 #move the result into argument
	syscall #print result
	
	#print newline
	li $v0,4
	la $a0,endNewline #load adress of newline
	syscall #print
	
	#exit
	li $v0,10
	syscall
	
	gcd: #by euclides: if (b == 0) {if (b == 0) {return a;} return Gcd(b, a % b);, but not with recursion
		move $t0,$a0
		move $t1,$a1
		bne $t0,$t1 loops #x!= y
		#else if x == y
		beq $t0,$zero,returnOne #if x == y == 0
		#else if x == y != 0
		j returnX
	loops: #x != y
		slt $t3,$t0,$t1 # $t3 = 1 if x < y else $t3 = 0
		bne $t3,$zero,loopYbiggerthenX
		j loopXbiggerthenY #else
	loopYbiggerthenX:
		beq $t0,$zero,returnY #if (x == 0) return y;
		#else:
		#y%x:
		div $t2,$t1,$t0 #$t2 = y/x
		mfhi $t2 #$t2 contains y%x
		move $t1,$t0 #y = x
		move $t0,$t2 #x = y%x
		j loopYbiggerthenX
	loopXbiggerthenY:
		beq $t1,$zero,returnX #if (y == 0) return x;
		#else:
		#x%y:
		div $t2,$t0,$t1 #$t2 = x/y
		mfhi $t2 #$t2 contains x%y
		move $t0,$t1 #x = y
		move $t1,$t2 #y = x%y
		j loopXbiggerthenY
		
	returnX:
		move $v0,$t0
		jr $ra
	returnY:
		move $v0,$t1
		jr $ra
	returnOne:
		li $v0,1
		jr $ra