package com.example.manohar.drioditor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.manohar.drioditor.adapters.codesAdapter;
import com.example.manohar.drioditor.callbacks.CodeEventListener;
import com.example.manohar.drioditor.callbacks.MainActionModeCallback;
import com.example.manohar.drioditor.db.codeDB;
import com.example.manohar.drioditor.db.codeDao;
import com.example.manohar.drioditor.model.codes;
import com.example.manohar.drioditor.utils.CodeUtils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.manohar.drioditor.EditCodeActivity.CODE_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements CodeEventListener, Drawer.OnDrawerItemClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<codes> codes;
    private codesAdapter adapter;
    private codeDao dao;
    private MainActionModeCallback actionModeCallback;
    private int checkedCount=0;
    private FloatingActionButton fab;
    private SharedPreferences settings;
    public static final String THEME_Key="app_theme";
    public static final String APP_PREFERENCES="code_settings";
    public int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings=getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        theme=settings.getInt(THEME_Key,R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupNavigation(savedInstanceState,toolbar);


        recyclerView=findViewById(R.id.code_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onAddNewCode();
            }
        });

        dao= codeDB.getInstance(this).code_dao();
    }

    private void setupNavigation(Bundle savedInstanceState,Toolbar toolbar){

        List<IDrawerItem> iDrawerItems=new ArrayList<>();

        iDrawerItems.add(new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Codes").withIcon(R.drawable.ic_code_black_24dp));

        List<IDrawerItem> stickyItems=new ArrayList<>();
        SwitchDrawerItem switchDrawerItem=new SwitchDrawerItem()
                .withName("DarkMode")
                .withIcon(R.drawable.ic_invert_colors_black_24dp)
                .withChecked(theme==R.style.AppTheme_Dark)
                .withOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            settings.edit().putInt(THEME_Key,R.style.AppTheme_Dark).apply();
                        }
                        else {
                            settings.edit().putInt(THEME_Key,R.style.AppTheme).apply();
                        }

                        MainActivity.this.recreate();

                    }
                });
        stickyItems.add(new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings_black_24dp));
        stickyItems.add(switchDrawerItem);

        AccountHeader header=new AccountHeaderBuilder().withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withEmail("Droiditor_feedback@gmail.com")
                        .withName("User Name")
                        .withIcon(R.mipmap.ic_launcher))
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)
                .withDrawerItems(iDrawerItems)
                .withTranslucentNavigationBar(true)
                .withStickyDrawerItems(stickyItems)
                .withAccountHeader(header)
                .withOnDrawerItemClickListener(this)
                .build();
    }

    private void loadCode(){
        this.codes=new ArrayList<>();

        List<codes> list = dao.getCodes();

        this.codes.addAll(list);

        this.adapter = new codesAdapter(this, this.codes);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
        showEmptyView();

        swipeToDeleteHelper.attachToRecyclerView(recyclerView);

    }

    private void showEmptyView(){
        if(codes.size()==0){
            this.recyclerView.setVisibility(View.GONE);
            findViewById(R.id.empty_codes_view).setVisibility(View.VISIBLE);
        }
        else {
            this.recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.empty_codes_view).setVisibility(View.GONE);
        }
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
    public void onCodeLongClick(codes code) {
        code.setChecked(true);
        checkedCount=1;
        adapter.setMultiCheckMode(true);

        adapter.setListener(new CodeEventListener() {
            @Override
            public void onCodeClick(com.example.manohar.drioditor.model.codes code) {
                code.setChecked(!code.isChecked());
                if(code.isChecked())
                    checkedCount++;
                else
                    checkedCount--;

                if(checkedCount>1){
                    actionModeCallback.changeShareItemVisible(false);
                }
                else
                    actionModeCallback.changeShareItemVisible(true);

                if(checkedCount==0){
                    actionModeCallback.getAction().finish();
                }

                actionModeCallback.setCount(checkedCount+"/"+codes.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCodeLongClick(com.example.manohar.drioditor.model.codes code) {

            }
        });

        actionModeCallback = new MainActionModeCallback() {
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(item.getItemId()==R.id.action_delete_codes)
                    onDeleteMultiCodes();
                else if(item.getItemId()==R.id.action_share_codes)
                    onShareCode();

                mode.finish();
                return false;
            }
        };

        startActionMode(actionModeCallback);
        fab.setVisibility(View.GONE);
        actionModeCallback.setCount(checkedCount+"/"+codes.size());


    }

    private void onDeleteMultiCodes(){
        List<codes> checkedCodes=adapter.getCheckedCodes();

        if(checkedCodes.size()!=0){
            for (codes c : checkedCodes) {
                dao.deleteCode(c);

            }
            loadCode();
            Toast.makeText(this, checkedCodes.size()+" Code(s) Deleted Successfully ! ", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "No Code selected", Toast.LENGTH_SHORT).show();

    }

    private void onShareCode(){

        codes code=adapter.getCheckedCodes().get(0);
        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String codetext=code.getCodeText()+"\n\n Created on "+CodeUtils.dateFromLong(code.getCodeDate());
        share.putExtra(Intent.EXTRA_TEXT,codetext);
        startActivity(share);

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        adapter.setMultiCheckMode(false);
        adapter.setListener(this);
        fab.setVisibility(View.VISIBLE);
    }
    

    private ItemTouchHelper swipeToDeleteHelper=new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if(codes!=null){
                codes swipedCode=codes.get(viewHolder.getAdapterPosition());
                if(swipedCode!=null){
                    swipeToDelete(swipedCode,viewHolder);
                }

            }

        }
    });

    private void swipeToDelete(final codes swipedCode, final RecyclerView.ViewHolder viewHolder){
        new AlertDialog.Builder(MainActivity.this)
        .setMessage("Delete Code?")
        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dao.deleteCode(swipedCode);
                codes.remove(swipedCode);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                showEmptyView();

            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

            }
        })
        .setCancelable(false)
        .create().show();

    }

    public boolean onItemClick(View view,int position,IDrawerItem drawerItem){

        Toast.makeText(this,""+position, Toast.LENGTH_SHORT).show();
        return false;
    }
}
