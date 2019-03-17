package com.example.manohar.drioditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class pdf_viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        final Button unsup=findViewById(R.id.fetch_report);
        unsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text_name=findViewById(R.id.dataset_name);
                String dataset_name=text_name.getText().toString();
                EditText gmail_report=findViewById(R.id.gmail_report);
                String report_gmail=gmail_report.getText().toString();
            }
        });
    }
}
