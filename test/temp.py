import os

x= os.popen('guesslang -a -i test.txt ').readlines()

print(x)