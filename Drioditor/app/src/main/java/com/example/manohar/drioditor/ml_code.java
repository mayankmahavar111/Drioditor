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
        out.setText("\n" +
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
                "\n" +
                "\n" +
                "    column=data[0].split(\",\")\n" +
                "    column[-1] =column[-1].replace('\\n','')\n" +
                "    data=data[1:]\n" +
                "    data=convertData(data)\n" +
                "    check = checkData(data,len(column))\n" +
                "    newColumn = updateColumn(check,column)\n" +
                "    newData = updateDataset(check,data)\n" +
                "    df = DataFrame(newData)\n" +
                "    df= df.transpose()\n" +
                "    df= df.apply(pd.to_numeric)\n" +
                "    correlation = df.corr()\n" +
                "    sns.heatmap(correlation,xticklabels=newColumn,yticklabels=newColumn)\n" +
                "    plt.savefig('data/test.jpg')\n" +
                "    details(data,df,newColumn,'data/test.jpg')\n");
    }

}
