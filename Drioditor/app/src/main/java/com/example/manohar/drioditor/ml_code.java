package com.example.manohar.drioditor;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class ml_code extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_code);

        Bundle values = getIntent().getExtras();
        if (values != null) {
            String url = values.getString("url");
            String dataset_name=values.getString("dataset_name");
            Log.i("values",url+""+dataset_name);
        }
        TextView out ;
        out = (TextView) findViewById(R.id.input_ml_code);
        out.setText("import pandas as pd\n" +
                "from pandas import DataFrame\n" +
                "from matplotlib import pyplot as plt\n" +
                "import seaborn as sns\n" +
                "from PyPDF2 import PdfFileReader, PdfFileWriter\n" +
                "from fpdf import FPDF\n" +
                "from algorithm import knn,naive,SVM,nn\n" +
                "import time\n" +
                "\n" +
                "def details(data,df,newcolumn,image):\n" +
                "    pdf = FPDF(orientation='P', unit='mm',format='A4')\n" +
                "    pdf.add_page()\n" +
                "    pdf.set_font(\"Arial\", size=12)\n" +
                "\n" +
                "    epw = pdf.w - 2 * pdf.l_margin\n" +
                "    col_width = epw / 4\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 35)\n" +
                "    pdf.cell(epw, 0.0, 'Drioditor', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 20)\n" +
                "    pdf.cell(epw, 0.0, 'Recommendation Report', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.ln(0.5)\n" +
                "\n" +
                "    column = len(data)\n" +
                "    row = len(data[0])\n" +
                "\n" +
                "    pdf.cell(200, 10, txt=\"\\n\", ln=1)\n" +
                "    pdf.cell(200, 10, txt=\"\\n\", ln=1)\n" +
                "    pdf.cell(200, 10, txt=\"Rows : {}\".format(row), ln=1)\n" +
                "    pdf.cell(200, 10, txt=\"Columns : {}\".format(column), ln=1)\n" +
                "\n" +
                "    avg=df.mean()\n" +
                "    median=df.median()\n" +
                "    mode=df.mode()[:1]\n" +
                "\n" +
                "\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 14.0)\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.ln(0.5)\n" +
                "\n" +
                "    temp=[]\n" +
                "    temp.append(\"Feature \\t\" + \"Average\\t\" +\"Median\\t\" + \"Mode\\n\" )\n" +
                "    for i in range(len(newcolumn)):\n" +
                "        temp.append(str(newcolumn[i])+\"\\t\"+str(round(avg[i],5)) + \"\\t\" + str(median[i])+\"\\t\"+str(mode[i][0])+\"\\n\")\n" +
                "    #print(temp)\n" +
                "\n" +
                "    th = pdf.font_size\n" +
                "\n" +
                "    \"\"\"\n" +
                "    for row in temp:\n" +
                "        for datum in row.split(','):\n" +
                "            pdf.cell(col_width, th, str(datum), border=1)\n" +
                "        pdf.ln(th)\n" +
                "    pdf.ln(4 * th)\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 14.0)\n" +
                "    pdf.cell(epw, 0.0, 'With more padding', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "\n" +
                "    \"\"\"\n" +
                "    for row in temp:\n" +
                "        for datum in row.split(','):\n" +
                "            pdf.cell(col_width, 2 * th, str(datum), border=1)\n" +
                "        pdf.ln(2 * th)\n" +
                "\n" +
                "    pdf.add_page()\n" +
                "    pdf.set_font('Times', 'B', 14)\n" +
                "    pdf.cell(epw, 0.0, 'Correlation Heatmap', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.ln(0.5)\n" +
                "    pdf.image('{}'.format(image),x=10,y=50)\n" +
                "\n" +
                "    pdf.add_page()\n" +
                "    pdf.set_font('Times', 'B', 14)\n" +
                "    pdf.cell(epw, 0.0, 'Algorithm ', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.ln(0.5)\n" +
                "\n" +
                "\n" +
                "    max_accuracy,accuracy_name=0,\"\"\n" +
                "    min_time, time_name=1000,\"\"\n" +
                "\n" +
                "    t=time.time()\n" +
                "    accuracy,tnr,tpr = knn('{}'.format(filename))\n" +
                "    t=time.time() -t\n" +
                "\n" +
                "    if max_accuracy<accuracy :\n" +
                "        max_accuracy=accuracy\n" +
                "        accuracy_name='Knn'\n" +
                "    if min_time>t:\n" +
                "        min_time=t\n" +
                "        time_name='Knn'\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 12)\n" +
                "    pdf.cell(200, 10, txt=\"Knn\", ln=1)\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.cell(210, 10, txt=\"Accuracy : {}\".format(accuracy), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TNR : {}\".format(tnr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TPR : {}\".format(tpr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"Time Taken : {}\".format(t), ln=1)\n" +
                "    #print(accuracy,tnr,tpr,t)\n" +
                "\n" +
                "    t = time.time()\n" +
                "    accuracy, tnr, tpr = naive('{}'.format(filename))\n" +
                "    t = time.time() - t\n" +
                "\n" +
                "    if max_accuracy<accuracy :\n" +
                "        max_accuracy=accuracy\n" +
                "        accuracy_name='Naive Bayes'\n" +
                "    if min_time>t:\n" +
                "        min_time=t\n" +
                "        time_name='Naive Bayes'\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 12)\n" +
                "    pdf.cell(200, 10, txt=\"Naive Bayes\", ln=1)\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.cell(210, 10, txt=\"Accuracy : {}\".format(accuracy), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TNR : {}\".format(tnr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TPR : {}\".format(tpr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"Time Taken : {}\".format(t), ln=1)\n" +
                "    #print(accuracy, tnr, tpr, t)\n" +
                "\n" +
                "    t = time.time()\n" +
                "    accuracy, tnr, tpr = SVM('{}'.format(filename))\n" +
                "    t = time.time() - t\n" +
                "\n" +
                "    if max_accuracy<accuracy :\n" +
                "        max_accuracy=accuracy\n" +
                "        accuracy_name='SVM'\n" +
                "    if min_time>t:\n" +
                "        min_time=t\n" +
                "        time_name='SVM'\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 12)\n" +
                "    pdf.cell(200, 10, txt=\"SVM\", ln=1)\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.cell(210, 10, txt=\"Accuracy : {}\".format(accuracy), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TNR : {}\".format(tnr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TPR : {}\".format(tpr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"Time Taken : {}\".format(t), ln=1)\n" +
                "    #print(accuracy, tnr, tpr, t)\n" +
                "\n" +
                "    t = time.time()\n" +
                "    accuracy, tnr, tpr = nn('{}'.format(filename))\n" +
                "    t = time.time() - t\n" +
                "\n" +
                "    if max_accuracy<accuracy :\n" +
                "        max_accuracy=accuracy\n" +
                "        accuracy_name='Neural Network'\n" +
                "    if min_time>t:\n" +
                "        min_time=t\n" +
                "        time_name='Neural Network'\n" +
                "\n" +
                "    pdf.set_font('Times', 'B', 12)\n" +
                "    pdf.cell(200, 10, txt=\"Neural Network\", ln=1)\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.cell(210, 10, txt=\"Accuracy : {}\".format(accuracy), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TNR : {}\".format(tnr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"TPR : {}\".format(tpr), ln=1)\n" +
                "    pdf.cell(210, 10, txt=\"Time Taken : {}\".format(t), ln=1)\n" +
                "    #print(accuracy, tnr, tpr, t)\n" +
                "\n" +
                "    pdf.add_page()\n" +
                "    pdf.set_font('Times', 'B', 14)\n" +
                "    pdf.cell(epw, 0.0, 'Recommendation', align='C')\n" +
                "    pdf.set_font('Times', '', 10.0)\n" +
                "    pdf.ln(0.5)\n" +
                "\n" +
                "    pdf.cell(200, 10, txt=\"Based on Accuracy : {} with accuracy {}\".format(accuracy_name,max_accuracy), ln=1)\n" +
                "    pdf.cell(200, 10, txt=\"Based on Time : {} with time taken {}\".format(time_name,min_time), ln=1)\n" +
                "    #print(max_accuracy,accuracy_name)\n" +
                "    #print(min_time,time_name)\n" +
                "\n" +
                "    pdf.output('data/output.pdf', 'F')\n" +
                "\n" +
                "\n" +
                "def pdfWriter(a,b):\n" +
                "    output = PdfFileWriter()\n" +
                "    pdfOne = PdfFileReader(open(\"{}\".format(a), \"rb\"))\n" +
                "    pdfTwo = PdfFileReader(open(\"{}\".format(b), \"rb\"))\n" +
                "\n" +
                "    output.addPage(pdfOne.getPage(0))\n" +
                "    output.addPage(pdfTwo.getPage(0))\n" +
                "\n" +
                "    outputStream = open(r\"data/output.pdf\", \"wb\")\n" +
                "    output.write(outputStream)\n" +
                "    outputStream.close()\n" +
                "\n" +
                "\n" +
                "def updateDataset(check, data):\n" +
                "    temp=[]\n" +
                "    for i in range(len(check)):\n" +
                "        if check[i] == 1:\n" +
                "            temp.append(data[i])\n" +
                "    return temp\n" +
                "\n" +
                "def updateColumn(check, column):\n" +
                "    temp=[]\n" +
                "    for i in range(len(check)):\n" +
                "        if check[i] == 1:\n" +
                "            temp.append(column[i])\n" +
                "    return temp\n" +
                "\n" +
                "def checkData(data,length):\n" +
                "    temp=[]\n" +
                "    for i in range(length):\n" +
                "        try:\n" +
                "            float(data[i][0])\n" +
                "            temp.append(1)\n" +
                "        except :\n" +
                "            temp.append(0)\n" +
                "    return temp\n" +
                "\n" +
                "def convertData(data):\n" +
                "    temp=[]\n" +
                "    length = len(data[0].split(\",\"))\n" +
                "    for i in range(length):\n" +
                "        test=[]\n" +
                "        for j in range(len(data)):\n" +
                "            test.append(data[j].split(',')[i])\n" +
                "        temp.append(test)\n" +
                "    return temp\n" +
                "\n" +
                "if __name__ == '__main__':\n" +
                "\n" +
                "    filename='data/iris.csv'\n" +
                "    f=open('{}'.format(filename) ,'r')\n" +
                "    data= f.readlines()\n" +
                "    f.close()\n" +
                "    column=data[0].split(\",\")\n" +
                "    column[-1] =column[-1].replace('\\n','')\n" +
                "    data=data[1:]\n" +
                "    data=convertData(data)\n" +
                "    check = checkData(data,len(column))\n" +
                "    #print(check)\n" +
                "    newColumn = updateColumn(check,column)\n" +
                "    newData = updateDataset(check,data)\n" +
                "    #print (len(newData),len(newColumn))\n" +
                "    #print(newColumn)\n" +
                "    df = DataFrame(newData)\n" +
                "    df= df.transpose()\n" +
                "    df= df.apply(pd.to_numeric)\n" +
                "    correlation = df.corr()\n" +
                "    sns.heatmap(correlation,\n" +
                "                xticklabels=newColumn,\n" +
                "                yticklabels=newColumn,)\n" +
                "    #plt.show()\n" +
                "    plt.savefig('data/test.jpg')\n" +
                "    details(data,df,newColumn,'data/test.jpg')\n");

    }

}
