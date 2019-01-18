package com.example.manohar.drioditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import com.github.clans.fab.FloatingActionButton;
//import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manohar.drioditor.db.codeDB;
import com.example.manohar.drioditor.db.codeDao;
import com.example.manohar.drioditor.model.codes;

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
import java.util.Date;
import java.util.Random;

public class EditCodeActivity extends AppCompatActivity {
    private codeDao dao;
    private EditText input_code;
    private codes temp;
    public static final String CODE_EXTRA_Key="code_id";
    public TextView out ;
    public String result;
    Handler handler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
        int theme=sharedPreferences.getInt(MainActivity.THEME_Key,R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_code);



        FloatingActionButton compile = (FloatingActionButton) findViewById(R.id.compile);
        Toolbar toolbar=findViewById(R.id.edit_code_activity_toolbar);
        setSupportActionBar(toolbar);

        out = (TextView) findViewById(R.id.textView2);
        input_code=findViewById(R.id.input_code);
        dao = codeDB.getInstance(this).code_dao();
        final String[] output = {"Unable to get result"};


        if(getIntent().getExtras()!=null){
            int id=getIntent().getExtras().getInt(CODE_EXTRA_Key,0);
            temp=dao.getCodeById(id);
            input_code.setText(temp.getCodeText());
        }
        else
            input_code.setFocusable(true);

        compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("myapp" , "This is working");

                Toast.makeText(getApplicationContext(), "compile and run" , Toast.LENGTH_LONG).show();

                final int min = 1;
                final int max = 100000;
                final int random = new Random().nextInt((max - min) + 1) + min;
                createMsg(random);


                out.setText("");
                try {
                    getMsg( Integer.toString(random) );
                    //handler.postDelayed(r, 100000);
                    try{
                        String res [] = result.split("\n");
                        for(int i=0 ; i<res.length; i++ ) {
                            out.append(res[i]);
                            out.append(System.getProperty("line.separator"));
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }


    private void createMsg(final int random){
        String msg= input_code.getText().toString();
        if (msg.isEmpty() )
            Toast.makeText(EditCodeActivity.this, "Nothing to compile and run", Toast.LENGTH_SHORT).show();
        else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String msg= input_code.getText().toString();
                        URL url = new URL("http://14.139.155.214/snippets/");
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
                    url = new URL("http://14.139.155.214/result/"+id+"/");
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
            onSaveCode();
        return super.onOptionsItemSelected(item);
    }

    public void onSaveCode(){
        String text=input_code.getText().toString();
        if(!text.isEmpty()){
            long date=new Date().getTime();
            if(temp==null){
                temp=new codes(text,date);
                dao.insertCode(temp);
            }
            else {
                temp.setCodeText(text);
                temp.setCodeDate(date);
                dao.updateCode(temp);

            }

            finish();
        }

    }
}
