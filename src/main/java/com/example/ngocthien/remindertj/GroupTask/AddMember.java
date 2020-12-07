package com.example.ngocthien.remindertj.GroupTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ngocthien.remindertj.AddGroupTask.ActivityAddGroupTask;
import com.example.ngocthien.remindertj.AddGroupTask.MainAddGroupTask;
import com.example.ngocthien.remindertj.AddGroupTask.ModelAddGroupTask;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.adapter_addgrouptask;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AddMember extends AppCompatActivity {
    LottieAnimationView floatingButton;
    RecyclerView recyclerView_addmember;
    adapter_add_memeber adapter;
    SharedPreferences sharedPreferences;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        sharedPreferences = getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        loadData();
        recyclerView_addmember = findViewById(R.id.recview_addmemeber);
        floatingButton = findViewById(R.id.floatingActionButton);
        recyclerView_addmember.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_addmember.setHasFixedSize(true);
        FirebaseRecyclerOptions<ModelAddMember> options =
                new FirebaseRecyclerOptions.Builder<ModelAddMember>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("Mission"), ModelAddMember.class)
                        .build();
        adapter = new adapter_add_memeber(options);
        recyclerView_addmember.setAdapter(adapter);
        floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMember.this, ActivityAddMember.class);
                startActivity(intent);
            }
        });
    }
    public void loadData(){
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.drawer_menu, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processserch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processserch(s);
//                return false;
//            }
//        });
//
//        return true;
//    }
//    public void loadData1() {
//        key = sharedPreferences.getString(MainActivity.GETKEY_ITEM_NAME, "");
//    }

//    public void processserch(String searchItem) {
//        FirebaseRecyclerOptions<ModelAddMember> options =
//                new FirebaseRecyclerOptions.Builder<ModelAddMember>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks").orderByChild("name").startAt(searchItem).endAt(searchItem + "\uf8ff"), ModelAddMember.class)
//                        .build();
//
//        adapter = new adapter_add_memeber(options);
//        adapter.startListening();
//        recyclerView_addmember.setAdapter(adapter);
//    }
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

}