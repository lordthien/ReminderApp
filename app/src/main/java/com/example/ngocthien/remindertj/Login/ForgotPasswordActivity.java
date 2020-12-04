package com.example.ngocthien.remindertj.Login;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngocthien.remindertj.Password;
import com.example.ngocthien.remindertj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;
public class ForgotPasswordActivity extends AppCompatActivity {
    Button btn_send;
    TextInputEditText recover_phonenumber;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    String phone_fb;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    String codeSent;
    TextInputEditText verfiCode;
    Button btn_verifyCode;
    public String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_send = findViewById(R.id.btn_send);
        verfiCode = findViewById(R.id.tiet_verfiCode);
        btn_verifyCode = findViewById(R.id.btn_verifiCode);
        recover_phonenumber = findViewById(R.id.recover_phonenumber);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = recover_phonenumber.getText().toString();
                rootNode = FirebaseDatabase.getInstance();
                databaseReference = rootNode.getReference().child("UserInfo").child(phone);
                if(phone.isEmpty()){
                    recover_phonenumber.setText("You need to enter phone number!");
                }
                else{
                    btn_verifyCode.setVisibility(View.VISIBLE);
                    verfiCode.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                    recover_phonenumber.setVisibility(View.GONE);
                    sendVerificationCode(phone);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                phone_fb = dataSnapshot.child("phone").getValue().toString();
                                if(phone_fb.equals(phone)){
                                    btn_verifyCode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(verfiCode.getText().toString().equals(codeSent)){
                                                Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                          //  Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, PasswordActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void sendVerificationCode(String phone){
        Toast.makeText(this, "" + phone, Toast.LENGTH_SHORT).show();
        if(phone.isEmpty()){
            recover_phonenumber.setError("Phone number is required");
            recover_phonenumber.requestFocus();
        }
        if(phone.length() > 10 || phone.length()< 9){
            recover_phonenumber.setError("please enter a valid phone");
            recover_phonenumber.requestFocus();
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
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // the func execute automatically
        @Override
        //
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String SMScode = phoneAuthCredential.getSmsCode();
            Toast.makeText(ForgotPasswordActivity.this, "SMS code: " + SMScode, Toast.LENGTH_SHORT).show();
            if (SMScode != null){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, SMScode);
                signInWithPhoneAuthCredential(credential);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
        // code has been sent but not on the same device
        @Override
        public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            btn_verifyCode.setOnClickListener(v -> {
                String code = verfiCode.getText().toString();
                if(code.isEmpty() || code.length() < 0){
                    verfiCode.setText("Wrong OTP...");
                    verfiCode.requestFocus();
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