package com.example.ngocthien.remindertj.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
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
    Button forgotPassword, btn_Signin;
    TextView btn_forgotPassword, registered;
    TextInputEditText phonenumber;
    TextInputEditText password1;
    FirebaseDatabase rootNode;
    String uid;
    DatabaseReference databaseReference;
    String phone_fb;
    String password_fb;
    String passwordUserInput;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES_STARTLOGIN = "MYPREF";
    public static final String PHONENUMBER_STARTlOGIN = "PHoneNUmber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__login);
        phonenumber = findViewById(R.id.editPhone);
        password1 = findViewById(R.id.editPassword);
        registered = findViewById(R.id.tv_signup);
        btn_Signin = findViewById(R.id.signup_btn);
        btn_forgotPassword = findViewById(R.id.tv_forgot_password);

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
                if (phonenumber.getText().toString().isEmpty() && password1.getText().toString().isEmpty()) {
                    phonenumber.setError("Phone number can not be blank");
                    phonenumber.requestFocus();
                    password1.setError("password can not be blank");
                    password1.requestFocus();
                }
                // Toast.makeText(Start_Login.this, "phone number or password can not be empty", Toast.LENGTH_SHORT).show();
                else {
                    sharedPreferences = getSharedPreferences(MyPREFERENCES_STARTLOGIN, Context.MODE_PRIVATE);
                    saveData(p);
                    rootNode = FirebaseDatabase.getInstance();
                    databaseReference = rootNode.getReference().child("UserInfo").child(phonenumber.getText().toString());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                phone_fb = dataSnapshot.child("phone").getValue(String.class);
                                password_fb = dataSnapshot.child("password").getValue(String.class);
                                if (password_fb.equals(password1.getText().toString())) {
                                    Intent intent = new Intent(Start_Login.this, MainAddGroupTask.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Start_Login.this, "Password you input is incorrect", Toast.LENGTH_SHORT).show();
                                    password1.setError("Incorrect password");
                                }
                            } else {
                                Toast.makeText(Start_Login.this, "You phone number is not existed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        phonenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    // Do whatever you want here
                } else {
                    Log.d("focus", "focused");
                }
            }
        });
        password1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    // Do whatever you want here
                } else {
                    Log.d("focus", "focused");
                }
            }
        });
    }
    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
    public void saveData(String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Start_Login.PHONENUMBER_STARTlOGIN, phone);
        editor.apply();
    }
}