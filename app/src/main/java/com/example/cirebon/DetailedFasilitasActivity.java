package com.example.cirebon;

import androidx.core.widget.NestedScrollView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cirebon.model.History;
import com.example.cirebon.model.Wisata;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.HashMap;
import java.util.Map;

public class DetailedFasilitasActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String TAG = "DetailedActivity";
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    GoogleMap mMap;
    Query query;
    String child, lokasi,child1;
    ImageView detail, iv_detailtiket;
    TextView nama, alamat, tiket, jam, rating, tv_detaildeskripsi1, tvkoorlat, tvkoorlong, tv_phone;
    JustifiedTextView deskripsi;
    String sNama, sGambar, sAlamat, sTiket, id, hari, phone;
    double dRating, dKoorlat, dKoorlong, dkorlat, dkorlong;
    NestedScrollView detaildeskripsi;
    Uri gmmIntentUri;
    Intent mapIntent;
    String korlat, korlong;
    String goolgeMap = "com.google.android.apps.maps";
    LinearLayout ll_tiket, ll_jam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_fasilitas);
        child1 = "all";
        child = getIntent().getStringExtra("child");
        child1 = getIntent().getStringExtra("child1");
        lokasi = getIntent().getStringExtra("lokasi");
        nama = findViewById(R.id.tv_detailnama);
        alamat = findViewById(R.id.tv_detailalamat);
        tiket = findViewById(R.id.tv_detailtiket);
        jam = findViewById(R.id.tv_detailjam);
        rating = findViewById(R.id.tv_detailrating);
        tv_phone = findViewById(R.id.tv_detailphone);
        tv_detaildeskripsi1 = findViewById(R.id.tv_detaildeskripsi1);
        deskripsi = findViewById(R.id.tv_detaildeskripsi2);
        detail = findViewById(R.id.iv_detailed);
        iv_detailtiket = findViewById(R.id.iv_detailtiket);
        detaildeskripsi = findViewById(R.id.sv_detaildeskripsi);
        tvkoorlat = findViewById(R.id.tvkoorlat);
        tvkoorlong = findViewById(R.id.tvkoorlong);
        ll_jam = findViewById(R.id.ll_jam);
        ll_tiket = findViewById(R.id.ll_tiket);
        //dKoorlat = Double.parseDouble(korlat);
        //dKoorlong = Double.parseDouble(korlong);
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(child).child(lokasi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    public void onStart() {
        super.onStart();
        final String userId = getUid();
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        sNama = data.getNama();
                        sAlamat = data.getAlamat();
                        sGambar = data.getGambar();
                        dRating = data.getRating();
                        nama.setText(sNama);
                        rating.setText(String.valueOf(dRating));
                        alamat.setText(sAlamat);
                        Picasso.get().load(sGambar).into(detail);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        dKoorlong = data.getKoorlong();
                        dKoorlat = data.getKoorlat();
                        LatLng sydney = new LatLng(dKoorlong, dKoorlat);
                        mMap.addMarker(new MarkerOptions().position(sydney).title(lokasi));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

    }
}