import numpy as np
from sklearn import neighbors,svm,naive_bayes,neural_network
from sklearn.metrics import confusion_matrix
from google_drive_downloader import GoogleDriveDownloader as gdd
def downloadDataset(url,name):
	try:
		id = url.split('=')[-1]
		gdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))
		return True
	except Exception as e:
		return e
def naive(dataset):
	temp=  downloadDataset('https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N','iris')
	if temp!=True:
		return 
	f=open('{}'.format(dataset),'r')
	data=f.readlines()[1:]
	temp=[]

	for i in range(len(data)):
		temp.append(data[i].split(',')[:-1])

	X=np.array(temp)
	X = X.astype(np.float)
	Y=[]

	for i in range(len(data)):
		Y.append(data[i].split(',')[-1])

	clf=naive_bayes.GaussianNB()
	clf.fit(X,Y)
	pred=clf.predict(X)
	cm=confusion_matrix(Y,pred)

	tp=cm[0][0]
	fp=cm[0][1]
	fn=cm[1][0]
	tn=cm[1][1]

	accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)
	tnr=round(float(tn)/float(tn+fp),4)
	tpr=round(float(tp)/float(tp+fn),4)

	return accuracy,tnr,tpr
if __name__ == '__main__':
	filename='data/iris.csv' 
	accuracy, tnr, tpr = naive('{}'.format(filename))
	print(accuracy, tnr , tpr)