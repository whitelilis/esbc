cd ~/.ssh
ssh_keygen -t rsa
sudo cat id_rsa.pub >> authorized_keys
scp authorized_keys pc@116.56.136.90:~/.ssh
scp authorized_keys pc@116.56.136.89:~/.ssh
scp authorized_keys pc@116.56.136.88:~/.ssh
scp authorized_keys pc@116.56.136.87:~/.ssh
scp authorized_keys pc@116.56.136.97:~/.ssh
scp authorized_keys pc@116.56.136.96:~/.ssh
scp authorized_keys pc@116.56.136.95:~/.ssh
scp authorized_keys pc@116.56.136.94:~/.ssh
scp authorized_keys pc@116.56.136.93:~/.ssh
scp authorized_keys pc@116.56.136.92:~/.ssh

