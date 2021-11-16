package com.example.InNayo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import org.apache.poi.ss.formula.functions.Na;

import java.security.Permission;


public class Congestion extends FragmentActivity implements OnMapReadyCallback {

    Button congestion_bt5, congestion_bt6, congestion_bt7, congestion_bt8;

    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congestion);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
//
        congestion_bt5 = (Button)findViewById(R.id.congestion_bt5);
        congestion_bt6 = (Button)findViewById(R.id.congestion_bt6);
        congestion_bt7 = (Button)findViewById(R.id.congestion_bt7);
        congestion_bt8 = (Button)findViewById(R.id.congestion_bt8);

        congestion_bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (congestion_bt6.getVisibility() == View.INVISIBLE) {
                    congestion_bt8.setVisibility(View.VISIBLE);
                    congestion_bt7.setVisibility(View.VISIBLE);
                    congestion_bt6.setVisibility(View.VISIBLE);
                }else {
                    congestion_bt6.setVisibility(View.INVISIBLE);
                    congestion_bt7.setVisibility(View.INVISIBLE);
                    congestion_bt8.setVisibility(View.INVISIBLE);
                }
            }
        });

        congestion_bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congestion_bt7.setVisibility(View.GONE);
                congestion_bt8.setVisibility(View.GONE);
            }
        });
        congestion_bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congestion_bt6.setVisibility(View.GONE);
                congestion_bt8.setVisibility(View.GONE);
            }
        });
        congestion_bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congestion_bt6.setVisibility(View.GONE);
                congestion_bt7.setVisibility(View.GONE);
            }
        });

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            TextView Serial = findViewById(R.id.Serial);
            public void onDataReceived(byte[] data, String message) {
                Serial.setText(message );
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.55834853256298, 127.04990932947216));
        marker.setMap(naverMap);
        marker.setWidth(80);
        marker.setHeight(100);
        marker.setIcon(OverlayImage.fromResource(R.drawable.red));
        marker.setCaptionText("한양여자대학교");
        marker.setCaptionRequestedWidth(200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {

    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    public void setup() {
        Button btnSend = findViewById(R.id.btnSend); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("Text", true);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
