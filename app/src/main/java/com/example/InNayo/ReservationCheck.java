package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.InNayo.Reservation.urls;

public class ReservationCheck extends AppCompatActivity {
    EditText reservation_check_place, reservation_check_year, reservation_check_month, reservation_check_date,
            reservation_check_ap, reservation_check_hour, reservation_check_person, reservation_check_name;
    Button reservation_check_bt;
    String Time, logined_name;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

        reservation_check_name = (EditText)findViewById(R.id.reservation_check_name);
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

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name","");
        reservation_check_name.setText(logined_name);

        reservation_check_bt = findViewById(R.id.reservation_check_bt);
        reservation_check_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = reservation_check_name.getText().toString().replace(" ","");
                String Place = reservation_check_place.getText().toString();
                String Year = reservation_check_year.getText().toString();
                String Month = reservation_check_month.getText().toString();
                String Day = reservation_check_date.getText().toString();
                String AP = reservation_check_ap.getText().toString();
                String Hour = reservation_check_hour.getText().toString();
                String Person = reservation_check_person.getText().toString();

                String Date = Year + "-" + Month + "-" + Day;
                if(AP.equals("오전 ") == true){
                    if (Integer.valueOf(selectedhour) <= 9) {
                        Time = "0" + selectedhour + ":00:00";
                    }else{
                        Time = selectedhour + ":00:00";
                    }
                }else{
                    if(Integer.valueOf(selectedhour)==12){
                        Time = selectedhour + ":00:00";
                    }else {
                        Time = String.valueOf(Integer.valueOf(selectedhour) + 12) + ":00:00";
                    }
                }
                insertToDatabases(Name, Place, Date, Time, Person);
            }
        });

    }

    private void insertToDatabases(String Name, String Place, String Date, String Time, String Person) {
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
//                Toast toast = Toast.makeText(ReservationCheck.this, s, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
//                toast.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationCheck.this);
//                builder.setTitle("제목");
                if (s.equals("예약 등록 완료")) {
                    builder.setMessage("예약이 완료되었습니다.");
                } else{
                    builder.setMessage("예약을 실패하였습니다. 재시도 바랍니다.");
                }
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ReservationCheck.this, MyBookingCheck.class);
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }

        @Override
        protected String doInBackground(String... params) {
            try {
                String Name = (String) params[0];
                String Place = (String) params[1];
                String Date = (String) params[2];
                String Time = (String) params[3];
                String Person = (String) params[4];

                String link = urls+"Reservation.php";
                String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                data += "&" + URLEncoder.encode("Place", "UTF-8") + "=" + URLEncoder.encode(Place, "UTF-8");
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
        task.execute(Name, Place, Date, Time, Person);
    }
}