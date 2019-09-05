

.text				
.globl main

main:
	addi	$sp, $29, -20
	sw	$31, 0($29)	# save register 31 (ra)  (since there is a nested / recursive procedure)
	sw	$0, 4($29)	# Space for parameter n on stack

	li 	$v0, 4		# Ask for number
	la	$a0, ask	
	syscall


	li	$v0, 5		# read int
	syscall			# $v0 holds the integer - n
	move $t1 , $v0
	li   $a1  'A'		# $a1-3 holds the pegs signs
	li   $a2  'B'
	li   $a3  'C'
	
	sw	$t1, 4($29)	# save n on stack
	sw      	$a1,  8($29)	# save 'A' on stack
	sw	$a2,  12($29)	# save 'B' on stack
	sw	$a3,  16($29)	# save 'c' on stack
	
	move  	$a0, $v0	# prepare argument for fact
	jal 	hanoi		# call hanoi
		
	move 	$t0, $v0	# save result temporarily
	
	lw	$31, 0($29)	# Restore register 31
	addu	$29, $29, 8	# pop stack
	li   	$v0, 10          # system call for exit
	syscall               # we are out of here

	


	


hanoi:
	lw         $t5, 4($29)
	sw 	$31, 0($29)	 
	bne	$t5, 1, recurs	# Need to recurse
	
	li 	$v0, 4		# print the result
	la	$a0, str1
	syscall

	
	li 	$v0,4
	la	$a0, 8($29)
	syscall
	
	li 	$v0,4
	la	$a0, str5
	syscall
	
	li 	$v0,4
	la	$a0, 16($29)
	syscall
	
	lw	$31, 0($29)	# Restore register 31
	addu	$29, $29,20	# Pop stack
	jr	$31		# return 
	

recurs:
	lw 	$t7, 4($29)
	lw	$t3, 8($29)
	lw	$t4  12($29)
	lw	$t5   16($29)
	addi	$29, $29, -20	# make space for parameters on stack
	
	sub     	$t7, $t7, 1
	
	
	
	
	sw	$31, 0($29)	# save register 31
	sw	$t7, 4($29)	# save argument
	sw         $t3,  8($29)	# save 'A' on stack
	sw	$t5,  12($29)	# save 'B' on stack
	sw	$t4,  16($29)	# save 'c' on stack
	jal	hanoi
	
	li 	$v0, 4		# intermediate printing of the result
	la	$a0, str1
	syscall

	
	li 	$v0,4
	la	$a0, 8($29)
	syscall
	
	li 	$v0,4
	la	$a0, str5
	syscall
	
	li 	$v0,4
	la	$a0, 16($29)
	syscall
	
	addi	$29, $29, -20	# make space for parameters on stack
	
	lw	$t3, 8($29)
	lw	$t4  12($29)
	lw	$t5   16($29)
	
	
	lw 	$t7, 4($29)	#sc recrus
	sw	$31, 0($29)	# save register 31
	sw	$t7, 4($29)	# save argument
	sw         $t5,  8($29)	# save 'A' on stack
	sw	$t3,  12($29)	# save 'B' on stack
	sw	$t4,  16($29)	# save 'c' on stack
	jal	hanoi
	
	lw	$31, 0($29)	# Restore register 31
	addu	$29, $29,20	# Pop stack
	jr	$31		# return 

	


.data				#indicates that succeeding lines contain static data
				#.asciiz stores a null-terminated string in memory
ask:  .asciiz "\nEnter number > "	
str1: .asciiz "\n"
str2: .asciiz "A"
str3: .asciiz "B"
str4: .asciiz "C"
str5: .asciiz ">"


	

