package com.example.ngocthien.remindertj.AddGroupTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ngocthien.remindertj.About.BlankFragment;
import com.example.ngocthien.remindertj.Chatbot.ChatbotActivity;

import com.example.ngocthien.remindertj.GroupTask.AddMember;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.Main.MainFragment;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.adapter_addgrouptask;
import com.example.ngocthien.remindertj.model;
import com.example.ngocthien.remindertj.myadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ngocthien.remindertj.Main.MainFragment.recview;

public class MainAddGroupTask extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recview1;
    String a;
    NavigationView navigationView;

    FloatingActionButton floatingActionButton;
    adapter_addgrouptask adapter;
    SharedPreferences sharedPreferences, getkey;
    DrawerLayout drawer;
    String key;
//    private AppBarConfiguration mAppBarConfiguration;

    //    public static final String BUNDLE ="bundle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_task);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        getkey = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        loadData();
        loadData1();
        recview1 = (RecyclerView) findViewById(R.id.recview1);
        recview1.setLayoutManager(new LinearLayoutManager(this));
        recview1.setHasFixedSize(true);
        FirebaseRecyclerOptions<ModelAddGroupTask> options =
                new FirebaseRecyclerOptions.Builder<ModelAddGroupTask>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks"), ModelAddGroupTask.class)
                        .build();
        adapter = new adapter_addgrouptask(options);
        recview1.setAdapter(adapter);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAddGroupTask.this, ActivityAddGroupTask.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processserch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processserch(s);
                return false;
            }
        });

        return true;
    }

    public void loadData1() {
        key = sharedPreferences.getString(MainActivity.GETKEY_ITEM_NAME, "");
    }

    public void processserch(String searchItem) {
        FirebaseRecyclerOptions<ModelAddGroupTask> options =
                new FirebaseRecyclerOptions.Builder<ModelAddGroupTask>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks").orderByChild("name").startAt(searchItem).endAt(searchItem + "\uf8ff"), ModelAddGroupTask.class)
                        .build();

        adapter = new adapter_addgrouptask(options);
        adapter.startListening();
        recview1.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    public void loadData() {
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAbout:
                BlankFragment blankFragment = new BlankFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, blankFragment).commit();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                break;
            case  R.id.chatbot:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatbotActivity()).commit();
            case R.id.addmember:
                Intent intent = new Intent(MainAddGroupTask.this, AddMember.class);
                startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}