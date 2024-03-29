package com.example.InNayo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.example.InNayo.Reservation.urls;


public class MemberLogin extends Fragment {
    EditText mID, mPassword;
    ProgressDialog dialog = null;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    Context memberlogincontext;
    Handler handler;
    String res;
    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.activity_member_login, container, false);
        memberlogincontext = container.getContext();

        pref = memberlogincontext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        TextView member_login_tv1 = (TextView) rootview.findViewById(R.id.member_login_tv1);
        member_login_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(memberlogincontext, Find.class);
                startActivity(intent);
            }
        });

        TextView member_login_tv2 = (TextView) rootview.findViewById(R.id.member_login_tv2);
        member_login_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(memberlogincontext, Signup.class);
                startActivity(intent);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        handler = new Handler(Looper.getMainLooper());

        mID = (EditText) rootview.findViewById(R.id.member_login_ed1);
        mPassword = (EditText) rootview.findViewById(R.id.member_login_ed2);

        Button member_login_bt1 = (Button) rootview.findViewById(R.id.member_login_bt1);
        member_login_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(memberlogincontext, "", "Validating user...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       login();
                    }
                }).start();
            }
        });

        return rootview;
    }

    void login() {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(urls+"Login.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("Id", mID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Pw", mPassword.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);
            res = response.replace('"', ' ');
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            if (response.equalsIgnoreCase("No Such User Found")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(memberlogincontext, "Login Fail" , Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(memberlogincontext, "안녕하세요!" + res + "님", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                        toast.show();
                        editor.putString("logined_name", res);
                        editor.putString("logined_id", mID.getText().toString());
                        editor.apply();
//                        ((Login)getActivity()).refresh();
                        Intent intent = new Intent(memberlogincontext, Main.class);
                        startActivity(intent);
                    }
                });
            }
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
}