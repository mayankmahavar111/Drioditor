package com.example.manohar.drioditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.example.manohar.drioditor.db.codeDB;
import com.example.manohar.drioditor.db.codeDao;
import com.example.manohar.drioditor.model.codes;

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
