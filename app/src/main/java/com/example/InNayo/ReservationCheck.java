package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ReservationCheck extends AppCompatActivity {
    EditText reservation_check_place, reservation_check_year, reservation_check_month, reservation_check_date,
            reservation_check_ap, reservation_check_hour, reservation_check_person;
    Button reservation_check_bt;
    String Time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

        reservation_check_place = (EditText)findViewById(R.id.reservation_check_place);
        reservation_check_year = (EditText)findViewById(R.id.reservation_check_year);
        reservation_check_month = (EditText)findViewById(R.id.reservation_check_month);
        reservation_check_date = (EditText)findViewById(R.id.reservation_check_date);
        reservation_check_ap = (EditText)findViewById(R.id.reservation_check_ap);
        reservation_check_hour = (EditText)findViewById(R.id.reservation_check_hour);
        reservation_check_person = (EditText)findViewById(R.id.reservation_check_person);

        String checkplace = getIntent().getExtras().getString("selectedplace");
        reservation_check_place.setText(checkplace);

        String selectedyear = getIntent().getExtras().getString("selectedyear");
        reservation_check_year.setText(selectedyear);

        String selectedmonth = getIntent().getExtras().getString("selectedmonth");
        reservation_check_month.setText(selectedmonth);

        String selecteddate = getIntent().getExtras().getString("selecteddate");
        reservation_check_date.setText(selecteddate);

        String selectedap = getIntent().getExtras().getString("selectedap");
        if (selectedap.equals("AM") == true){
            reservation_check_ap.setText("오전 ");
        }else{
            reservation_check_ap.setText("오후 ");
        }

        String selectedhour = getIntent().getExtras().getString("selectedhour");
        reservation_check_hour.setText(selectedhour);

        String selectedpp = getIntent().getExtras().getString("selectedpp");
        reservation_check_person.setText(selectedpp);

        reservation_check_bt = findViewById(R.id.reservation_check_bt);
        reservation_check_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Place = reservation_check_place.getText().toString();
                String Year = reservation_check_year.getText().toString();
                String Month = reservation_check_month.getText().toString();
                String Day = reservation_check_date.getText().toString();
                String AP = reservation_check_ap.getText().toString();
                String Hour = reservation_check_hour.getText().toString();
                String Person = reservation_check_person.getText().toString();

                String Date = Year + "-" + Month + "-" + Day;
                if(AP.equals("AM") == true){
                    Time = Hour + ":00:00";
                }else{
                    Time = String.valueOf(Integer.valueOf(Hour) + 12) + ":00:00";
                }
                insertToDatabases(Place, Date, Time, Person);
            }
        });
    }

    private void insertToDatabases(String Place, String Date, String Time, String Person) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ReservationCheck.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast toast = Toast.makeText(ReservationCheck.this, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String Place = (String) params[0];
                    String Date = (String) params[1];
                    String Time = (String) params[2];
                    String Person = (String) params[3];

                    String link = "http://172.30.27.84/Reservation.php";
                    String data = URLEncoder.encode("Place", "UTF-8") + "=" + URLEncoder.encode(Place, "UTF-8");
                    data += "&" + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8");
                    data += "&" + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(Time, "UTF-8");
                    data += "&" + URLEncoder.encode("Person", "UTF-8") + "=" + URLEncoder.encode(Person, "UTF-8");

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
        task.execute(Place, Date, Time, Person);
    }
}