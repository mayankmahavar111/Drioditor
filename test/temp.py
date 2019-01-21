import os

x= os.popen('guesslang -a -i test.txt ').readlines()

print(x[-1].split(" ")[-1])