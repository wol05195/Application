package com.example.InNayo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class NonMemberLogin extends Fragment {
    EditText non_member_login_et1, non_member_login_et2;
    Button non_member_login_bt;
    TextView non_member_login_tv;
    int indexfname, indexbname, indexpass;
    ArrayList<String> nonmember;
    Context nonmemberlogincontext;
    String value = "";
    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_non_member_login,container,false);
        nonmember = new ArrayList<>();

        nonmemberlogincontext = container.getContext();

        checkSelfPermission();
        readExcel();

        pref = nonmemberlogincontext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        non_member_login_et1= (EditText)rootview.findViewById(R.id.non_member_login_et1);
        non_member_login_et2= (EditText)rootview.findViewById(R.id.non_member_login_et2);
        non_member_login_bt = (Button)rootview.findViewById(R.id.non_member_login_bt);
        non_member_login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexfname = nonmember.indexOf(non_member_login_et1.getText().toString());
                indexbname = nonmember.lastIndexOf(non_member_login_et1.getText().toString());
                indexpass = nonmember.indexOf(non_member_login_et2.getText().toString());

                if(indexfname != -1 && indexpass != -1){
                    if(indexfname <indexbname){
                        if(non_member_login_et2.getText().toString().equals(nonmember.get(indexbname+2)) == true){
                            Toast toast = Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                            toast.show();
                            editor.putString("logined_name", non_member_login_et1.getText().toString());
                            editor.apply();
                            ((Login)getActivity()).refresh();
                        }else{
                            if(non_member_login_et2.getText().toString().equals(nonmember.get(indexfname+2)) == true){
                                Toast toast = Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                                toast.show();
                                editor.putString("logined_name", non_member_login_et1.getText().toString());
                                editor.apply();
                                ((Login)getActivity()).refresh();
                            }else{
                                Toast.makeText(getContext(), "errorsection 1", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if(indexfname >= indexbname){
                        if(non_member_login_et2.getText().toString().equals(nonmember.get(indexfname+2)) == true){
                            Toast toast = Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                            toast.show();
                            editor.putString("logined_name", non_member_login_et1.getText().toString());
                            editor.apply();
                            ((Login)getActivity()).refresh();
                        }else{
                            Toast.makeText(getContext(), "errorsection 2", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "errorsection 3", Toast.LENGTH_SHORT).show();
                    }

                }else if(indexfname == -1){
                    Toast toast = Toast.makeText(getContext(), "이름을 다시 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                    toast.show();
                }else if(indexpass == -1){
                    Toast toast = Toast.makeText(getContext(), "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getContext(), "오류 발생하였습니다. 문의바랍니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
                    toast.show();
                }
            }
        });
        non_member_login_tv = (TextView) rootview.findViewById(R.id.non_member_login_tv);
        non_member_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Signup.class);
                startActivity(intent);
            }
        });
        return rootview;
    }

    private void readExcel() {
        File excelFilePath = new File(getContext().getExternalFilesDir(null), "NonMember.xls");

        try {
            FileInputStream inputStream = new FileInputStream(excelFilePath);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고 해당 타입에 맞게 가져온다.
                        case NUMERIC:
                            value = cell.getNumericCellValue() + "";
                            break;
                        case STRING:
                            value = cell.getStringCellValue() + "";
                            break;
                        case BLANK:
                            value = cell.getBooleanCellValue() + "";
                            break;
                        case ERROR:
                            value = cell.getErrorCellValue() + "";
                            break;
                    }

                    if(nextRow.getRowNum()>=2){
                        nonmember.add(value);
                    }else{
                    }
                }
            }
            workbook.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length; for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity","권한 허용 : " + permissions[i]); }
            }
        }
    }
    public void checkSelfPermission() {
        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "), 1);
        } else {
            // 모두 허용 상태
//            Toast.makeText(getContext(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }
}