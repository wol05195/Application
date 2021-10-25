package com.example.InNayo;

import android.Manifest;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.apache.poi.ss.formula.functions.Na;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;


public class Congestion extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private MapView mapView;
    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    Button congestion_bt5, congestion_bt6, congestion_bt7, congestion_bt8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congestion);

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

//        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {
//            mapFragment = MapFragment.newInstance();
//            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
//        }

//        mapFragment.getMapAsync(this);


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
    }

//    @Override
//    public void onMapReady(@NonNull NaverMap naverMap) {
//        this.naverMap = naverMap;
//        naverMap.setLocationSource(locationSource);
//        naverMap.getUiSettings().setLocationButtonEnabled(true);
//        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);



//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(37.5670135, 126.9783740));
//        marker.setMap(naverMap);
//        marker.setWidth(80);
//        marker.setHeight(100);
//        marker.setIcon(OverlayImage.fromResource(R.drawable.red));
//        marker.setCaptionText("한양여자대학교");
//        marker.setCaptionRequestedWidth(200);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//        naverMap.getUiSettings().setLocationButtonEnabled(true);
//        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
//
        Marker marker = new Marker();
//        marker.setPosition(new LatLng(37.5670135, 126.9783740)); //시청
        marker.setPosition(new LatLng(37.55834853256298, 127.04990932947216));

        marker.setMap(naverMap);
        marker.setWidth(80);
        marker.setHeight(100);
        marker.setIcon(OverlayImage.fromResource(R.drawable.red));
        marker.setCaptionText("한양여자대학교");
        marker.setCaptionRequestedWidth(200);



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}