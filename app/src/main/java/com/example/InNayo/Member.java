package com.example.InNayo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Member extends Fragment {
    private EditText mName, mID, mPassword, mRePassword, mPhone, mEmail;
    Context membercontext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.activity_member, container, false);
        membercontext = container.getContext();

        Button member_button1 = (Button) rootview.findViewById(R.id.member_button1);
        member_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString();
                String Id = mID.getText().toString();
                String Pw = mPassword.getText().toString();
                String Phone = mPhone.getText().toString();
                String Email = mEmail.getText().toString();

                insertToDatabase(Name, Id, Pw, Phone, Email);
            }
        });

        mName = (EditText) rootview.findViewById(R.id.member_et1);
        mID = (EditText) rootview.findViewById(R.id.member_et2);
        mPassword = (EditText) rootview.findViewById(R.id.member_et3);
        mPhone = (EditText) rootview.findViewById(R.id.member_et5);
        mEmail = (EditText) rootview.findViewById(R.id.member_et6);

        return rootview;
    }

    private void insertToDatabase(String Name, String Id, String Pw, String Phone, String Email) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(membercontext, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast toast = Toast.makeText(membercontext, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
               try {
                    String Name = (String) params[0];
                    String Id = (String) params[1];
                    String Pw = (String) params[2];
                    String Phone = (String) params[3];
                    String Email = (String) params[4];

                    String link = "http://192.168.35.167/Register.php";
                    String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
                    data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Name, Id, Pw, Phone, Email);
    }
}