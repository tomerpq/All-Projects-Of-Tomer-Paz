#Tomer Paz - 315311365
#!/bin/bash
let summ=0
array=($1)
cat $2 | 
( while read line
do
	pass=1
	lineArray=($line)
	if [[ ${array[0]} != ${lineArray[0]} ]]
	then
		pass=0
	fi
	if [[ ${array[1]} != ${lineArray[1]} ]]
	then
		pass=0
	fi
	if [[ $pass == 1 ]]
	then
		echo $line
		tmp=$[${lineArray[2]}]
		summ=$[$summ+$tmp]
	fi
done
echo Total balance: $summ )
