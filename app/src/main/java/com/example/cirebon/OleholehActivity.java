package com.example.cirebon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class OleholehActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    Query query;
    ImageView imageView1, imageView2, imageView3, imageView4;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    LinearLayout btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_hospital;
    EditText editText1;
    String search, key;
    TextView textView1, textView2;
    Button btn_sentrabatik, btn_oleholeh, btn_mall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oleholeh);
        recyclerView = findViewById(R.id.recyclerview1);
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("belanja").child("batik");
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView1.setImageResource(R.drawable.populer_belanja1);
        imageView2.setImageResource(R.drawable.populer_belanja2);
        imageView3.setImageResource(R.drawable.populer_belanja3);
        imageView4.setImageResource(R.drawable.populer_belanja4);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView1.setText(R.string.belanja);
        textView2.setText(R.string.belanja_populer);
        editText1 = findViewById(R.id.editText1);
        btn_sentrabatik = findViewById(R.id.btn_sentrabatik);
        btn_oleholeh = findViewById(R.id.btn_oleholeh);
        btn_mall = findViewById(R.id.btn_mall);
        btn_sentrabatik.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
        btn_sentrabatik.setTextColor(getResources().getColor(R.color.colorWhite));
        btn_sentrabatik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "batik";
                btn_sentrabatik.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
                btn_sentrabatik.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_mall.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_mall.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_oleholeh.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_oleholeh.setTextColor(getResources().getColor(R.color.colorBlack));
                tampilan(key);
            }
        });
        btn_oleholeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "oleholeh";
                btn_sentrabatik.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_sentrabatik.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_mall.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_mall.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_oleholeh.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
                btn_oleholeh.setTextColor(getResources().getColor(R.color.colorWhite));
                tampilan(key);
            }
        });
        btn_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "mall";
                btn_sentrabatik.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_sentrabatik.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_mall.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorAccent));
                btn_mall.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_oleholeh.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorButton));
                btn_oleholeh.setTextColor(getResources().getColor(R.color.colorBlack));
                tampilan(key);
            }
        });

        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText1.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    query = FirebaseDatabase.getInstance().getReference().child("data").child("belanja").child("all").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
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
                                            String child = "belanja/all";
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
                                String child = "belanja/all";
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
    }
    public void back(View view){
        onBackPressed();
    }
    public void populer1(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "belanja";
        String lokasi = "batik/Batik Nofa";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer2(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "belanja";
        String lokasi = "mall/Grage City Mall";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer3(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "belanja";
        String lokasi = "mall/CSB (Cirebon Super Block)";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void populer4(View view){
        Intent intent = new Intent(this, DetailedActivity.class);
        String child = "belanja";
        String lokasi = "batik/BT Batik Trusmi";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void tampilan(String child){
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("belanja").child(child);
        final String child1 = child;
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
                                String child = "belanja/all";
                                TextView koorlong = view.findViewById(R.id.tvkoorlong);
                                TextView koorlat = view.findViewById(R.id.tvkoorlat);
                                TextView lokasi = view.findViewById(R.id.tv_cardnama);
                                String mLokasi = lokasi.getText().toString();
                                String sKoorlong = koorlong.getText().toString();
                                String sKoorlat = koorlat.getText().toString();
                                Intent intent = new Intent (getApplicationContext(), DetailedActivity.class);
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