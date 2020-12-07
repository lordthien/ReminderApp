package com.example.ngocthien.remindertj.About;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngocthien.remindertj.Analytics.AnalyticsApplication;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultFragment;
import com.example.ngocthien.remindertj.Login.LoginActivity;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AboutFragment extends AppDefaultFragment {
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;

    private long menuID;
    TextView  passwordabout, repasswordabout;

    public AboutFragment(){

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_about;
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
}
