package com.example.InNayo;

import android.Manifest;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.InNayo.Reservation.urls;

public class NonMember extends Fragment {
    Button non_member_bt;
    EditText fname, ftel, fpw, nonmember_et4;
    String value="";
    ArrayList<String> nonmember;
    Context nonmembercontext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.activity_non_member,container,false);

        nonmembercontext = container.getContext();

        checkSelfPermission();
        readExcel();

        fname = (EditText)rootview.findViewById(R.id.nonmember_et1);
        ftel = (EditText)rootview.findViewById(R.id.nonmember_et2);
        fpw = (EditText)rootview.findViewById(R.id.nonmember_et3);
        nonmember_et4 = (EditText)rootview.findViewById(R.id.nonmember_et4);

        non_member_bt = (Button) rootview.findViewById(R.id.non_member_bt);
        non_member_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = fname.getText().toString();
                String Tel = ftel.getText().toString();
                String Password = fpw.getText().toString();

                if(fpw.getText().toString().equals(nonmember_et4.getText().toString()) == true){
                    if(new File(getContext().getExternalFilesDir(null), "NonMember.xls").exists()) {
                        updateExcel(Name, Tel, Password);
                    }else{
                        createExcel();
                        updateExcel(Name, Tel, Password);
                    }
                        insertToDatabase(Name, Tel);
                    Toast.makeText(getContext(), "비회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "비밀번호를 동일하게 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
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

            File xlsFile = new File(getContext().getExternalFilesDir(null),"NonMember.xls");
            try {
                FileOutputStream os = new FileOutputStream(xlsFile);
                workbook.write(os); // 외부 저장소에 엑셀 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateExcel(String Name, String Tel, String Password) {
        File excelFilePath = new File(getContext().getExternalFilesDir(null), "NonMember.xls");
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
            File xlsFile = new File(getContext().getExternalFilesDir(null), "NonMember.xls");
            FileOutputStream outputStream = new FileOutputStream(xlsFile);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
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

    private void insertToDatabase(String Name, String Tel) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(nonmembercontext, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
//                Toast toast = Toast.makeText(nonmembercontext, s, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
//                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String Name = (String) params[0];
                    String Tel = (String) params[1];

                    String link = urls+"Register_non.php";
                    String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Tel", "UTF-8") + "=" + URLEncoder.encode(Tel, "UTF-8");

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
        task.execute(Name, Tel);
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