package com.example.ngocthien.remindertj.Login;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ngocthien.remindertj.R;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {


    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] color = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Button signinbutton = (Button)findViewById(R.id.btnSignin);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        models = new ArrayList<>();
        models.add(new Model(R.mipmap.ic_launcher,"Brochure","Chưa biết ghi gì để trống"));
        models.add(new Model(R.drawable.sticker,"Sticker","Để trống ít bữa biết ghi gì rồi ghi"));
        models.add(new Model(R.drawable.poster,"Poster","Ghi để chơi"));
        models.add(new Model(R.drawable.namecard,"Namecard","Ghi cho vui"));

        adapter = new Adapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] color_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        color = color_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position< (adapter.getCount()-1)&& position < (color.length -1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(
                            positionOffset,
                            color[position],
                            color[position +1]
                    )
                    );
                }
                else {
                    viewPager.setBackgroundColor(color[color.length -1 ]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}