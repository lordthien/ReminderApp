package com.example.ngocthien.remindertj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.ngocthien.remindertj.Analytics.AnalyticsApplication;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultActivity;

public class NewStartLogin extends AppCompatActivity {

    private TextView mVersionTextView;

    private String appVersion = "1.0";
    private Toolbar toolbar;
    private TextView contactMe;
    String theme;
    //    private UUID mId
    private AnalyticsApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_start_login);
    }
}
