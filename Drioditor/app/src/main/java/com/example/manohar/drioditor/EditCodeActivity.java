package com.example.manohar.drioditor;

import android.content.Context;
import android.content.SharedPreferences;
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

public class EditCodeActivity extends AppCompatActivity {
    private codeDao dao;
    private EditText input_code;
    private codes temp;
    public static final String CODE_EXTRA_Key="code_id";

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
        input_code=findViewById(R.id.input_code);
        dao = codeDB.getInstance(this).code_dao();

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

                //createMsg();

                try {
                    getMsg("2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void createMsg(){
        String msg= input_code.getText().toString();
        if (msg.isEmpty() )
            Toast.makeText(EditCodeActivity.this, "Nothing to compile and run", Toast.LENGTH_SHORT).show();
        else{

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String msg= input_code.getText().toString();
                        URL url = new URL("http://10.52.34.12:8000/snippets/");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("code" , msg);
                        jsonParam.put("title" , "Test");


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



    private void getMsg(String id) throws IOException {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        try {

            String site_url_json = "http://10.0.2.2:8000/result/"+id+"/";
            URL url = new URL(site_url_json);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            Log.i("result" , resultJson);

        } catch (Exception e) {
            e.printStackTrace();
        }



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
