package com.example.cirebon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cirebon.adapter.Adapter3;
import com.example.cirebon.adapter.WisataAdapter;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    Query query;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    LinearLayout btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_hospital;
    EditText editText1;
    Uri gmmIntentUri;
    Intent mapIntent;
    String goolgeMap = "com.google.android.apps.maps";

    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerview1);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        search = getIntent().getStringExtra("search");
        query = FirebaseDatabase.getInstance().getReference().child("data").child("all").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
        editText1 = findViewById(R.id.editText1);
        editText1.setText(search);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText1.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    query = FirebaseDatabase.getInstance().getReference().child("data").child("all").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
                    FirebaseRecyclerAdapter<Wisata, Adapter3> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<Wisata, Adapter3>(
                                    Wisata.class,
                                    R.layout.historycard,
                                    Adapter3.class,
                                    query
                            ) {
                                @Override
                                protected void populateViewHolder(Adapter3 viewHolder, Wisata model, int i) {
                                    viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(),model.getId(),model.getRating());
                                    final String lokasi = model.getNama();
                                    String id = model.getId();
                                    if (id.equals("transportasi") || id.equals("fasilitas/masjid") || id.equals("fasilitas/spbu") || id.equals("fasilitas/bengkel")){
                                        viewHolder.ll_rating.setVisibility(View.GONE);
                                        viewHolder.navigasi.setVisibility(View.VISIBLE);
                                    }
                                    viewHolder.navigasi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            navigasi(0, 0, lokasi);
                                        }
                                    });
                                }

                                @Override
                                public Adapter3 onCreateViewHolder(ViewGroup parent, int viewType) {

                                    final Adapter3 viewHolder = super.onCreateViewHolder(parent, viewType);
                                    viewHolder.setOnClickListener(new Adapter3.ClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            TextView mChild = view.findViewById(R.id.tv_historyid);
                                            LinearLayout ll_rating = view.findViewById(R.id.ll_rating);
                                            Button btn_navigasi = view.findViewById(R.id.btn_historynavigasi);
                                            String child = mChild.getText().toString();
                                            TextView lokasi = view.findViewById(R.id.tv_historytittle);
                                            String mLokasi = lokasi.getText().toString();
                                            if (child.equals("tempatwisata") || child.equals("kuliner") || child.equals("belanja/all")){
                                                Intent intent = new Intent (getApplicationContext(), DetailedActivity.class);
                                                intent.putExtra("child", child);
                                                intent.putExtra("lokasi", mLokasi);
                                                intent.putExtra("child1", child);
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent (getApplicationContext(), DetailedFasilitasActivity.class);
                                                intent.putExtra("child", child);
                                                intent.putExtra("lokasi", mLokasi);
                                                intent.putExtra("child1", child);
                                                startActivity(intent);
                                            }
                                            if (child.equals("transportasi") || child.equals("fasilitas/masjid") || child.equals("fasilitas/spbu") || child.equals("fasilitas/bengkel")){
                                                ll_rating.setVisibility(View.GONE);
                                                btn_navigasi.setVisibility(View.VISIBLE);
                                            }

                                        }

                                    });
                                    return viewHolder;
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
        FirebaseRecyclerAdapter<Wisata, Adapter3> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, Adapter3>(
                        Wisata.class,
                        R.layout.historycard,
                        Adapter3.class,
                        query
                ) {
                    @Override
                    protected void populateViewHolder(Adapter3 viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(),model.getId(),model.getRating());
                        final String lokasi = model.getNama();
                        String id = model.getId();
                        if (id.equals("transportasi") || id.equals("fasilitas/masjid") || id.equals("fasilitas/spbu") || id.equals("fasilitas/bengkel")){
                            viewHolder.ll_rating.setVisibility(View.GONE);
                            viewHolder.navigasi.setVisibility(View.VISIBLE);
                        }
                        viewHolder.navigasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigasi(0, 0, lokasi);
                            }
                        });
                    }

                    @Override
                    public Adapter3 onCreateViewHolder(ViewGroup parent, int viewType) {

                        final Adapter3 viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new Adapter3.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mChild = view.findViewById(R.id.tv_historyid);
                                LinearLayout ll_rating = view.findViewById(R.id.ll_rating);
                                Button btn_navigasi = view.findViewById(R.id.btn_historynavigasi);
                                String child = mChild.getText().toString();
                                TextView lokasi = view.findViewById(R.id.tv_historytittle);
                                String mLokasi = lokasi.getText().toString();
                                if (child.equals("tempatwisata") || child.equals("kuliner") || child.equals("belanja/all")){
                                    Intent intent = new Intent (getApplicationContext(), DetailedActivity.class);
                                    intent.putExtra("child", child);
                                    intent.putExtra("lokasi", mLokasi);
                                    intent.putExtra("child1", child);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent (getApplicationContext(), DetailedFasilitasActivity.class);
                                    intent.putExtra("child", child);
                                    intent.putExtra("lokasi", mLokasi);
                                    intent.putExtra("child1", child);
                                    startActivity(intent);
                                }
                                if (child.equals("transportasi") || child.equals("fasilitas/masjid") || child.equals("fasilitas/spbu") || child.equals("fasilitas/bengkel")){
                                    ll_rating.setVisibility(View.GONE);
                                    btn_navigasi.setVisibility(View.VISIBLE);
                                }

                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
            Toast.makeText(getApplicationContext(), "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void back(View view){onBackPressed();}
}