package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Booking extends AppCompatActivity {
    Button booking_button1;
    EditText booking_edit1, booking_edit2, booking_edit3;
    Intent intent;
    Calendar bookingCalendar = Calendar.getInstance();
    NumberPicker np;
    String state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        booking_button1 = (Button) findViewById(R.id.booking_button1);
        booking_button1.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Reservation.class);
            intent.putExtra("year", String.valueOf(bookingCalendar.get(Calendar.YEAR)));
            intent.putExtra("month", String.valueOf(bookingCalendar.get(Calendar.MONTH)+1));
            intent.putExtra("date", String.valueOf(bookingCalendar.get(Calendar.DATE)));
            intent.putExtra("time", String.valueOf(bookingCalendar.get(Calendar.HOUR)));
            intent.putExtra("ap", String.valueOf(state));
            intent.putExtra("people", String.valueOf(np.getValue()));
            startActivity(intent);
            finish();
        });

        booking_edit1 = (EditText)findViewById(R.id.booking_edit1);
        booking_edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });

        booking_edit2 = (EditText)findViewById(R.id.booking_edit2);
        booking_edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker();
            }
        });

        booking_edit3 = (EditText)findViewById(R.id.booking_edit3);
        booking_edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker();
            }
        });
    }

    private void DatePicker(){
        final DatePickerDialog DatePicker = new DatePickerDialog(Booking.this, R.style.DatePickerStyle,  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                bookingCalendar.set(Calendar.YEAR, year);
                bookingCalendar.set(Calendar.MONTH, month);
                bookingCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String Format = "yyyy년 M월 d일";    // 출력형식   2018/11/28
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.KOREA);
                booking_edit1.setText("날짜 : " + sdf.format(bookingCalendar.getTime()));
            }
        },bookingCalendar.get(Calendar.YEAR), bookingCalendar.get(Calendar.MONTH), bookingCalendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }

    private void TimePicker(){
        int hour = bookingCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = bookingCalendar.get(Calendar.MINUTE);

        final TimePickerDialog TimePicker = new TimePickerDialog(Booking.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                state = "AM";
                // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                if (selectedHour > 12) {
                    selectedHour -= 12;
                    state = "PM";
                }else if(selectedHour == 12){
                    selectedHour = 12;
                    state = "PM";
                }
                // EditText에 출력할 형식 지정
                booking_edit2.setText("시간 : "+ state + " " + selectedHour + "시 ");
//                intent.putExtra("time", selectedHour);
            }
        }, hour, minute/60, false); // true의 경우 24시간 형식의 TimePicker 출현
        TimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TimePicker.setTitle("시간을 선택해주세요");
        TimePicker.show();
    }

    private void NumberPicker() {
        final Dialog numDialog = new Dialog(this);
        numDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        numDialog.setContentView(R.layout.person);

        Button okkBtn = (Button) numDialog.findViewById(R.id.num_btn_ok);
        Button cancelBtn = (Button) numDialog.findViewById(R.id.num_btn_cancel);

        np = (NumberPicker) numDialog.findViewById(R.id.NumPicker);
        np.setMinValue(1);
        np.setMaxValue(6);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setWrapSelectorWheel(false);
        setDividerColor(np,R.color.white);
        np.setValue(1);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        okkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking_edit3.setText("인원수 : " + String.valueOf(np.getValue()) + "명");
                numDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numDialog.dismiss();
            }
        });

        numDialog.show();
    }

    private void setDividerColor(NumberPicker picker, int color){
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields){
            if (pf.getName().equals("mSelectionDivider")){
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}