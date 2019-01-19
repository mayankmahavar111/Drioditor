package com.example.manohar.drioditor;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

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

    }

}
