package com.example.ngocthien.remindertj;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {
    public static String TAG1 = myadapter.class.getSimpleName();
    SharedPreferences sharedPreferences, getkey;
    String a, key;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options)
    {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull final myadapter.myviewholder holder, final int position, @NonNull final model model) {

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
        holder.itemView.setOnClickListener(v -> {
            sharedPreferences = v.getContext().getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, Context.MODE_PRIVATE);
            getkey = v.getContext().getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
            loadData();
            loadData1();
            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.itemView.getContext())
                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                    .setGravity(Gravity.CENTER)
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
                //Toast.makeText(view.getContext(), "getkeyyyyy : " + key, Toast.LENGTH_SHORT).show();
                Map<String,Object> map=new HashMap<>();
                map.put("title",edit_title.getText().toString());
                map.put("description",edit_description.getText().toString());
                map.put("date",edit_date.getText().toString());
                map.put("time", edit_time.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("UserInfo")
                        .child(a)
                        .child("GroupTasks").child(key).child("SingleTask")
                        .child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(aVoid -> dialogPlus.dismiss())
                        .addOnFailureListener(e -> dialogPlus.dismiss());
            });
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
    public void loadData(){
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
    public void loadData1() {
        key = getkey.getString(MainActivity.GETKEY_ITEM_NAME, "");
    }
}




