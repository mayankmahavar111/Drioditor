import pandas as pd
from pandas import DataFrame
from matplotlib import pyplot as plt
import seaborn as sns
from PyPDF2 import PdfFileReader, PdfFileWriter
from fpdf import FPDF
from algorithm import knn,naive,SVM,nn
import time

def details(data,df,newcolumn,image):
    pdf = FPDF(orientation='P', unit='mm',format='A4')
    pdf.add_page()
    pdf.set_font("Arial", size=12)

    epw = pdf.w - 2 * pdf.l_margin
    col_width = epw / 4

    pdf.set_font('Times', 'B', 35)
    pdf.cell(epw, 0.0, 'Drioditor', align='C')
    pdf.set_font('Times', '', 10.0)

    pdf.cell(200, 10, txt="\n", ln=1)
    pdf.cell(200, 10, txt="\n", ln=1)
    pdf.set_font('Times', 'B', 20)
    pdf.cell(epw, 0.0, 'Recommendation Report', align='C')
    pdf.set_font('Times', '', 10.0)
    pdf.ln(0.5)

    column = len(data)
    row = len(data[0])

    pdf.cell(200, 10, txt="\n", ln=1)
    pdf.cell(200, 10, txt="\n", ln=1)
    pdf.cell(200, 10, txt="Rows : {}".format(row), ln=1)
    pdf.cell(200, 10, txt="Columns : {}".format(column), ln=1)
    pdf.cell(200, 10, txt="Missing Values : {}".format(0), ln=1)

    avg=df.mean()
    median=df.median()
    mode=df.mode()[:1]



    pdf.set_font('Times', 'B', 14.0)
    pdf.set_font('Times', '', 10.0)
    pdf.ln(0.5)

    temp=[]
    temp.append("Feature \t" + "Average\t" +"Median\t" + "Mode\n" )
    for i in range(len(newcolumn)):
        temp.append(str(newcolumn[i])+"\t"+str(round(avg[i],5)) + "\t" + str(median[i])+"\t"+str(mode[i][0])+"\n")
    #print(temp)

    th = pdf.font_size

    """
    for row in temp:
        for datum in row.split(','):
            pdf.cell(col_width, th, str(datum), border=1)
        pdf.ln(th)
    pdf.ln(4 * th)

    pdf.set_font('Times', 'B', 14.0)
    pdf.cell(epw, 0.0, 'With more padding', align='C')
    pdf.set_font('Times', '', 10.0)

    """
    for row in temp:
        for datum in row.split(','):
            pdf.cell(col_width, 2 * th, str(datum), border=1)
        pdf.ln(2 * th)

    pdf.add_page()
    pdf.set_font('Times', 'B', 14)
    pdf.cell(epw, 0.0, 'Correlation Heatmap', align='C')
    pdf.set_font('Times', '', 10.0)
    pdf.ln(0.5)
    pdf.image('{}'.format(image),x=10,y=50)

    pdf.add_page()
    pdf.set_font('Times', 'B', 14)
    pdf.cell(epw, 0.0, 'Algorithm ', align='C')
    pdf.set_font('Times', '', 10.0)
    pdf.ln(0.5)


    max_accuracy,accuracy_name=0,""
    min_time, time_name=1000,""

    t=time.time()
    accuracy,tnr,tpr = knn('{}'.format(filename))
    t=time.time() -t

    if max_accuracy<accuracy :
        max_accuracy=accuracy
        accuracy_name='Knn'
    if min_time>t:
        min_time=t
        time_name='Knn'

    pdf.set_font('Times', 'B', 12)
    pdf.cell(200, 10, txt="Knn", ln=1)
    pdf.set_font('Times', '', 10.0)
    pdf.cell(210, 10, txt="Accuracy : {}".format(accuracy), ln=1)
    pdf.cell(210, 10, txt="TNR : {}".format(tnr), ln=1)
    pdf.cell(210, 10, txt="TPR : {}".format(tpr), ln=1)
    pdf.cell(210, 10, txt="Time Taken : {}".format(t), ln=1)
    #print(accuracy,tnr,tpr,t)

    t = time.time()
    accuracy, tnr, tpr = naive('{}'.format(filename))
    t = time.time() - t

    if max_accuracy<accuracy :
        max_accuracy=accuracy
        accuracy_name='Naive Bayes'
    if min_time>t:
        min_time=t
        time_name='Naive Bayes'

    pdf.set_font('Times', 'B', 12)
    pdf.cell(200, 10, txt="Naive Bayes", ln=1)
    pdf.set_font('Times', '', 10.0)
    pdf.cell(210, 10, txt="Accuracy : {}".format(accuracy), ln=1)
    pdf.cell(210, 10, txt="TNR : {}".format(tnr), ln=1)
    pdf.cell(210, 10, txt="TPR : {}".format(tpr), ln=1)
    pdf.cell(210, 10, txt="Time Taken : {}".format(t), ln=1)
    #print(accuracy, tnr, tpr, t)

    t = time.time()
    accuracy, tnr, tpr = SVM('{}'.format(filename))
    t = time.time() - t

    if max_accuracy<accuracy :
        max_accuracy=accuracy
        accuracy_name='SVM'
    if min_time>t:
        min_time=t
        time_name='SVM'

    pdf.set_font('Times', 'B', 12)
    pdf.cell(200, 10, txt="SVM", ln=1)
    pdf.set_font('Times', '', 10.0)
    pdf.cell(210, 10, txt="Accuracy : {}".format(accuracy), ln=1)
    pdf.cell(210, 10, txt="TNR : {}".format(tnr), ln=1)
    pdf.cell(210, 10, txt="TPR : {}".format(tpr), ln=1)
    pdf.cell(210, 10, txt="Time Taken : {}".format(t), ln=1)
    #print(accuracy, tnr, tpr, t)

    t = time.time()
    accuracy, tnr, tpr = nn('{}'.format(filename))
    t = time.time() - t

    if max_accuracy<accuracy :
        max_accuracy=accuracy
        accuracy_name='Neural Network'
    if min_time>t:
        min_time=t
        time_name='Neural Network'

    pdf.set_font('Times', 'B', 12)
    pdf.cell(200, 10, txt="Neural Network", ln=1)
    pdf.set_font('Times', '', 10.0)
    pdf.cell(210, 10, txt="Accuracy : {}".format(accuracy), ln=1)
    pdf.cell(210, 10, txt="TNR : {}".format(tnr), ln=1)
    pdf.cell(210, 10, txt="TPR : {}".format(tpr), ln=1)
    pdf.cell(210, 10, txt="Time Taken : {}".format(t), ln=1)
    #print(accuracy, tnr, tpr, t)

    pdf.add_page()
    pdf.set_font('Times', 'B', 14)
    pdf.cell(epw, 0.0, 'Recommendation', align='C')
    pdf.set_font('Times', '', 10.0)
    pdf.ln(0.5)

    pdf.cell(200, 10, txt="Based on Accuracy    : {} with accuracy {}%".format(accuracy_name,max_accuracy*100), ln=1)
    pdf.cell(200, 10, txt="Based on Time        : {} with time taken {}s".format(time_name,round(min_time,6)), ln=1)
    #print(max_accuracy,accuracy_name)
    #print(min_time,time_name)

    pdf.output('data/output.pdf', 'F')


def pdfWriter(a,b):
    output = PdfFileWriter()
    pdfOne = PdfFileReader(open("{}".format(a), "rb"))
    pdfTwo = PdfFileReader(open("{}".format(b), "rb"))

    output.addPage(pdfOne.getPage(0))
    output.addPage(pdfTwo.getPage(0))

    outputStream = open(r"data/output.pdf", "wb")
    output.write(outputStream)
    outputStream.close()


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

    filename='data/iris.csv'
    f=open('{}'.format(filename) ,'r')
    data= f.readlines()
    f.close()
    column=data[0].split(",")
    column[-1] =column[-1].replace('\n','')
    data=data[1:]
    data=convertData(data)
    check = checkData(data,len(column))
    #print(check)
    newColumn = updateColumn(check,column)
    newData = updateDataset(check,data)
    #print (len(newData),len(newColumn))
    #print(newColumn)
    df = DataFrame(newData)
    df= df.transpose()
    df= df.apply(pd.to_numeric)
    correlation = df.corr()
    sns.heatmap(correlation,
                xticklabels=newColumn,
                yticklabels=newColumn,)
    #plt.show()
    plt.savefig('data/test.jpg')
    details(data,df,newColumn,'data/test.jpg')
