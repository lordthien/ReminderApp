package com.example.ngocthien.remindertj.AddGroupTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static android.content.Context.MODE_PRIVATE;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngocthien.remindertj.About.BlankFragment;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultActivity;
import com.example.ngocthien.remindertj.Chatbot.ChatbotActivity;

import com.example.ngocthien.remindertj.GroupTask.AddMember;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.Main.MainFragment;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.adapter_addgrouptask;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.ngocthien.remindertj.AddGroupTask.MainFragmentAddGroupTask.recview_add_group_task;

public class MainAddGroupTask extends AppDefaultActivity implements NavigationView.OnNavigationItemSelectedListener  {

    String a;
    NavigationView navigationView;
    TextView nav_phonenumber;
    FloatingActionButton floatingActionButton;
    adapter_addgrouptask adapter;
    SharedPreferences sharedPreferences, getkey, getphonenumberfornavi;
    DrawerLayout drawer;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        navigationView = findViewById(R.id.nav_view);
        //Get Access For Set Value In Navigation Header
        View hView = (View) navigationView.getHeaderView(0);
        nav_phonenumber  = (TextView) hView.findViewById(R.id.phoneNumber);
        sharedPreferences = getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        getkey = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        loadData();
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.main_add_group;
    }

    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return MainFragmentAddGroupTask.newInstance();
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

    public void processserch(String searchItem) {
        FirebaseRecyclerOptions<ModelAddGroupTask> options =
                new FirebaseRecyclerOptions.Builder<ModelAddGroupTask>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks").orderByChild("name").startAt(searchItem).endAt(searchItem + "\uf8ff"), ModelAddGroupTask.class)
                        .build();
        adapter = new adapter_addgrouptask(options);
        recview_add_group_task.setAdapter(adapter);
        adapter.startListening();

    }

    public void loadData() {
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
        nav_phonenumber.setText(a);
    }
    public void loadData1(){
        key = getkey.getString(MainActivity.MyPREFERENCES,"");
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatbotActivity()).commit();break;
            case R.id.addmember:
                Intent intent = new Intent(MainAddGroupTask.this, AddMember.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}