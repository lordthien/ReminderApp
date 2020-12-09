package com.example.ngocthien.remindertj.Chatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ngocthien.remindertj.R;

public class ChatbotActivity extends Fragment implements View.OnClickListener {
    TextView getStarted;
    Fragment ChatbotDetail;

    @NonNull
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_chatbot,container,false);
        getStarted = view.findViewById(R.id.getStarted);
        getStarted.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        ChatbotDetail chatbotDetail = new ChatbotDetail();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, chatbotDetail);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}