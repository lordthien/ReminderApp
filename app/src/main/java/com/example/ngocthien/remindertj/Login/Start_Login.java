package com.example.ngocthien.remindertj.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngocthien.remindertj.AddGroupTask.MainAddGroupTask;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Start_Login extends AppCompatActivity {
    Button forgotPassword, btn_Signin, registered, btn_forgotPassword;
    TextInputEditText phonenumber;
    TextInputEditText password1;
    FirebaseDatabase rootNode;
    String uid;
    DatabaseReference databaseReference;
    String phone_fb;
    String password_fb;
    String repassword_fb;
    String passwordUserInput;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES_STARTLOGIN = "MYPREF";
    public static final String FB_PHONENUMBER ="phonenumber";
    public static final String PHONENUMBER_STARTlOGIN = "PHoneNUmber";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__login);
         phonenumber = findViewById(R.id.editPhone);
         password1 = findViewById(R.id.editPassword);
         registered = findViewById(R.id.register_btn);
         btn_Signin = findViewById(R.id.signup_btn);
         btn_forgotPassword = findViewById(R.id.fotgotpassword_btn);

        btn_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Login.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
         btn_Signin.setOnClickListener(v -> {
         });
         registered.setOnClickListener(v -> {
             Intent intent = new Intent(Start_Login.this, LoginActivity.class);
             startActivity(intent);
         });
         btn_Signin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String p = phonenumber.getText().toString();
                 if(phonenumber.getText().toString().isEmpty() || password1.getText().toString().isEmpty())
                     Toast.makeText(Start_Login.this, "phone number or password can not be empty", Toast.LENGTH_SHORT).show();
                 else{
                     sharedPreferences = getSharedPreferences(MyPREFERENCES_STARTLOGIN, Context.MODE_PRIVATE);
                     Toast.makeText(Start_Login.this, "" + phonenumber.getText().toString(), Toast.LENGTH_SHORT).show();
                     saveData(p);
                         rootNode= FirebaseDatabase.getInstance();
                         databaseReference = rootNode.getReference().child("UserInfo").child(phonenumber.getText().toString());

                         databaseReference.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if(dataSnapshot.exists()){
                                     phone_fb = dataSnapshot.child("phone").getValue(String.class);
                                    // Toast.makeText(Start_Login.this, "Hello ccc" + phone_fb, Toast.LENGTH_SHORT).show();
                                     password_fb = dataSnapshot.child("password").getValue(String.class);
                                     String phoneNumberUserInput = phonenumber.getText().toString();
                                     // Toast.makeText(Start_Login.this, "password_fb" + password_fb, Toast.LENGTH_SHORT).show();
                                     // Toast.makeText(Start_Login.this, "password user input " + password1.getText().toString(), Toast.LENGTH_SHORT).show();
//                             if(phoneNumberUserInput.equals(phone_fb))
//                             {
                                     if(password_fb.equals(password1.getText().toString())){
                                         Intent intent = new Intent(Start_Login.this, MainAddGroupTask.class);
                                         startActivity(intent);
                                     }
                                     else
                                         Toast.makeText(Start_Login.this, "Password you input is incorrect", Toast.LENGTH_SHORT).show();
//                             }
//                             else
//                                 Toast.makeText(Start_Login.this, "Phone number is not existed", Toast.LENGTH_SHORT).show();
                                 }
                                 else
                                     Toast.makeText(Start_Login.this, "DataSnapshot is not existed", Toast.LENGTH_SHORT).show();
                             }
                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                     }
                 }
         });
    }
    public void saveData(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Start_Login.PHONENUMBER_STARTlOGIN, phone);
        editor.commit();
    }
}