package com.example.cirebon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cirebon.adapter.WisataAdapter;
import com.example.cirebon.model.History;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class TempatWisataActivity extends BaseActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_wisata);
        recyclerView = findViewById(R.id.recyclerview1);
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("tempatwisata");
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        editText1 = findViewById(R.id.editText1);
        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText1.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    query = FirebaseDatabase.getInstance().getReference().child("data").child("tempatwisata").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
                    FirebaseRecyclerAdapter<Wisata, WisataAdapter> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<Wisata, WisataAdapter>(
                                    Wisata.class,
                                    R.layout.wisatacard,
                                    WisataAdapter.class,
                                    query
                            ) {
                                @Override
                                protected void populateViewHolder(WisataAdapter viewHolder, Wisata model, int i) {
                                    viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(), model.getTiket(), model.getRating(), model.getKoorlat(), model.getKoorlong());
                                }

                                @Override
                                public WisataAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

                                    final WisataAdapter viewHolder = super.onCreateViewHolder(parent, viewType);
                                    viewHolder.setOnClickListener(new WisataAdapter.ClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            String child = "tempatwisata";
                                            TextView koorlong = view.findViewById(R.id.tvkoorlong);
                                            TextView koorlat = view.findViewById(R.id.tvkoorlat);
                                            TextView lokasi = view.findViewById(R.id.tv_cardnama);
                                            String mLokasi = lokasi.getText().toString();
                                            String sKoorlong = koorlong.getText().toString();
                                            String sKoorlat = koorlat.getText().toString();
                                            Intent intent = new Intent (getApplicationContext(), DetailedActivity.class);
                                            intent.putExtra("koorlat",sKoorlat);
                                            intent.putExtra("koorlong",sKoorlong);
                                            intent.putExtra("child", child);
                                            intent.putExtra("lokasi", mLokasi);
                                            intent.putExtra("child1", child);
                                            startActivity(intent);
                                            TextView rating = view.findViewById(R.id.tv_cardrating);
                                            TextView alamat = view.findViewById(R.id.tv_cardalamat);
                                            String sAlamat = alamat.getText().toString();
                                            double dRating = Double.parseDouble(rating.getText().toString());
                                            TextView gambar = view.findViewById(R.id.tvgambar);
                                            String sGambar = gambar.getText().toString();
                                            writeNewPost(getUid(), mLokasi, sAlamat,dRating, child, sGambar);
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
        final String userId = getUid();
        FirebaseRecyclerAdapter<Wisata, WisataAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, WisataAdapter>(
                        Wisata.class,
                        R.layout.wisatacard,
                        WisataAdapter.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(WisataAdapter viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(), model.getTiket(), model.getRating(), model.getKoorlat(), model.getKoorlong());
                    }

                    @Override
                    public WisataAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

                        final WisataAdapter viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new WisataAdapter.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String child = "tempatwisata";
                                TextView lokasi = view.findViewById(R.id.tv_cardnama);
                                TextView rating = view.findViewById(R.id.tv_cardrating);
                                TextView alamat = view.findViewById(R.id.tv_cardalamat);
                                TextView gambar = view.findViewById(R.id.tvgambar);
                                String sGambar = gambar.getText().toString();
                                String sAlamat = alamat.getText().toString();
                                double dRating = Double.parseDouble(rating.getText().toString());
                                String mLokasi = lokasi.getText().toString();
                                Intent intent = new Intent (getApplicationContext(), DetailedActivity.class);
                                intent.putExtra("child", child);
                                intent.putExtra("lokasi", mLokasi);
                                intent.putExtra("child1", child);
                                startActivity(intent);
                                writeNewPost(userId, mLokasi, sAlamat,dRating, child, sGambar);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public void back(View view){
        onBackPressed();
    }
    public void populer1(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Keraton Kesepuhan";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer2(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Cirebon Waterland Taman Ade Irma Suryani";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer4(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Masjid Agung Sang Cipta";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer3(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Taman Sari Gua Sunyaragi";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
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