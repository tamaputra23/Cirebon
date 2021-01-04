package com.example.cirebon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cirebon.adapter.Adapter;
import com.example.cirebon.adapter.Adapter2;
import com.example.cirebon.adapter.WisataAdapter;
import com.example.cirebon.model.History;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FasilitasActivity extends BaseActivity {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    Query query;
    Uri gmmIntentUri;
    Intent mapIntent;
    String goolgeMap = "com.google.android.apps.maps";
    ImageView imageView1, imageView2, imageView3, imageView4;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    LinearLayout btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_hospital, btn_more, btn_mesjid, btn_hotel, btn_rs;
    LinearLayout ll_populerresto, ll_populerhotel, ll_populernongkrong;
    EditText editText1;
    String search, key, fasilitas;
    TextView textView1, textView2;
    Button btn_sentrabatik, btn_oleholeh, btn_mall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);
        fasilitas = "masjid";
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("fasilitas").child(fasilitas);
        recyclerView = findViewById(R.id.recyclerview1);
        ll_populerhotel = findViewById(R.id.ll_popularhotel);
        ll_populernongkrong = findViewById(R.id.ll_popularnongkrong);
        ll_populerresto = findViewById(R.id.ll_popularresto);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        editText1 = findViewById(R.id.editText1);
        btn_rs = findViewById(R.id.btn_rs);
        btn_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fasilitas = "rs";
                tampilan(fasilitas);
                ll_populerhotel.setVisibility(View.GONE);
                ll_populerresto.setVisibility(View.GONE);
                ll_populernongkrong.setVisibility(View.GONE);
            }
        });
        btn_mesjid = findViewById(R.id.btn_mesjid);
        btn_mesjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fasilitas = "masjid";
                tampilan2(fasilitas);
                ll_populerhotel.setVisibility(View.GONE);
                ll_populerresto.setVisibility(View.GONE);
                ll_populernongkrong.setVisibility(View.GONE);
            }
        });
        btn_hotel = findViewById(R.id.btn_hotel);
        btn_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fasilitas = "hotel";
                tampilan(fasilitas);
                ll_populerhotel.setVisibility(View.VISIBLE);
                ll_populerresto.setVisibility(View.GONE);
                ll_populernongkrong.setVisibility(View.GONE);
            }
        });
        btn_more = findViewById(R.id.btn_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ViewDialog2 alert = new ViewDialog2();
                alert.showDialog();
            }
        });

        editText1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText1.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    query = FirebaseDatabase.getInstance().getReference().child("data").child("fasilitas").child("all").orderByChild("nama").startAt(search).endAt(search + "\uf8ff");
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
                                            String child = "fasilitas/all";
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

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
                        final String lokasi = model.getNama();
                        final String child = "fasilitas/masjid";
                        final String sAlamat = model.getAlamat();
                        final double dRating = 0.0;
                        final String sGambar = model.getGambar();
                        final double dkoorlat = model.getKoorlat();
                        final double dkoorlong = model.getKoorlong();
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
    }
    public void back(View view){
        onBackPressed();
    }
    public void kesepuhan(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/restoran";
        String lokasi = "Nasi Jamblang Ibu Nur";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void waterland(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/restoran";
        String lokasi = "Urban Chicken";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void tamansari(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/restoran";
        String lokasi = "Restoran Marina";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void mesjidagung(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/restoran";
        String lokasi = "RM Padang Sederhana";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void kesepuhan1(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas";
        String child1 = "hotel";
        intent.putExtra("child1", child1);
        String lokasi = "hotel/Hotel Aston Cirebon";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void waterland1(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas";
        String child1 = "hotel";
        intent.putExtra("child1", child1);
        String lokasi = "hotel/Swiss-Belhotel Cirebon";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void tamansari1(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas";
        String child1 = "hotel";
        intent.putExtra("child1", child1);
        String lokasi = "hotel/The Luxton Cirebon Hotel";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
    }
    public void mesjidagung1(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas";
        String child1 = "hotel";
        intent.putExtra("child1", child1);
        String lokasi = "hotel/BATIQA Hotel";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
    }
    public void kesepuhan2(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/nongkrong";
        String lokasi = "nongkrong/Olive Bistro";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void waterland2(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/nongkrong";
        String lokasi = "My Story Cafe & Bistro";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void tamansari2(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/nongkrong";
        String lokasi = "Teras Kopi Cirebon";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public void mesjidagung2(View view){
        Intent intent = new Intent(this, DetailedFasilitasActivity.class);
        String child = "fasilitas/nongkrong";
        String lokasi = "Lambada Cafe";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        intent.putExtra("child1", child);
        startActivity(intent);
    }
    public class ViewDialog2 extends BaseActivity {
        LinearLayout btn_mesjid, btn_hotel, btn_rs, btn_resto, btn_nongrong, btn_spbu, btn_bengkel;
        public void showDialog() {
            final BottomSheetDialog dialog = new BottomSheetDialog(FasilitasActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            dialog.setContentView(R.layout.bottomdialog);
            btn_rs = dialog.findViewById(R.id.btn_rs);
            btn_rs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "rs";
                    tampilan(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            btn_mesjid = dialog.findViewById(R.id.btn_mesjid);
            btn_mesjid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "masjid";
                    tampilan2(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            btn_hotel = dialog.findViewById(R.id.btn_hotel);
            btn_hotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "hotel";
                    tampilan(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.VISIBLE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            btn_resto = dialog.findViewById(R.id.btn_resto);
            btn_resto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "restoran";
                    tampilan(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.VISIBLE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            btn_nongrong = dialog.findViewById(R.id.nongkrong);
            btn_nongrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "nongkrong";
                    tampilan(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.VISIBLE);
                }
            });
            btn_spbu = dialog.findViewById(R.id.btn_spbu);
            btn_spbu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "spbu";
                    tampilan2(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            btn_bengkel = dialog.findViewById(R.id.btn_bengkel);
            btn_bengkel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fasilitas = "bengkel";
                    tampilan2(fasilitas);
                    dialog.dismiss();
                    ll_populerhotel.setVisibility(View.GONE);
                    ll_populerresto.setVisibility(View.GONE);
                    ll_populernongkrong.setVisibility(View.GONE);
                }
            });
            dialog.show();
        }
    }
    public void tampilan(String child){
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("fasilitas").child(child);
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
                                String child = "fasilitas";
                                TextView rating = view.findViewById(R.id.tv_cardrating);
                                TextView alamat = view.findViewById(R.id.tv_cardalamat);
                                TextView gambar = view.findViewById(R.id.tvgambar);
                                String sGambar = gambar.getText().toString();
                                String sAlamat = alamat.getText().toString();
                                double dRating = Double.parseDouble(rating.getText().toString());
                                TextView koorlong = view.findViewById(R.id.tvkoorlong);
                                TextView koorlat = view.findViewById(R.id.tvkoorlat);
                                TextView lokasi = view.findViewById(R.id.tv_cardnama);
                                String mLokasi = child1 + "/"+lokasi.getText().toString();
                                String sKoorlong = koorlong.getText().toString();
                                String sKoorlat = koorlat.getText().toString();
                                Intent intent = new Intent (getApplicationContext(), DetailedFasilitasActivity.class);
                                intent.putExtra("child1", child1);
                                intent.putExtra("child", child);
                                intent.putExtra("lokasi", mLokasi);
                                startActivity(intent);
                                String child2 = "fasilitas/" +child1;
                                writeNewPost(getUid(), lokasi.getText().toString(), sAlamat,dRating, child2, sGambar);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public void tampilan2(String child){
        myRef = FirebaseDatabase.getInstance().getReference().child("data").child("fasilitas").child(child);
        final String child1 = child;
        FirebaseRecyclerAdapter<Wisata, Adapter2> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, Adapter2>(
                        Wisata.class,
                        R.layout.cardview,
                        Adapter2.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(Adapter2 viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getGambar(), model.getAlamat(), model.getTiket(), model.getRating(), model.getKoorlat(), model.getKoorlong());
                        final String lokasi = model.getNama();
                        final String child = "fasilitas/" + child1;
                        final double dkoorlat = model.getKoorlat();
                        final double dkoorlong = model.getKoorlong();
                        final String sAlamat = model.getAlamat();
                        final double dRating = 0.0;
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
            Toast.makeText(FasilitasActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
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