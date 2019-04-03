package com.example.manohar.drioditor;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.cryptobrewery.syntaxview.SyntaxView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;

public class template extends AppCompatActivity {
    private SyntaxView input_code;
    public TextView out ;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public int random;
    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        int ml_algorithm=0;
        String url, dataset_name, report_gmail;

        FloatingActionButton compile = (FloatingActionButton) findViewById(R.id.compile);
        Toolbar toolbar=findViewById(R.id.edit_code_activity_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton speech = (FloatingActionButton) findViewById(R.id.speech);

        speech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


        out = (TextView) findViewById(R.id.textView2);
        //editText = (EditText)findViewById(R.id.input_code);
        input_code=findViewById(R.id.input_ml_code);
        //this will set the color of Code Text background
        input_code.setBgColor("#2b2b2b");
        //this will set the color of strings between " "
        input_code.setPrintStatmentsColor("#6a8759");
        //this will set the default code text color other than programming keywords!
        input_code.setCodeTextColor("#ffffff");
        //this will set programming keywords color like String,int,for,etc...
        input_code.setKeywordsColor("#cc7832");
        //this will set the numbers color in code | ex: return 0; 0 will be colored
        input_code.setNumbersColor("#4a85a3");
        //this will set the line number view background color at left
        input_code.setRowNumbersBgColor("#2b2b2b");
        //this will set the color of numbers in the line number view at left
        input_code.setRowNumbersColor("#cc7832");
        //this will set color of Annotations like super,@Nullable,etc ....
        input_code.setAnnotationsColor("#1932F3");
        //this will set special characters color like ;
        input_code.setSpecialCharsColor("#cc7832");

        compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("myapp" , "This is working");

                Toast.makeText(getApplicationContext(), "compile and run" , Toast.LENGTH_LONG).show();

                final int min = 1;
                final int max = 1000;
                random = new Random().nextInt((max - min) + 1) + min;

                Object [] objects;
                objects = new Object[0];

                Msg msg = new Msg();
                msg.doInBackground(objects);

                /*
                msgPart2 msg2 = new msgPart2();
                msg2.doInBackground(objects);*/

                /*
                createMsg(random);



                try {
                    getMsg( Integer.toString(random) );
                    */
                //handler.postDelayed(r, 100000);
                showText();

            }
        });

        Bundle values = getIntent().getExtras();
        if (values != null) {
            ml_algorithm=values.getInt("ml_algorithm");
            url = values.getString("url");
            dataset_name=values.getString("dataset_name");
            report_gmail=values.getString("report_gmail");
            //Log.i("values",""+ml_algorithm+""+url+""+dataset_name+""+report_gmail);
        }

        SyntaxView out=findViewById(R.id.input_ml_code);

        switch (ml_algorithm){
            case 1:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def naive(dataset):\n" +
                        "\ttemp=  downloadDataset("+"'https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N'"+",'"+values.getString("dataset_name")+"')\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=naive_bayes.GaussianNB()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/"+values.getString("dataset_name")+".csv' \n"+
                        "\taccuracy, tnr, tpr = naive('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 2:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def knn(dataset):\n" +
                        "\ttemp=  downloadDataset("+"'https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N'"+",'"+values.getString("dataset_name")+"')\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=neighbors.KNeighborsClassifier()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\t#print(cm)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/"+values.getString("dataset_name")+".csv' \n"+
                        "\taccuracy, tnr, tpr = knn('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 3:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def SVM(dataset):\n" +
                        "\ttemp=  downloadDataset("+"'https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N'"+",'"+values.getString("dataset_name")+"')\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=svm.LinearSVC()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n"+
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/"+values.getString("dataset_name")+".csv' \n"+
                        "\taccuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 4:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def nn(dataset):\n" +
                        "\ttemp=  downloadDataset("+"'https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N'"+",'"+values.getString("dataset_name")+"')\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=neural_network.MLPClassifier()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/"+values.getString("dataset_name")+".csv' \n"+
                        "\taccuracy, tnr, tpr = nn('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 5:
                 out.getCode().setText("Under Construction");
                break;
            case 6:
                out.getCode().setText("Under Construction");
                break;
            case 7:
                out.getCode().setText("Under Construction");
                break;
            case 8:
                out.getCode().setText("Under Construction");
                break;
            default:
                out.getCode().setText("Please select one of the given ML Algorithm");
                break;
        }

    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://play.google.com/store/apps/details?id==com.google.android.voicesearch"));
            startActivity(browserIntent);
            /*Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();*/
            Log.i("speech",""+a);
        }
    }
    private class Msg extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            createMsg(random);
            return null;
        }

        protected void onPostExecute() {

        }

    }
    private void createMsg(final int random){
        input_code.checkMyCode();
        String msg="import numpy as np\n"+"from sklearn import neighbors,svm,naive_bayes,neural_network\n"+"from sklearn.metrics import confusion_matrix\n";
        msg+=input_code.getCode().getText().toString();
        if (msg.isEmpty() )
            Toast.makeText(template.this, "Nothing to compile and run", Toast.LENGTH_SHORT).show();
        else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {


                    //Get Method
                    try {


                        String msg="import numpy as np\n"+"from sklearn import neighbors,svm,naive_bayes,neural_network\n"+"from sklearn.metrics import confusion_matrix\n";

                        msg+= input_code.getCode().getText().toString();

                        //msg=msg.replaceAll("temp=  downloadDataset(url,name)","temp=  downloadDataset('"+"https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N"+"','"+values.getString("dataset_name")+"')");
                        //msg=msg.replaceAll("data/iris.csv","data/'"+values.getString("dataset_name")+".csv'");

                        URL url = new URL("http://10.0.2.2:8000/snippets/");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("code" , msg);
                        jsonParam.put("title" , Integer.toString(random));


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

                    //Post Method

                    getRes(random);
                    showText();

                }
            });

            thread.start();


        }
    }

    private void showText( ){
        out.setText("");
        try{

            out.setMovementMethod(new ScrollingMovementMethod());

            result=result.replaceAll("^\"|\"$", "");
            String temp="";
            String res[] = result.split("@#");
            for(int i=0 ; i<res.length; i++ ) {
                Log.i("result",res[i]+'\n');
                temp = temp + res[i]+" <br />"+"";
            }
            Log.i("result",temp);
            Log.i("result", String.valueOf(Html.fromHtml(temp)));
            out.setText(Html.fromHtml(temp));
            /*
            int i=0;
            while(i<result.length()){
                temp="";
                while (result.charAt(i)!=92 && result.charAt(i+1)!='n' ){
                    temp=temp+result.charAt(i);

                    Log.i("result",temp);
                    i=i+1;

                }
                i=i+2;

                out.append(temp+'\n');
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getRes(final int random){
        URL url;
        StringBuffer response = new StringBuffer();
        String id = Integer.toString(random);
        try {
            url = new URL("http://10.0.2.2:8000/result/"+id+"/");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url");
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            //conn.setRequestProperty("Accept","application/form");

            Log.i("check" , "worked till 1");
            // handle the response
            int status = conn.getResponseCode();
            Log.i("check" , "worked till 2");
            if (status != 200) {
                throw new IOException("get failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Log.i("check" , "worked till 3");
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            //Here is your json in string format
            String responseJSON = response.toString();
            //Log.i("check" , responseJSON);
            result = responseJSON;

            Log.i("check" , result);
        }
    }

}
