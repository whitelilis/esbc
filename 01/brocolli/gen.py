import logging
import random

def main():
	USER_NUM = 800000000
	LOG_NUM = 5000000000
	logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(message)s',
                    datefmt  = '%Y-%m-%d %H:%M:%S',
                    filename='log',
                    filemode='w')

	for i in range(0,1000):
		state = random.choice(['in','out'])
		user_id = str(random.randrange(1,USER_NUM,1))
		logging.debug(user_id+' ' + state)





if __name__ == '__main__':
	main()
