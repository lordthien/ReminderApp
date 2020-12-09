package com.example.ngocthien.remindertj.Main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.ngocthien.remindertj.Login.Start_Login;
import com.example.ngocthien.remindertj.model;
import com.example.ngocthien.remindertj.myadapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ngocthien.remindertj.About.AboutActivity;
import com.example.ngocthien.remindertj.AddToDo.AddToDoActivity;
import com.example.ngocthien.remindertj.AddToDo.AddToDoFragment;
import com.example.ngocthien.remindertj.Analytics.AnalyticsApplication;
import com.example.ngocthien.remindertj.AppDefault.AppDefaultFragment;
import com.example.ngocthien.remindertj.R;
import com.example.ngocthien.remindertj.Reminder.ReminderFragment;
import com.example.ngocthien.remindertj.Utility.ItemTouchHelperClass;
import com.example.ngocthien.remindertj.Utility.RecyclerViewEmptySupport;
import com.example.ngocthien.remindertj.Utility.StoreRetrieveData;
import com.example.ngocthien.remindertj.Utility.ToDoItem;
import com.example.ngocthien.remindertj.Utility.TodoNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.util.Log.*;

public class MainFragment extends AppDefaultFragment{


    private FloatingActionButton mAddToDoItemFAB;
    public static RecyclerView recview;
    myadapter adapter;
    String key;

    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";

    private static final int REQUEST_ID_TODO_ITEM = 100;

    public static final String FILENAME = "todoitems.json";

    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    String a;

    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";
    public static String TAG = MainFragment.class.getSimpleName();
    FirebaseRecyclerOptions<model> options;
    SharedPreferences sharedPreferences, sharedPreferences1;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private AnalyticsApplication app;
    private String[] testStrings = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();

        theme = getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);

        if (theme.equals(LIGHTTHEME)) {
            mTheme = R.style.CustomStyle_LightTheme;
        } else {
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        this.getActivity().setTheme(mTheme);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        sharedPreferences = getActivity().getSharedPreferences(Start_Login.MyPREFERENCES_STARTLOGIN, MODE_PRIVATE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loadData();
        loadData1();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();
        recview = view.findViewById(R.id.toDoRecyclerView);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.setHasFixedSize(true);
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo").child(a).child("GroupTasks").child(key).child("SingleTask"), model.class)
                        .build();
        adapter = new myadapter(options);

        recview.setAdapter(adapter);

        mAddToDoItemFAB = (FloatingActionButton) view.findViewById(R.id.addToDoItemFAB);
        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                app.send(this, "Action", "FAB pressed");
                Intent newTodo = new Intent(getContext(), AddToDoActivity.class);
                ToDoItem item = new ToDoItem("","", false, null);
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setTodoColor(color);

                newTodo.putExtra(TODOITEM, item);
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });

        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
            @Override
            public void hide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight() + fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };
    }
@Override
public void onStart() {
    super.onStart();
    if(adapter!=null){
        adapter.startListening();
    }
}







    private AlarmManager getAlarmManager() {
        return (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
    }



        public void loadData(){
            a = sharedPreferences.getString(Start_Login.PHONENUMBER_STARTlOGIN, "");
            Toast.makeText(app, "Hello "  + a, Toast.LENGTH_SHORT).show();
        }
    public void loadData1(){
        key = sharedPreferences.getString( MainActivity.GETKEY_ITEM_NAME, "");
        Toast.makeText(app, "Hellovalue "  + key, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        recview.removeOnScrollListener(customRecyclerScrollViewListener);
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_main;
    }
    public static MainFragment newInstance() {
        return new MainFragment();
    }
}
