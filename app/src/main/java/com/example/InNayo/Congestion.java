package com.example.InNayo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;


public class Congestion extends FragmentActivity implements OnMapReadyCallback {
    Button congestion_bt4, congestion_bt5, congestion_bt6, congestion_bt7, congestion_bt8;

//    private BluetoothSPP bt;

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker1 = new Marker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congestion);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        congestion_bt4 = (Button)findViewById(R.id.congestion_bt4);
        congestion_bt5 = (Button)findViewById(R.id.congestion_bt5);
        congestion_bt6 = (Button)findViewById(R.id.congestion_bt6);
        congestion_bt7 = (Button)findViewById(R.id.congestion_bt7);
        congestion_bt8 = (Button)findViewById(R.id.congestion_bt8);

        congestion_bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMarker(marker1, 37.55830092764167, 127.05047158233968, R.drawable.yellow, 0);

                marker1.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay)
                    {
                        Toast.makeText(getApplication(), "도서관 인원 __/150 명 입니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

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

//        bt = new BluetoothSPP(this); //Initializing
//
//        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
//            Toast.makeText(getApplicationContext()
//                    , "Bluetooth is not available"
//                    , Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
//            TextView Serial = findViewById(R.id.Serial);
//            public void onDataReceived(byte[] data, String message) {
//                Serial.setText(message );
//            }
//        });
//
//        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
//            public void onDeviceConnected(String name, String address) {
//                Toast.makeText(getApplicationContext()
//                        , "Connected to " + name + "\n" + address
//                        , Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceDisconnected() { //연결해제
//                Toast.makeText(getApplicationContext()
//                        , "Connection lost", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceConnectionFailed() { //연결실패
//                Toast.makeText(getApplicationContext()
//                        , "Unable to connect", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
//                    bt.disconnect();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
//                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
//                }
//            }
//        });
    }

    private void setMarker(Marker marker, double lat, double lng, int color, int i) {
        //원근감 표시
        marker.setIconPerspectiveEnabled(true);
        //아이콘 지정
        marker.setIcon(OverlayImage.fromResource(color));
        //마커의 투명도
        marker.setAlpha(1);
        //마커 크기
        marker.setWidth(80);
        marker.setHeight(100);
        //마커 위치
        marker.setPosition(new LatLng(lat, lng));
        //마커 우선순위
        marker.setZIndex(i);
        //마커 표시
        marker.setMap(naverMap);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        //배경 지도 선택
        naverMap.setMapType(NaverMap.MapType.Navi);
        //건물 표시
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);
        //위치 및 각도 조정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.55834853256298, 127.04990932947216),   // 위치 지정
                14,                                     // 줌 레벨
                0,                                       // 기울임 각도
                0                                     // 방향
        );
        naverMap.setCameraPosition(cameraPosition);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
//    public void onDestroy() {
//        super.onDestroy();
//        bt.stopService(); //블루투스 중지
//    }
//
//    public void onStart() {
//        super.onStart();
//        if (!bt.isBluetoothEnabled()) { //
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
//        } else {
//            if (!bt.isServiceAvailable()) {
//                bt.setupService();
//                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
//                setup();
//            }
//        }
//    }
//
//    public void setup() {
//        Button btnSend = findViewById(R.id.btnSend); //데이터 전송
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                bt.send("Text", true);
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
//            if (resultCode == Activity.RESULT_OK)
//                bt.connect(data);
//        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
//            if (resultCode == Activity.RESULT_OK) {
//                bt.setupService();
//                bt.startService(BluetoothState.DEVICE_OTHER);
//                setup();
//            } else {
//                Toast.makeText(getApplicationContext()
//                        , "Bluetooth was not enabled."
//                        , Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }

}