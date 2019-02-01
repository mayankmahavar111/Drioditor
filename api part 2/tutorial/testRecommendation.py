import pandas as pd
from pandas import DataFrame
from matplotlib import pyplot as plt
import seaborn as sns
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


