package com.example.InNayo;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NonMember extends Fragment {
    Button non_member_bt;
    EditText fname, ftel, fpw, nonmember_et4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_non_member,container,false);

        checkSelfPermission();
        createExcel();

        fname = (EditText)rootview.findViewById(R.id.nonmember_et1);
        ftel = (EditText)rootview.findViewById(R.id.nonmember_et2);
        fpw = (EditText)rootview.findViewById(R.id.nonmember_et3);

        non_member_bt = (Button) rootview.findViewById(R.id.non_member_bt);
        non_member_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = fname.getText().toString();
                String Tel = ftel.getText().toString();
                String Password = fpw.getText().toString();

                updateExcel(Name, Password, Tel);
            }
        });
        return rootview;
    }
    private void createExcel() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(); // 새로운 시트 생성

        Object[][] bookData = {
                {"이름", "전화번호", "비밀번호"}
        };
        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

            File xlsFile = new File(getContext().getExternalFilesDir(null),"JavaBooks.xls");
            try {
                FileOutputStream os = new FileOutputStream(xlsFile);
                workbook.write(os); // 외부 저장소에 엑셀 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateExcel(String Name, String Tel, String Password) {
        File excelFilePath = new File(getContext().getExternalFilesDir(null), "JavaBooks.xls");
        try {
            FileInputStream inputStream = new FileInputStream(excelFilePath);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Object[][] bookData = {
                    {Name, Tel, Password},
            };

            int rowCount = sheet.getLastRowNum();

            for (Object[] aBook : bookData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount-1);

                for (Object field : aBook) {
                    cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }

            }

            inputStream.close();
            File xlsFile = new File(getContext().getExternalFilesDir(null), "JavaBooks.xls");
            FileOutputStream outputStream = new FileOutputStream(xlsFile);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length; for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
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
            Toast.makeText(getContext(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }
}