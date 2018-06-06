cd ~/.ssh
ssh-keygen -t rsa
touch authorized_keys
chmod +rw authorized_keys
cat id_rsa.pub >> authorized_keys
scp authorized_keys pc@116.56.139.91:~
