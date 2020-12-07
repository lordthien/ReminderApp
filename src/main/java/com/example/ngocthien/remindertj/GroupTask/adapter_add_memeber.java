package com.example.ngocthien.remindertj.GroupTask;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngocthien.remindertj.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Random;

public class adapter_add_memeber extends FirebaseRecyclerAdapter<ModelAddMember, adapter_add_memeber.myviewholder> {
    public adapter_add_memeber(@NonNull FirebaseRecyclerOptions<ModelAddMember> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ModelAddMember modelAddMember) {
        Random r = new Random();
        int red= r.nextInt(255 + 1);
        int green= r.nextInt(255 + 1);
        int blue= r.nextInt(255 + 1);
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.relativeLayout_addmemeber.setBackground(draw);
        holder.tv_memberphone.setText(modelAddMember.getMemberphone());
        holder.tv_namehost.setText(modelAddMember.getNamehost());
        holder.tv_namegrouptask.setText(modelAddMember.getNamegrouptask());
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_addmember,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView tv_memberphone, tv_namehost, tv_namegrouptask;
        RelativeLayout relativeLayout_addmemeber;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            relativeLayout_addmemeber = itemView.findViewById(R.id.bg);
            tv_memberphone = itemView.findViewById(R.id.tv_memberphone);
            tv_namegrouptask = itemView.findViewById(R.id.tv_namegrouptask);
            tv_namehost = itemView.findViewById(R.id.tv_namehost);
        }
    }
}
