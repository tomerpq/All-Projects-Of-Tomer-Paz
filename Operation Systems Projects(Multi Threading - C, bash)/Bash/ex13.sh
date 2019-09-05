#Tomer Paz - 315311365
#!/bin/bash
lane=${1//[[:blank:]]/}
cd "$1"
ls -v |
while read fn
do
	if [[ $fn == $2 ]]
	then
		cat $fn
	else
		a="$lane/$fn"
		if [[ -d $a ]]
		then
			cd "$a"
			ls -v |
			while read insideFn
			do
				if [[ $insideFn == $2 ]]
				then
					cat $insideFn
				fi
			done
			cd ..
		fi
	fi
done
