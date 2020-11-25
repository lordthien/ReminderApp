package com.example.ngocthien.remindertj.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngocthien.remindertj.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button btn_send;
    TextInputEditText recover_phonenumber;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    String phone_fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_send = findViewById(R.id.btn_send);
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
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                phone_fb = dataSnapshot.child("phone").getValue().toString();
//                            Toast.makeText(ForgotPasswordActivity.this, "phonefb: " + phone_fb, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(ForgotPasswordActivity.this, "phone: " + phone, Toast.LENGTH_SHORT).show();
                                if(phone_fb.equals(phone)){
                                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                                    startActivity(intent);
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
}