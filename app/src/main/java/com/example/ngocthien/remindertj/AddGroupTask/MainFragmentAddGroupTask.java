package com.example.ngocthien.remindertj.AddGroupTask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngocthien.remindertj.AppDefault.AppDefaultActivity;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultFragment;
import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.Main.MainActivity;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.adapter_addgrouptask;
import com.example.ngocthien.remindertj.myadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.example.ngocthien.remindertj.adapter_addgrouptask;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.MODE_PRIVATE;

public class MainFragmentAddGroupTask extends AppDefaultFragment {
    public static RecyclerView recview_add_group_task;
    String a;
    FloatingActionButton floatingActionButton;
    adapter_addgrouptask adapter;
    SharedPreferences sharedPreferences, getkey, getphonenumberfornavi;
    String key;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        getkey = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        loadData();
        loadData1();
        recview_add_group_task = (RecyclerView) view.findViewById(R.id.recycler_view_add_group_task);
        recview_add_group_task.setLayoutManager(new LinearLayoutManager(getContext()));
        recview_add_group_task.setHasFixedSize(true);

        FirebaseRecyclerOptions<ModelAddGroupTask> options =
                new FirebaseRecyclerOptions.Builder<ModelAddGroupTask>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks"), ModelAddGroupTask.class)
                        .build();
        adapter = new adapter_addgrouptask(options);

        recview_add_group_task.setAdapter(adapter);
        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_group_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddGroupTask.class);
                startActivity(intent);
            }
        });
    }
    public void loadData1() {
        key = sharedPreferences.getString(MainActivity.GETKEY_ITEM_NAME, "");
    }
    public void loadData() {
        a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
    }
//           private void initRecyclerView(){
//            FirebaseRecyclerOptions<ModelAddGroupTask> options =
//                    new FirebaseRecyclerOptions.Builder<ModelAddGroupTask>()
//                            .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks"), ModelAddGroupTask.class)
//                            .build();
//            adapter = new adapter_addgrouptask(options);
//            recview_add_group_task.setAdapter(adapter);
//            adapter.startListening();

//        ItemTouchHelper.Callback itemTouchHelperCallback;
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(recview_add_group_task);
  //  }
//    ItemTouchHelper.SimpleCallback simpleCallback  = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            if(direction == ItemTouchHelper.LEFT){
//                Toast.makeText(getActivity(), "Deleting", Toast.LENGTH_SHORT).show();
//                adapter_addgrouptask.myviewholder my = (adapter_addgrouptask.myviewholder) viewHolder;
//                my.deleteItem();
//            }
//
//
//        }
//
//        @Override
//        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                    .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primary_text))
//                    .addActionIcon(R.drawable.ic_baseline_delete_24)
//                    .create()
//                    .decorate();
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//    };
    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }
    @Override
    protected int layoutRes() {
        return R.layout.activity_add_group_task;
    }
    public static MainFragmentAddGroupTask newInstance(){
        return new MainFragmentAddGroupTask();
    }
}
