package com.example.ngocthien.remindertj.About;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngocthien.remindertj.Login.UserInfo;
import com.example.ngocthien.remindertj.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    UserInfo userInfo = new UserInfo();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
        TextInputEditText phone = (TextInputEditText)view.findViewById(R.id.phonenumber_about);
        TextInputEditText pass = (TextInputEditText)view.findViewById(R.id.password_about);
        TextInputEditText repass = (TextInputEditText)view.findViewById(R.id.repassword_about);
        Button btn_save = (Button)view.findViewById(R.id.btn_save);
        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference("UserInfo").child("0827342755");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                  // Toast.makeText(getActivity(), "Hellloo", Toast.LENGTH_SHORT).show();
                    userInfo.setPhone(dataSnapshot.child("phone").getValue(String.class));
                    userInfo.setPassword(dataSnapshot.child("password").getValue(String.class));
                    userInfo.setRepassword(dataSnapshot.child("repassword").getValue(String.class));

                    phone.setText(userInfo.getPhone());
                    pass.setText(userInfo.getPassword());
                    repass.setText(userInfo.getRepassword());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });
        return view;
    }
}