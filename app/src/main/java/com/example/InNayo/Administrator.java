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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    String Category, StartTime, EndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        admin_et1 = (EditText)findViewById(R.id.admin_et1);
        admin_et2 = (EditText)findViewById(R.id.admin_et2);
        admin_et3 = (EditText)findViewById(R.id.admin_et3);
        admin_et4 = (EditText)findViewById(R.id.admin_et4);

        Spinner categorySpinner = (Spinner)findViewById(R.id.admin_spinner1);
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Spinner startSpinner = (Spinner)findViewById(R.id.admin_spinner2);
        ArrayAdapter startAdapter = ArrayAdapter.createFromResource(this,
                R.array.hour_start, android.R.layout.simple_spinner_item);
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(startAdapter);

        Spinner endSpinner = (Spinner)findViewById(R.id.admin_spinner3);
        ArrayAdapter endAdapter = ArrayAdapter.createFromResource(this,
                R.array.hour_end, android.R.layout.simple_spinner_item);
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                switch (position){
                    case 0:
                        Category = "4";
                        break;
                    case 1:
                        Category = "1";
                        break;
                    case 2:
                        Category = "5";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        StartTime = "08:00:00";
                        break;
                    case 1:
                        StartTime = "09:00:00";
                        break;
                    case 2:
                        StartTime = "10:00:00";
                        break;
                    case 3:
                        StartTime = "11:00:00";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        EndTime = "20:00:00";
                        break;
                    case 1:
                        EndTime = "21:00:00";
                        break;
                    case 2:
                        EndTime = "22:00:00";
                        break;
                    case 3:
                        EndTime = "23:00:00";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        admin_button1 = (Button)findViewById(R.id.admin_button1);
        admin_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = admin_et1.getText().toString();
                String Location = admin_et2.getText().toString();
                String Phone = admin_et3.getText().toString();
                String TotalPerson = admin_et4.getText().toString();

                insertToDatabases(Category, Name, Location, Phone, TotalPerson, StartTime, EndTime);
            }
        });
    }

    private void insertToDatabases(String Category, String Name, String Location, String Phone, String TotalPerson, String StartTime, String EndTime) {
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
                    String Category = (String) params[0];
                    String Name = (String) params[1];
                    String Location = (String) params[2];
                    String Phone = (String) params[3];
                    String TotalPerson = (String) params[4];
                    String StartTime = (String) params[5];
                    String EndTime = (String) params[6];

                    String link = "http://172.30.1.33/Administrator.php";
                    String data = URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(Category, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
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
        task.execute(Category, Name, Location, Phone, TotalPerson, StartTime, EndTime);
    }
}