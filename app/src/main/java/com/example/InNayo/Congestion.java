package com.example.InNayo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;


public class Congestion extends AppCompatActivity implements OnMapReadyCallback {


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


    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(37.55834853256298, 127.04990932947216));
        marker1.setMap(naverMap);
        marker1.setWidth(200);
        marker1.setHeight(200);
        marker1.setIcon(OverlayImage.fromResource(R.drawable.h));
        marker1.setCaptionText("한양여자대학교");
        marker1.setCaptionRequestedWidth(200);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.5606832475916, 127.047201183911));
        marker2.setMap(naverMap);
        marker2.setWidth(200);
        marker2.setHeight(200);
        marker2.setIcon(OverlayImage.fromResource(R.drawable.m));
        marker2.setCaptionText("미네스키친");
        marker2.setCaptionRequestedWidth(200);







    }
}