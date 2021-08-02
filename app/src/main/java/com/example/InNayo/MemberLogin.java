package com.example.InNayo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MemberLogin extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.activity_member_login, container, false);

//        TextView tv_pw = (TextView) rootview.findViewById(R.id.tv_pw);
//        tv_pw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Find.class);
//                startActivity(intent);
//            }
//        });

//        Button btnLogin = (Button) rootview.findViewById(R.id.b_login);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Login.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView tvSignup = (TextView) rootview.findViewById(R.id.tv_signup);
//        tvSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Signup.class);
//                startActivity(intent);
//            }
//        });

        return rootview;
    }
}