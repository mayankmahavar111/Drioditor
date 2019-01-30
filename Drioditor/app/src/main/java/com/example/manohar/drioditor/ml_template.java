package com.example.manohar.drioditor;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ml_template extends Activity implements OnItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_template);

        // Spinner element
        Spinner spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select ML Template");
        categories.add("Naive Bayes");
        categories.add("K-Nearest Neighbors (KNN)");
        categories.add("Support Vector Machine (SVM)");
        categories.add("Multi-layer Perceptron (MLP)");
        categories.add("Decision Tree");
        categories.add("Linear Regression");
        categories.add("K-means clustering");
        categories.add("Principal component analysis (PCA)");


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        final Button unsup=findViewById(R.id.drive_submit);
        unsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdapterView av=findViewById(R.id.spinner);
                int ml_algorithm=av.getSelectedItemPosition();
                EditText text_url=findViewById(R.id.drive_url);
                String url=text_url.getText().toString();
                EditText text_name=findViewById(R.id.dataset_name);
                String dataset_name=text_name.getText().toString();
                EditText gmail_report=findViewById(R.id.gmail_report);
                String report_gmail=gmail_report.getText().toString();
                openActivity(ml_algorithm,url,dataset_name,report_gmail);
            }
        });
    }

    private void openActivity(int ml_algorithm,String url,String dataset_name,String report_gmail) {
        Intent intent=new Intent(this,template.class);
        intent.putExtra("ml_algorithm",ml_algorithm);
        intent.putExtra("url",url);
        intent.putExtra("dataset_name",dataset_name);
        intent.putExtra("report_gmail",report_gmail);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: "+Integer.toString(position) + item, Toast.LENGTH_LONG).show();
        if(position==0)
            Toast.makeText(parent.getContext(), "Please Select the ML Algorithm", Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

