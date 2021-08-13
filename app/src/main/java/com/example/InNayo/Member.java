package com.example.InNayo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Member extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_member,container,false);

        Button member_button1 = (Button) rootview.findViewById(R.id.member_button1);
        member_button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), Main.class);
               startActivity(intent);
           }
       });

//        Button b_signup = (Button) rootview.findViewById(R.id.b_signup);
//        b_signup.setOnClickListener(v -> {
//            Toast toast = Toast.makeText(getActivity(), "회원가입 완료.", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
//            toast.show();
//
//        });
        return rootview;
    }
}