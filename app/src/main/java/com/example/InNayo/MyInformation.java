package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

public class MyInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        Button b_ok = findViewById(R.id.b_ok);
        b_ok.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getApplicationContext(), "개인정보가 수정되었습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();

        });
    }
}