import pandas as pd
from pandas import DataFrame
from matplotlib import pyplot as plt
import seaborn as sns
from pypdf2 import PdfFileReader, PdfFileWriter

def updateDataset(check, data):
    temp=[]
    for i in range(len(check)):
        if check[i] == 1:
            temp.append(data[i])
    return temp

def updateColumn(check, column):
    temp=[]
    for i in range(len(check)):
        if check[i] == 1:
            temp.append(column[i])
    return temp

def checkData(data,length):
    temp=[]
    for i in range(length):
        try:
            float(data[i][0])
            temp.append(1)
        except :
            temp.append(0)
    return temp

def convertData(data):
    temp=[]
    length = len(data[0].split(","))
    for i in range(length):
        test=[]
        for j in range(len(data)):
            test.append(data[j].split(',')[i])
        temp.append(test)
    return temp

if __name__ == '__main__':
    f=open('data/iris.csv' ,'r')
    data= f.readlines()
    f.close()
    column=data[0].split(",")
    column[-1] =column[-1].replace('\n','')
    data=data[1:]
    data=convertData(data)
    check = checkData(data,len(column))
    print(check)
    newColumn = updateColumn(check,column)
    newData = updateDataset(check,data)
    print (len(newData),len(newColumn))
    print(newColumn)
    df = DataFrame(newData)
    df= df.transpose()
    df= df.apply(pd.to_numeric)
    #correlation_matrix(df,newColumn)
    correlation = df.corr()
    sns.heatmap(correlation,
                xticklabels=newColumn,
                yticklabels=newColumn)
    #plt.show()
    plt.savefig("data/iris.pdf")
