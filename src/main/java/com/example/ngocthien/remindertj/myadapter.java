package com.example.ngocthien.remindertj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngocthien.remindertj.AddToDo.AddToDoFragment;
import com.example.ngocthien.remindertj.GroupTask.ActivityAddMember;
import com.example.ngocthien.remindertj.Main.MainFragment;
import com.example.ngocthien.remindertj.Utility.ToDoItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {

    ArrayList<model> arrayList = new ArrayList<>();
    String title, description, date, time ;
    public static String TAG1 = myadapter.class.getSimpleName();
    public static final String NAME = "name";
    model model;

    ActivityAddMember activityAddMember = new ActivityAddMember();
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myadapter.myviewholder holder, final int position, @NonNull final model model) {
        arrayList.add(new model(title, description,date, time));

        Random r = new Random();
        int red= r.nextInt(255 + 1);
        int green= r.nextInt(255 + 1);
        int blue= r.nextInt(255 + 1);

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.relativeLayout.setBackground(draw);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());



        ;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();
                View myview = dialogPlus.getHolderView();
                final EditText edit_title=myview.findViewById(R.id.ed_title);
                final EditText edit_description=myview.findViewById(R.id.ed_description);
                final EditText edit_date=myview.findViewById(R.id.ed_date);
                final EditText edit_time = myview.findViewById(R.id.ed_time);
                Button update=myview.findViewById(R.id.btn_update);

                edit_title.setText(model.getTitle());
                edit_description.setText(model.getDescription());
                edit_date.setText(model.getDate());
                edit_time.setText(model.getTime());
                dialogPlus.show();
                update.setOnClickListener(view -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("title",edit_title.getText().toString());
                    map.put("description",edit_description.getText().toString());
                    map.put("date",edit_date.getText().toString());
                    map.put("time", edit_time.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("AddTaskAction")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialogPlus.dismiss();
                                }
                            })
                            .addOnFailureListener(e -> dialogPlus.dismiss());
                });
            }
        });
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singrow,parent,false);
        Log.e(TAG1, "onCreateViewHolder: ");
        return new myviewholder(view);
    }
    static class myviewholder extends RecyclerView.ViewHolder{
        TextView title, description, date, time;
        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            title =(TextView) itemView.findViewById(R.id.tv_title);
            description=itemView.findViewById(R.id.tv_description);
            date =itemView.findViewById(R.id.tv_date);
            time =itemView.findViewById(R.id.tv_time);

            relativeLayout =itemView.findViewById(R.id.bg);
        }
    }

}




