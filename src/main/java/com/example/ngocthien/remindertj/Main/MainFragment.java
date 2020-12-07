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
    private static final String TAG1 = "USER ID"  ;
    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    public static RecyclerView recview;
    myadapter adapter;
    String key;

    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";
   // private MainFragment.BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;
    public ItemTouchHelper itemTouchHelper;
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
       // e(TAG, "onViewCreated: " );
        recview.setAdapter(adapter);

        //mCoordLayout = (CoordinatorLayout) view.findViewById(R.id.myCoordinatorLayout);
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
                //noinspection ResourceType
//                String color = getResources().getString(R.color.primary_ligher);
                newTodo.putExtra(TODOITEM, item);
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });
//        mRecyclerView = (RecyclerView)findViewById(R.id.toDoRecyclerView);
//        mRecyclerView = (RecyclerViewEmptySupport) view.findViewById(R.id.toDoRecyclerView);
//        if (theme.equals(LIGHTTHEME)) {
//            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.primary_lightest));
//        }
//        mRecyclerView.setEmptyView(view.findViewById(R.id.toDoEmptyView));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


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
//        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);
//
//        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
//
//
//        mRecyclerView.setAdapter(adapter);
//        setUpTransitions();


    }



//    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData) {
//        ArrayList<ToDoItem> items = null;
//
//        try {
//            items = storeRetrieveData.loadFromFile();
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        if (items == null) {
//            items = new ArrayList<>();
//        }
//        return items;
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        app.send(this);
//
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
//        if (sharedPreferences.getBoolean(ReminderFragment.EXIT, false)) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(ReminderFragment.EXIT, false);
//            editor.apply();
//            getActivity().finish();
//        }
//
//        if (getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false)) {
//            SharedPreferences.Editor editor = getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
//            editor.putBoolean(RECREATE_ACTIVITY, false);
//            editor.apply();
//            getActivity().recreate();
//        }
//
//
//    }

@Override
public void onStart() {
    super.onStart();
    if(adapter!=null){
        adapter.startListening();
    }
}
//    @Override
//    public void onStart() {
//        app = (AnalyticsApplication) getActivity().getApplication();
//        super.onStart();
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
//        if (sharedPreferences.getBoolean(CHANGE_OCCURED, false)) {
//
////            mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);
////            adapter = new MainFragment.BasicListAdapter(mToDoItemsArrayList);
////            mRecyclerView.setAdapter(adapter);
//
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(CHANGE_OCCURED, false);
////            editor.commit();
//            editor.apply();
//
//
//        }
//    }
//
    public void addThemeToSharedPreferences(String theme) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_SAVED, theme);
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAbout:
                Intent i = new Intent(getContext(), AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.chatbot:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM) {
//            ToDoItem item = (ToDoItem) data.getSerializableExtra(TODOITEM);
//            if (item.getToDoText().length() <= 0) {
//                return;
//            }
//            boolean existed = false;
//
//            if (item.hasReminder() && item.getToDoDate() != null) {
//                Intent i = new Intent(getContext(), TodoNotificationService.class);
//                i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
//                i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
//                createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
//
//            }
//
//            for (int i = 0; i < mToDoItemsArrayList.size(); i++) {
//                if (item.getIdentifier().equals(mToDoItemsArrayList.get(i).getIdentifier())) {
//                    mToDoItemsArrayList.set(i, item);
//                    existed = true;
//                    adapter.notifyDataSetChanged();
//                    break;
//                }
//            }
//            if (!existed) {
//                addToDataStore(item);
//            }
//
//
//        }
//    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode) {
        PendingIntent pi = PendingIntent.getService(getContext(), requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    private void createAlarm(Intent i, int requestCode, long timeInMillis) {
        AlarmManager am = getAlarmManager();
        PendingIntent pi = PendingIntent.getService(getContext(), requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());
    }

    private void deleteAlarm(Intent i, int requestCode) {
        if (doesPendingIntentExist(i, requestCode)) {
            PendingIntent pi = PendingIntent.getService(getContext(), requestCode, i, PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);
            d("NgocThien", "PI Cancelled " + doesPendingIntentExist(i, requestCode));
        }
    }
//
//    private void addToDataStore(ToDoItem item) {
//        mToDoItemsArrayList.add(item);
//        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);
//
//    }


    public void makeUpItems(ArrayList<ToDoItem> items, int len) {
        for (String testString : testStrings) {
            ToDoItem item = new ToDoItem(testString,testString, false, new Date());
            //noinspection ResourceType
//            item.setTodoColor(getResources().getString(R.color.red_secondary));
            items.add(item);
        }

    }

//    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
//        private ArrayList<ToDoItem> items;
//
//        @Override
//        public void onItemMoved(int fromPosition, int toPosition) {
//            if (fromPosition < toPosition) {
//                for (int i = fromPosition; i < toPosition; i++) {
//                    Collections.swap(items, i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    Collections.swap(items, i, i - 1);
//                }
//            }
//            notifyItemMoved(fromPosition, toPosition);
//        }
//
//        @Override
//        public void onItemRemoved(final int position) {
//            //Remove this line if not using Google Analytics
//            app.send(this, "Action", "Swiped Todo Away");
//
//            mJustDeletedToDoItem = items.remove(position);
//            mIndexOfDeletedToDoItem = position;
//            Intent i = new Intent(getContext(), TodoNotificationService.class);
//            deleteAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode());
//            notifyItemRemoved(position);
//
////            String toShow = (mJustDeletedToDoItem.getToDoText().length()>20)?mJustDeletedToDoItem.getToDoText().substring(0, 20)+"...":mJustDeletedToDoItem.getToDoText();
//            String toShow = "Todo";
//            Snackbar.make(mCoordLayout, "Deleted " + toShow, Snackbar.LENGTH_LONG)
//                    .setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            //Comment the line below if not using Google Analytics
//                            app.send(this, "Action", "UNDO Pressed");
//                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
//                            if (mJustDeletedToDoItem.getToDoDate() != null && mJustDeletedToDoItem.hasReminder()) {
//                                Intent i = new Intent(getContext(), TodoNotificationService.class);
//                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
//                                i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());
//                                createAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode(), mJustDeletedToDoItem.getToDoDate().getTime());
//                            }
//                            notifyItemInserted(mIndexOfDeletedToDoItem);
//                        }
//                    }).show();
//        }
//
//        @Override
//        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
//            return new ViewHolder(v);
//        }

//        @Override
//        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
//            ToDoItem item = items.get(position);
////            if(item.getToDoDate()!=null && item.getToDoDate().before(new Date())){
////                item.setToDoDate(null);
////            }
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
//            //Background color for each to-do item. Necessary for night/day mode
//            int bgColor;
//            //color of title text in our to-do item. White for night mode, dark gray for day mode
//            int todoTextColor;
//            if (sharedPreferences.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)) {
//                bgColor = Color.WHITE;
//                todoTextColor = getResources().getColor(R.color.secondary_text);
//            } else {
//                bgColor = Color.DKGRAY;
//                todoTextColor = Color.WHITE;
//            }
//            holder.linearLayout.setBackgroundColor(bgColor);
//
//            if (item.hasReminder() && item.getToDoDate() != null) {
//                holder.mToDoTextview.setMaxLines(1);
//                holder.mTimeTextView.setVisibility(View.VISIBLE);
////                holder.mToDoTextview.setVisibility(View.GONE);
//            } else {
//                holder.mTimeTextView.setVisibility(View.GONE);
//                holder.mToDoTextview.setMaxLines(2);
//            }
//            holder.mToDoTextview.setText(item.getToDoText());
//            holder.mToDoTextview.setTextColor(todoTextColor);
////            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));
//
////            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
//            //We check if holder.color is set or not
////            if(item.getTodoColor() == null){
////                ColorGenerator generator = ColorGenerator.MATERIAL;
////                int color = generator.getRandomColor();
////                item.setTodoColor(color+"");
////            }
//
//            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
//                    .textColor(Color.WHITE)
//                    .useFont(Typeface.DEFAULT)
//                    .toUpperCase()
//                    .endConfig()
//                    .buildRound(item.getToDoText().substring(0, 1), item.getTodoColor());
//
////            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
//            holder.mColorImageView.setImageDrawable(myDrawable);
//            if (item.getToDoDate() != null) {
//                String timeToShow;
//                if (android.text.format.DateFormat.is24HourFormat(getContext())) {
//                    timeToShow = AddToDoFragment.formatDate(MainFragment.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
//                } else {
//                    timeToShow = AddToDoFragment.formatDate(MainFragment.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
//                }
//                holder.mTimeTextView.setText(timeToShow);
//            }
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return items.size();
//        }
//
//        BasicListAdapter(ArrayList<ToDoItem> items) {
//
//            this.items = items;
//        }


        @SuppressWarnings("deprecation")
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            View mView;
//            LinearLayout linearLayout;
//            TextView mToDoTextview;
//            //            TextView mColorTextView;
//            ImageView mColorImageView;
//            TextView mTimeTextView;
////            int color = -1;
//
//            public ViewHolder(View v) {
//                super(v);
//                mView = v;
//                v.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());
//                        Intent i = new Intent(getContext(), AddToDoActivity.class);
//                        i.putExtra(TODOITEM, item);
//                        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
//                    }
//                });
//                mToDoTextview = (TextView) v.findViewById(R.id.toDoListItemTextview);
//                mTimeTextView = (TextView) v.findViewById(R.id.todoListItemTimeTextView);
////                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
//                mColorImageView = (ImageView) v.findViewById(R.id.toDoListItemColorImageView);
//                linearLayout = (LinearLayout) v.findViewById(R.id.listItemLinearLayout);
//            }
//
//
//        }
//    }

    //Used when using custom fonts
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

//    private void saveDate() {
//        try {
//            storeRetrieveData.saveToFile(mToDoItemsArrayList);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        try {
//            storeRetrieveData.saveToFile(mToDoItemsArrayList);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//    }

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


    //    public void setUpTransitions(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            Transition enterT = new Slide(Gravity.RIGHT);
//            enterT.setDuration(500);
//
//            Transition exitT = new Slide(Gravity.LEFT);
//            exitT.setDuration(300);
//
//            Fade fade = new Fade();
//            fade.setDuration(500);
//
//            getWindow().setExitTransition(fade);
//            getWindow().setReenterTransition(fade);
//
//        }
//    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_main;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }
}
