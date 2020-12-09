package com.example.ngocthien.remindertj.AddGroupTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ngocthien.remindertj.Login.LoginActivity;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityAddGroupTask extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText name;
    Button addButton;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_task2);
        sharedPreferences = getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        loadData();
        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference("").child("UserInfo").child(a).child("GroupTasks");
        name = findViewById(R.id.ednamegrouptask);
        addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(v -> {
            Map<String,Object> map=new HashMap<>();
            map.put("name", name.getText().toString());
            databaseReference.child(name.getText().toString()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = new Intent(ActivityAddGroupTask.this, MainAddGroupTask.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show());
        });
    }
    public void loadData(){
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
}