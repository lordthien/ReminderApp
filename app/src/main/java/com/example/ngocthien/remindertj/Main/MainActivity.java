package com.example.ngocthien.remindertj.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.ngocthien.remindertj.About.BlankFragment;
import com.example.ngocthien.remindertj.Chatbot.ChatbotActivity;
import com.example.ngocthien.remindertj.Login.LoginActivity;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.model;
import com.example.ngocthien.remindertj.myadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ngocthien.remindertj.AboutFragment;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultActivity;
import com.example.ngocthien.remindertj.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.ngocthien.remindertj.Main.MainFragment.recview;

public class MainActivity extends AppDefaultActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    myadapter adapter;
    SharedPreferences sharedPreferences;
    TextView nav_phonenumber, nav_about_phonenumber;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView = findViewById(R.id.nav_view);
        //Get Access For Set Value In Navigation Header
        View hView = (View) navigationView.getHeaderView(0);
        nav_phonenumber  = (TextView) hView.findViewById(R.id.phoneNumber);
        loadData();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }
    public void loadData(){
        String a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
        nav_phonenumber.setText(a);
    }
    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_main;
    }
    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return MainFragment.newInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.drawer_menu, menu);
        MenuItem item  =menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
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
    public void processserch(String searchItem){
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AddTaskAction").orderByChild("title").startAt(searchItem).endAt(searchItem + "\uf8ff"), model.class)
                        .build();
        adapter = new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}




