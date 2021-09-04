package com.example.InNayo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NonMemberLogin extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_non_member_login,container,false);

//        Button non_member_bt = (Button) rootview.findViewById(R.id.non_member_bt);
//        non_member_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Main.class);
//                startActivity(intent);
//            }
//        });

        TextView non_member_login_tv = (TextView) rootview.findViewById(R.id.non_member_login_tv);
        non_member_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Signup.class);
                startActivity(intent);
            }
        });
        return rootview;
    }
}