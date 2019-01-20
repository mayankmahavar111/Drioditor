package com.example.manohar.drioditor;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class dialog_act extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_act);
        final Button unsup=findViewById(R.id.drive_submit);
        unsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text_url=findViewById(R.id.drive_url);
                String url=text_url.getText().toString();
                EditText text_name=findViewById(R.id.dataset_name);
                String dataset_name=text_name.getText().toString();
                openActivity(url,dataset_name);
            }
        });
    }

    public void openActivity(String url, String dataset_name){
        Intent intent=new Intent(this,ml_code.class);
        intent.putExtra("url",url);
        intent.putExtra("dataset_name",dataset_name);
        startActivity(intent);
    }

}
