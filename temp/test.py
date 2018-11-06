import urllib.request as ur


data=ur.urlopen("http://127.0.0.1:8000/groups/?name='anything'")

test=data.read()
print(test)
f=open('temp.json','w')
f.write(str(test))
f.close()