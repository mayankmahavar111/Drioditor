package com.example.manohar.drioditor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manohar.drioditor.db.codeDB;
import com.example.manohar.drioditor.db.codeDao;
import com.example.manohar.drioditor.model.codes;

import net.cryptobrewery.syntaxview.SyntaxView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class EditCodeActivity extends AppCompatActivity {
    private codeDao dao;
    private SyntaxView input_code;
    private codes temp;
    public static final String CODE_EXTRA_Key="code_id";
    public TextView out ;
    public String result;
    public int random;
    //public EditText editText;
    Handler handler ;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    static String fileName="";
    private String tempFileName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
        int theme=sharedPreferences.getInt(MainActivity.THEME_Key,R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_code);

        FloatingActionButton speech = (FloatingActionButton) findViewById(R.id.speech);

        speech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


        FloatingActionButton compile = (FloatingActionButton) findViewById(R.id.compile);
        Toolbar toolbar=findViewById(R.id.edit_code_activity_toolbar);
        setSupportActionBar(toolbar);

        out = (TextView) findViewById(R.id.textView2);
        //editText = (EditText)findViewById(R.id.input_code);
        input_code=findViewById(R.id.input_code);
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

        dao = codeDB.getInstance(this).code_dao();
        final String[] output = {"Unable to get result"};


        if(getIntent().getExtras()!=null){
            int id=getIntent().getExtras().getInt(CODE_EXTRA_Key,0);
            temp=dao.getCodeById(id);
            input_code.getCode().setText(temp.getCodeText());
        }
        else
            input_code.setFocusable(true);

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



    }



    private void runCode(){
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

    private class Msg extends AsyncTask{


        @Override
        protected Object doInBackground(Object[] objects) {
            createMsg(random);
            return null;
        }

        protected void onPostExecute() {

        }

    }

    private void getRes(final int random){
        URL url;
        StringBuffer response = new StringBuffer();
        String id = Integer.toString(random);
        try {
            url = new URL("http://10.53.78.42:8000/result/"+id+"/");
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

    private int getFlag(){
        int flag =1;
        while (flag!=1) {
            URL url;
            StringBuffer response = new StringBuffer();
            try {
                url = new URL("http://10.53.78.42:8000/flag/");
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

                Log.i("check", "worked till 1");
                // handle the response
                int status = conn.getResponseCode();
                Log.i("check", "worked till 2");
                if (status != 200) {
                    throw new IOException("get failed with error code " + status);
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    Log.i("check", "worked till 3");
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
                flag = Integer.parseInt(responseJSON);
            }
        }
        return flag;
    }

    private void createMsg(final int random){
        input_code.checkMyCode();
        String msg= input_code.getCode().getText().toString();
        if (msg.isEmpty() )
            Toast.makeText(EditCodeActivity.this, "Nothing to compile and run", Toast.LENGTH_SHORT).show();
        else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {


                    //Get Method
                    try {

                        String msg= input_code.getCode().getText().toString();
                        URL url = new URL("http://10.53.78.42:8000/snippets/");
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





    private void getMsg(final String id ) throws IOException {

        final String[] output = {"Unable to get result"};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                StringBuffer response = new StringBuffer();
                try {
                    url = new URL("http://10.53.78.42:8000/result/"+id+"/");
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



        });
                thread.start();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_code_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.save_code)
            openFileNameDialog();
        return super.onOptionsItemSelected(item);
    }

    public void onSaveCode(String fileName){
        String text=input_code.getCode().getText().toString();
        if(!text.isEmpty()){
            long date=new Date().getTime();
            if(temp==null){
                temp=new codes(text,date,fileName);
                dao.insertCode(temp);
            }
            else {
                temp.setCodeText(text);
                temp.setCodeDate(date);
                temp.setCodeName(fileName);
                dao.updateCode(temp);
            }
        }

    }

    public String openFileNameDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter the File name");


            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            if (temp!=null)
            input.setText(temp.getCodeName());
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tempFileName = input.getText().toString();
                    onSaveCode(tempFileName);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        Log.i("test",""+tempFileName);
        return tempFileName;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String abc= result.get(0).toString();

                    Log.i("voice" ,abc);

                    String[] test = abc.split(" ",0);
                    Log.i("voice" ,test.toString());

                    String msg= input_code.getCode().getText().toString();
                    int cursorPosition = input_code.getCode().getSelectionStart();
                    String out="";
                    String temp;
                    switch (test[0].toLowerCase()){
                        case "up":
                                input_code.getCode().setSelection(0);
                            break;

                        case "down" :
                            input_code.getCode().setSelection(input_code.getCode().getText().length());
                            break;
                        case "move":
                            input_code.getCode().setSelection(Integer.parseInt(test[1]));
                            break;
                        case "replace":

                            out =msg.replace(test[1],test[2]);
                            Log.i("voice 1",out);
                            input_code.getCode().setText(out);
                            break;

                        case "remove":
                            out =msg.replace(test[1],"");
                            Log.i("voice 1",out);
                            input_code.getCode().setText(out);
                            break;

                        case "enter":
                            temp="";
                            for (int i=1;i<test.length;i++)
                                temp= temp+test[i]+" ";
                            for(int i=0;i<cursorPosition;i++)
                                out+=msg.charAt(i);
                            out +=temp;

                            for(int i=cursorPosition;i<msg.length();i++)
                                out+=msg.charAt(i);
                            Log.i("voice 1",out);
                            input_code.getCode().setText(out);
                            input_code.getCode().setSelection(cursorPosition+temp.length());
                            break;

                        case "press":
                            if (test[1].equalsIgnoreCase("enter")){
                                temp="\n";
                                for(int i=0;i<cursorPosition;i++)
                                    out+=msg.charAt(i);
                                out +=temp;

                                for(int i=cursorPosition;i<msg.length();i++)
                                    out+=msg.charAt(i);
                                Log.i("voice 1",out);
                                input_code.getCode().setText(out);
                                input_code.getCode().setSelection(cursorPosition+temp.length());
                        }

                        case "save":
                                openFileNameDialog();


                                break;

                        case "run":
                            runCode();
                            break;
                        default:
                            temp="";
                            for (int i=0;i<test.length;i++)
                                temp= temp+test[i]+" ";
                            for(int i=0;i<cursorPosition;i++)
                                out+=msg.charAt(i);
                            out +=temp;

                            for(int i=cursorPosition;i<msg.length();i++)
                                out+=msg.charAt(i);
                            Log.i("voice 1",out);
                            input_code.getCode().setText(out);
                            input_code.getCode().setSelection(cursorPosition+temp.length());
                    }
                }
                break;
            }

        }
    }
}
