package com.example.cirebon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cirebon.adapter.Adapter;
import com.example.cirebon.adapter.WisataAdapter;
import com.example.cirebon.model.History;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class TransportasiActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    Query query;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    LinearLayout btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_hospital;
    EditText editText1;
    String search;
    ImageView imageView1, imageView2, imageView3, imageView4;
    Uri gmmIntentUri;
    Intent mapIntent;
    String goolgeMap = "com.google.android.apps.maps";
    GoogleMap mMap;
    Context context;
    Resources resources;
    TextView textView1, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportasi);
        recyclerView = findViewById(R.id.recyclerview1);
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("transportasi");
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView1.setImageResource(R.drawable.populer_transportasi1);
        imageView2.setImageResource(R.drawable.populer_transportasi2);
        imageView3.setImageResource(R.drawable.populer_transportasi3);
        imageView4.setImageResource(R.drawable.populer_transportasi4);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView1.setText(R.string.transportasi);
        textView2.setText(R.string.transportasi_populer);
        editText1 = findViewById(R.id.editText1);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(final View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText1.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    query = FirebaseDatabase.getInstance().getReference().child("data").child("transportasi").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
                    FirebaseRecyclerAdapter<Wisata, Adapter> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<Wisata, Adapter>(
                                    Wisata.class,
                                    R.layout.cardview2,
                                    Adapter.class,
                                    query
                            ) {
                                @Override
                                protected void populateViewHolder(Adapter viewHolder, Wisata model, int i) {
                                    viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(), model.getTiket(), model.getRating(), model.getKoorlat(), model.getKoorlong());
                                    final String lokasi = model.getNama();
                                    final String child = "transportasi";
                                    final double dkoorlat = model.getKoorlat();
                                    final double dkoorlong = model.getKoorlong();
                                    final String sAlamat = model.getAlamat();
                                    final double dRating = model.getRating();
                                    final String sGambar = model.getGambar();
                                    viewHolder.navigasi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            navigasi(dkoorlat, dkoorlong, lokasi);
                                            writeNewPost(getUid(), lokasi, sAlamat,dRating, child, sGambar);
                                        }
                                    });
                                }
                            };
                    recyclerView.setAdapter(firebaseRecyclerAdapter);
                    return true;
                }
                return false;
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Wisata, Adapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, Adapter>(
                        Wisata.class,
                        R.layout.cardview2,
                        Adapter.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(Adapter viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(), model.getTiket(), model.getRating(), model.getKoorlat(), model.getKoorlong());
                        final String child = "transportasi";
                        final String lokasi = model.getNama();
                        final double dkoorlat = model.getKoorlat();
                        final double dkoorlong = model.getKoorlong();
                        final String sAlamat = model.getAlamat();
                        final double dRating = model.getRating();
                        final String sGambar = model.getGambar();
                        viewHolder.navigasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigasi(dkoorlat, dkoorlong, lokasi);
                                writeNewPost(getUid(), lokasi, sAlamat,dRating, child, sGambar);
                            }
                        });
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }public void back(View view){
        onBackPressed();
    }
    public void populer1(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "transportasi";
        String lokasi = "Stasiun Kejaksan";
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(child).child(lokasi);
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        String lokasi = data.getNama();

                        double dkoorlat = data.getKoorlat();
                        double dkoorlong = data.getKoorlong();
                        navigasi( dkoorlat, dkoorlong, lokasi);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    public void populer2(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "transportasi";
        String lokasi = "Bandar Udara Internasional Kertajati";
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(child).child(lokasi);
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        String lokasi = data.getNama();

                        double dkoorlat = data.getKoorlat();
                        double dkoorlong = data.getKoorlong();
                        navigasi( dkoorlat, dkoorlong, lokasi);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    public void populer3(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "transportasi";
        String lokasi = "Pelabuhan Cirebon";
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(child).child(lokasi);
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        String lokasi = data.getNama();

                        double dkoorlat = data.getKoorlat();
                        double dkoorlong = data.getKoorlong();
                        navigasi( dkoorlat, dkoorlong, lokasi);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    public void populer4(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "transportasi";
        String lokasi = "Stasiun Parujakan";
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child(child).child(lokasi);
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Wisata data = dataSnapshot.getValue(Wisata.class);
                        String lokasi = data.getNama();

                        double dkoorlat = data.getKoorlat();
                        double dkoorlong = data.getKoorlong();
                        navigasi( dkoorlat, dkoorlong, lokasi);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    private void navigasi(double koorlong, double koorlat, String lokasi) {
        gmmIntentUri = Uri.parse("geo:"+ koorlat + "," +koorlong+"?z=14&q=" + lokasi);
        // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
        mapIntent.setPackage(goolgeMap);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(TransportasiActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }
    private void writeNewPost(String userId, String nama, String alamat, double rating, String id, String gambar) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("history").push().getKey();
        History history = new History(userId, nama, alamat, rating, id, gambar);
        Map<String, Object> postValues = history.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/history/" + userId + "/" +  key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
}