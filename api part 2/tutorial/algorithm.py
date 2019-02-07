import numpy as np
from sklearn import neighbors,svm,naive_bayes,neural_network
from sklearn.metrics import confusion_matrix
import random

def splitdataset(X,Y,ratio):
    length = int(ratio* len(X))
    size=len(X[0])

    temp=[]
    for i in range(length):
        temp.append(" ")
    temp_x,temp_y=np.zeros(shape=(length,size)),temp

    temp = []
    for i in range(len(X)-length):
        temp.append(" ")
    test_x, test_y = np.zeros(shape=(len(X)-length, size)),temp

    temp=[]
    i=0
    while len(temp) <length :
        randint= random.randint(0,length)
        if randint not in temp:
            temp_x[i]=X[randint]
            temp_y[i]=str(Y[randint])
            i=i+1
            temp.append(randint)
    temp_y=np.array(temp_y)

    j=0
    for i in range(len(X)):
        if i not in temp:
            test_x[j]=X[i]
            test_y[j]=str(Y[i])
            j=j+1
            temp.append(j)
    test_y=np.array(test_y)

    return temp_x,temp_y,test_x,test_y



def knn(dataset):
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

    clf=neighbors.KNeighborsClassifier()
    clf.fit(X,Y)
    pred=clf.predict(X)
    cm=confusion_matrix(Y,pred)

    #print(cm)

    tp=cm[0][0]
    fp=cm[0][1]
    fn=cm[1][0]
    tn=cm[1][1]

    accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)
    tnr=round(float(tn)/float(tn+fp),4)
    tpr=round(float(tp)/float(tp+fn),4)

    return accuracy,tnr,tpr


def naive(dataset):
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

def SVM(dataset):
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

    clf=svm.LinearSVC()
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

def nn(dataset):
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

    clf=neural_network.MLPClassifier()
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

