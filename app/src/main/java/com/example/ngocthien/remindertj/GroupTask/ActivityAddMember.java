package com.example.ngocthien.remindertj.GroupTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ngocthien.remindertj.AddGroupTask.ActivityAddGroupTask;
import com.example.ngocthien.remindertj.AddGroupTask.MainAddGroupTask;
import com.example.ngocthien.remindertj.Login.Model;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.model;
import com.example.ngocthien.remindertj.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class ActivityAddMember extends AppCompatActivity {
    Button btnAddMember;
    EditText user_phoneumber;
    EditText namegroup;
    EditText hostID;
    SharedPreferences sharedPreferences;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = rootNode.getReference();
    DatabaseReference databaseReference1 = rootNode.getReference();
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        loadData();
        setContentView(R.layout.activity_add_member2);
        btnAddMember = findViewById(R.id.btn_add_meember);
        user_phoneumber = findViewById(R.id.ed_memeber_phoneumber);
        namegroup = findViewById(R.id.ed_namegrouptask);
        hostID = findViewById(R.id.ed_hostid);
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChanged();
                Intent intent = new Intent(ActivityAddMember.this, AddMember.class);
                startActivity(intent);
            }
        });
    }
    public void dataChanged() {
        databaseReference = rootNode.getReference().child("UserInfo").child(a).child("GroupTasks").child(namegroup.getText().toString()).child("SingleTask");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot da : snapshot.getChildren()) {
                        model model = new model();
                        model.setTitle(da.child("title").getValue(String.class));
                        model.setDescription(da.child("description").getValue(String.class));
                        model.setDate(da.child("date").getValue(String.class));
                        model.setTime(da.child("time").getValue(String.class));
                        //Toast.makeText(ActivityAddMember.this, "getkey" + da.getKey(), Toast.LENGTH_SHORT).show();
                        databaseReference1.child("UserInfo").child(user_phoneumber.getText().toString()).child("GroupTasks").child(namegroup.getText().toString()).child("SingleTask").child(da.getKey()).setValue(model);
                    }
                } else
                    Toast.makeText(ActivityAddMember.this, "data snapshot is not exists", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void loadData() {
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}