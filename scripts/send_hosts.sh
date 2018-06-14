#! /bin/bash
for end_of_ip in 85 86 87 88 89 90 91 92 94 95 96 97
do 
	echo ${end_of_ip}
	scp hosts root@116.56.136.${end_of_ip}:~
	ssh root@116.56.136.${end_of_ip} "cat hosts > /etc/hosts"
	ssh root@116.56.136.${end_of_ip} "/etc/init.d/networking restart"
done
