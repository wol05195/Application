package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    Button b_create, b_notice, b_view, b_show;
    EditText e_username, e_password, e_title, e_content;
    TextView t_show;
//    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new myDBHelper(this);

        b_create = (Button) findViewById(R.id.b_create);
        b_notice = (Button) findViewById(R.id.b_notice);
        b_view= (Button) findViewById(R.id.b_view);
        b_show= (Button) findViewById(R.id.b_show);

        e_username = (EditText) findViewById(R.id.e_username);
        e_password = (EditText) findViewById(R.id.e_password);
        e_title = (EditText) findViewById(R.id.e_title);
        e_content = (EditText) findViewById(R.id.e_content);

        t_show = (TextView) findViewById(R.id.t_show);

        b_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB=myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO member VALUES ('"+
                        e_username.getText().toString() + "' , "+ "'"+
                        e_password.getText().toString() +"');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        b_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB=myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO notice VALUES ('"+
                        e_title.getText().toString() + "' , "+ "'"+
                        e_content.getText().toString() + "' , " + "'"+
                        e_username.getText().toString() +"');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "내용이 입력되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                sqlDB.execSQL("CREATE VIEW pair AS SELECT password FROM member, notice WHERE member.username = notice.username;");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "뷰 생성", Toast.LENGTH_SHORT).show();
            }
        });

        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT password FROM pair;", null);

                String content= null;
                while (cursor.moveToNext()) {
                    content = cursor.getString(0);
                    t_show.setText(content);
                }
                cursor.close();
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "뷰 보여주기", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class myDBHelper extends SQLiteOpenHelper {
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS member");
            db.execSQL("DROP TABLE IF EXISTS notice");
            onCreate(db);
        }
        public myDBHelper(Context context){

            super(context, "testDB", null, 1);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE member(username char(15) PRIMARY KEY, password char(10));");
            db.execSQL("CREATE TABLE notice(title char(20) PRIMARY KEY, content char(15), username char(15) REFERENCES member (username) ON DELETE CASCADE);");
        }

    }
}