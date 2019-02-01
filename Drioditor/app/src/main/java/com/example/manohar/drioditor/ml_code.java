package com.example.manohar.drioditor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ml_code extends Activity {

    private EditText input_code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_code);

        Bundle values = getIntent().getExtras();
        if (values != null) {
            String url = values.getString("url");
            String dataset_name=values.getString("dataset_name");
            String report_gmail=values.getString("report_gmail");
            Log.i("values",url+""+dataset_name);
        }
        TextView out = (TextView) findViewById(R.id.input_ml_code);
        out.setText("def updateDataset(check, data):\n" +
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
                "    filename='data/"+values.getString("dataset_name")+".csv'\n" +
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

        FloatingActionButton compile = (FloatingActionButton) findViewById(R.id.compile);


        compile.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(getApplicationContext(), "Recommandation" , Toast.LENGTH_LONG).show();

                   Object [] objects;
                   objects = new Object[0];

                   Msg msg = new Msg();
                   msg.doInBackground(objects);
                   Log.i("post" , "it worked");
               }
           }


        );





    }


    private class Msg extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            createMsg(0);
            return null;
        }

        protected void onPostExecute() {

        }



    }
    private void createMsg(final int random){
        input_code=findViewById(R.id.input_ml_code);
        String msg= input_code.getText().toString();
        if (msg.isEmpty() )
            Toast.makeText(ml_code.this, "Nothing to compile and run", Toast.LENGTH_SHORT).show();
        else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {


                    //Get Method
                    try {

                        String msg= input_code.getText().toString();
                        URL url = new URL("http://10.0.2.2:8000/recommendation/");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("code" , msg);
                        jsonParam.put("name" , "iris");
                        jsonParam.put("emailid","mayankmahavar111@gmail.com");
                        jsonParam.put("url" , "https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N");


                        Log.i("JSON", jsonParam.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(jsonParam.toString());

                        os.flush();
                        os.close();

                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG" , conn.getResponseMessage());

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        Log.i("check" , "Worked");
                    }


                }
            });

            thread.start();


        }
    }



}
