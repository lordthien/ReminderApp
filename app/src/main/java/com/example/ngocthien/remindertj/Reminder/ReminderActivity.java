package com.example.ngocthien.remindertj.Reminder;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.ngocthien.remindertj.AppDefault.AppDefaultActivity;
import com.example.ngocthien.remindertj.R;

public class ReminderActivity extends AppDefaultActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.reminder_layout;
    }

    @NonNull
    @Override
    protected ReminderFragment createInitialFragment() {
        return ReminderFragment.newInstance();
    }


}
