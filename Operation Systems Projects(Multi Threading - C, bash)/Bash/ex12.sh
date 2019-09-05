#Tomer Paz - 315311365
#!/bin/bash
ls -v $1 |
while read fn
do
	if [[ $fn == *.txt ]]
	then
		echo $fn "is a file"
	fi
done
ls -v $1 |
while read fn
do
	a="$1/$fn"
	if [[ -d $a ]]
	then
		echo $fn "is a directory"
	fi
done
