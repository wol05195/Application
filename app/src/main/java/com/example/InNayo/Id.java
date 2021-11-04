package com.example.InNayo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
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

public class Id extends Fragment {
    EditText mID, mPhone;
    TextView id_tv1;
    Button id_button1;
    ProgressDialog dialog = null;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    Context idcontext;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_id,container,false);
        idcontext = container.getContext();
        handler = new Handler(Looper.getMainLooper());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mID = (EditText) rootview.findViewById(R.id.id_et1);
        mPhone = (EditText) rootview.findViewById(R.id.id_et2);

        id_tv1 = (TextView) rootview.findViewById(R.id.id_tv1);
        id_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(idcontext, Login.class);
                startActivity(intent);
            }
        });

        id_button1 = (Button) rootview.findViewById(R.id.id_button1);
        id_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(idcontext,"","Validating user...",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        find_id();
                    }
                }).start();
            }
        });
        return rootview;
    }

    void find_id() {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(urls+"Find_Id.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("Id", mID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("Phone", mPhone.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);
            String res = response.replace('"', ' ');
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
                        Toast.makeText(idcontext, "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(idcontext, "비밀번호는" + res + "입니다.", Toast.LENGTH_LONG).show();
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