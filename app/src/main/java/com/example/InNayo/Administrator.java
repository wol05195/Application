package com.example.InNayo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Administrator extends AppCompatActivity {
    EditText admin_et1, admin_et2, admin_et3, admin_et4, admin_et5, admin_et6;
    Button admin_button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        admin_et1 = (EditText)findViewById(R.id.admin_et1);
        admin_et2 = (EditText)findViewById(R.id.admin_et2);
        admin_et3 = (EditText)findViewById(R.id.admin_et3);
        admin_et4 = (EditText)findViewById(R.id.admin_et4);
        admin_et5 = (EditText)findViewById(R.id.admin_et5);
        admin_et6 = (EditText)findViewById(R.id.admin_et6);

        admin_button1 = (Button)findViewById(R.id.admin_button1);
        admin_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = admin_et1.getText().toString();
                String Location = admin_et2.getText().toString();
                String Phone = admin_et3.getText().toString();
                String TotalPerson = admin_et4.getText().toString();
                String StartTime = admin_et5.getText().toString();
                String EndTime = admin_et6.getText().toString();

                insertToDatabases(Name, Location, Phone, TotalPerson, StartTime, EndTime);
            }
        });
    }

    private void insertToDatabases(String Name, String Location, String Phone, String TotalPerson, String StartTime, String EndTime) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Administrator.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast toast = Toast.makeText(Administrator.this, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String Name = (String) params[0];
                    String Location = (String) params[1];
                    String Phone = (String) params[2];
                    String TotalPerson = (String) params[3];
                    String StartTime = (String) params[4];
                    String EndTime = (String) params[5];

                    String link = "http://192.168.0.8/Administrator.php";
                    String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Location", "UTF-8") + "=" + URLEncoder.encode(Location, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
                    data += "&" + URLEncoder.encode("TotalPerson", "UTF-8") + "=" + URLEncoder.encode(TotalPerson, "UTF-8");
                    data += "&" + URLEncoder.encode("StartTime", "UTF-8") + "=" + URLEncoder.encode(StartTime, "UTF-8");
                    data += "&" + URLEncoder.encode("EndTime", "UTF-8") + "=" + URLEncoder.encode(EndTime, "UTF-8");

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
        task.execute(Name, Location, Phone, TotalPerson, StartTime, EndTime);
    }
}