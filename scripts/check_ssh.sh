#! /bin/bash
for end_of_ip in 85 86 87 88 89 90 91 92 94 95 96 97
do
        ssh pc@116.56.136.${end_of_ip} "cat ~/.ssh/authorized_keys"
done

