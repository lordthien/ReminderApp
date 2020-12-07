package com.example.ngocthien.remindertj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngocthien.remindertj.AddGroupTask.ModelAddGroupTask;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.Main.MainFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class adapter_addgrouptask extends FirebaseRecyclerAdapter<ModelAddGroupTask, adapter_addgrouptask.myviewholder> {

    private static final String TAG = "trantrongtin";
    public static final String GETNAME = "balbal";
    SharedPreferences sharedPreferences;
    public adapter_addgrouptask(@NonNull FirebaseRecyclerOptions<ModelAddGroupTask> options)  {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final ModelAddGroupTask modelAddGroupTask) {
        Random r = new Random();
        int red= r.nextInt(255 + 1);
        int green= r.nextInt(255 + 1);
        int blue= r.nextInt(255 + 1);
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.relativeLayout.setBackground(draw);
        holder.name.setText(modelAddGroupTask.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("GETKEY",getRef(position).getKey());
                Toast.makeText(v.getContext(), "Hello" + getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        Log.e(TAG, "myviewholder1: " + new ModelAddGroupTask().getName());
        return new myviewholder(view);
    }
    static class myviewholder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.tv_name);
            relativeLayout = itemView.findViewById(R.id.bg);
        }
    }
}





