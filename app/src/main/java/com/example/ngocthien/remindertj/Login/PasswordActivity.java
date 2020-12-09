package com.example.ngocthien.remindertj.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngocthien.remindertj.Password;
import com.example.ngocthien.remindertj.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.InternalTokenProvider;

public class PasswordActivity extends AppCompatActivity {
    TextInputEditText enterPassword, enterRePassword;
    Button registerButton;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        enterPassword = findViewById(R.id.enterpassword);
        enterRePassword = findViewById(R.id.enterrepassword);
        registerButton = findViewById(R.id.register_btn);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = sharedPreferences.getString(LoginActivity.PHONENUMBER, "");
                String password  = enterPassword.getText().toString();
                String repassword = enterRePassword.getText().toString();
                if(password.isEmpty() || repassword.isEmpty()){
                    enterPassword.setError("Password can not be blank");
                    enterPassword.requestFocus();
                    enterRePassword.setError("Password can not be blank");
                    enterRePassword.requestFocus();
                   // Toast.makeText(PasswordActivity.this, "You need to fill in password and repassword", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(repassword)){
                        rootNode= FirebaseDatabase.getInstance();
                        databaseReference = rootNode.getReference("UserInfo");
                        UserInfo userInfo = new UserInfo(phonenumber, password, repassword );
                        databaseReference.child(phonenumber).setValue(userInfo);
                        Intent intent = new Intent(PasswordActivity.this, Start_Login.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(PasswordActivity.this, "password and confirm password not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}