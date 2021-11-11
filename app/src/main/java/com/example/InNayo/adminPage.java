package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class adminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        class Item {

            int no;
            String name;
            String msg;
            String imgPath;
            String date;

            public Item() {
            }

            public Item(int no, String name, String msg, String imgPath, String date) {
                this.no = no;
                this.name = name;
                this.msg = msg;
                this.imgPath = imgPath;
                this.date = date;
            }

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}