package com.example.ngocthien.remindertj.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG1 = "USER ID:";
    Button btnGetVC, btnSI;
    String phone;
    FirebaseAuth mAuth;
    String codeSent;
    TextInputEditText editTextPhone, editTextCode;
    TextInputLayout txtInput1, txtInput2;
    public static final String MyPREFERENCES = "MYPREF";
    public static final String FB_PHONENUMBER ="phonenumber";
    public static final String PHONENUMBER = "PHoneNUmber";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnGetVC = findViewById(R.id.btnGetVerificationCode);
        btnSI = findViewById(R.id.btnSignUp);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCode = findViewById(R.id.editTextCodeReceived);
        txtInput1  = findViewById(R.id.register_phonenumber);
        txtInput2  = findViewById(R.id.textInput2);

            btnGetVC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextPhone.getText().toString().isEmpty()){
                        editTextPhone.setError("Please enter phone number!");
                        editTextPhone.requestFocus();
                    }
                    else {
                        phone = editTextPhone.getText().toString();
                        mAuth = FirebaseAuth.getInstance();
                        //Toast.makeText(LoginActivity.this, "phone number " + phone + "" + phone.length(), Toast.LENGTH_SHORT).show();
                        sendVerificationCode(phone);
                        btnGetVC.setVisibility(View.GONE);
                        editTextPhone.setVisibility(View.GONE);
                        txtInput1.setVisibility(View.GONE);
                        btnSI.setVisibility(View.VISIBLE);
                        txtInput2.setVisibility(View.VISIBLE);
                        editTextCode.setVisibility(View.VISIBLE);
                        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        saveData(phone);
                        // Toast.makeText(LoginActivity.this, "Added data successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    public void saveData(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONENUMBER, phone);
        editor.commit();
    }
    public void verifySignInCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, codeByUser);
        signInWithPhoneAuthCredential(credential);
    }
    // user sign in
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void sendVerificationCode(String phone){
        Toast.makeText(this, "" + phone, Toast.LENGTH_SHORT).show();
        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
        }
        if(phone.length() > 10 || phone.length()< 9){
            editTextPhone.setError("please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                 TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks // what to do when the code sent
        Toast.makeText(this, "1111111" , Toast.LENGTH_SHORT).show();
    }
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new OnVerificationStateChangedCallbacks() {
        // the func execute automatically
        @Override
        //
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String SMScode = phoneAuthCredential.getSmsCode();
            Toast.makeText(LoginActivity.this, "SMS code: " + SMScode, Toast.LENGTH_SHORT).show();
            if (SMScode != null){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, SMScode);
                signInWithPhoneAuthCredential(credential);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
        // code has been sent but not on the same device
        @Override
        public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            btnSI.setOnClickListener(v -> {
                String code = editTextCode.getText().toString();
                if(code.isEmpty() || code.length() < 0){
                    editTextCode.setText("Wrong OTP...");
                    editTextCode.requestFocus();
                    return;
                }
                if(s.isEmpty())
                    return;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(s, code);
                signInWithPhoneAuthCredential(credential);
            });
        }
    };

}