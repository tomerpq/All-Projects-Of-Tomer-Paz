#Tomer Paz - 315311365
#!/bin/bash
num=1
cat $1 | 
while read line
do
	for word in $line 
	do
		if test $word = $2
		then
			echo $num $line
			break	
		fi
	done
	num=$[$num+1]
done
