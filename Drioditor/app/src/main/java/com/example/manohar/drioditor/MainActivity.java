package com.example.manohar.drioditor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.manohar.drioditor.adapters.codesAdapter;
import com.example.manohar.drioditor.callbacks.CodeEventListener;
import com.example.manohar.drioditor.db.codeDB;
import com.example.manohar.drioditor.db.codeDao;
import com.example.manohar.drioditor.model.codes;
import com.example.manohar.drioditor.utils.CodeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.manohar.drioditor.EditCodeActivity.CODE_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements CodeEventListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<codes> codes;
    private codesAdapter adapter;
    private codeDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.code_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onAddNewCode();
            }
        });

        dao= codeDB.getInstance(this).code_dao();
    }

    private void loadCode(){
        this.codes=new ArrayList<>();

        List<codes> list = dao.getCodes();

        this.codes.addAll(list);

        this.adapter = new codesAdapter(this, codes);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);

    }

    private void onAddNewCode(){
        startActivity(new Intent(this, EditCodeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCode();
    }

    @Override
    public void onCodeClick(com.example.manohar.drioditor.model.codes code) {
        Intent edit=new Intent(this,EditCodeActivity.class);
        edit.putExtra(CODE_EXTRA_Key,code.getId());
        startActivity(edit);

    }

    @Override
    public void onCodeLongClick(final com.example.manohar.drioditor.model.codes code) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.deleteCode(code);
                loadCode();

            }
        }).setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent share=new Intent(Intent.ACTION_SEND);
                String text=code.getCodeText()+"\n Created On :"+CodeUtils.dateFromLong(code.getCodeDate());
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,text); 
                startActivity(share);

            }
        }).create().show();
    }
}
