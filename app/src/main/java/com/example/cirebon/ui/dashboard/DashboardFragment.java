package com.example.cirebon.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cirebon.BaseActivity;
import com.example.cirebon.DetailedActivity;
import com.example.cirebon.DetailedFasilitasActivity;
import com.example.cirebon.FasilitasActivity;
import com.example.cirebon.R;
import com.example.cirebon.adapter.Adapter3;
import com.example.cirebon.model.History;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    Query query;
    Uri gmmIntentUri;
    Intent mapIntent;
    String goolgeMap = "com.google.android.apps.maps";
    LinearLayoutManager mLayoutManager; //for sorting
    Button btn_semua, btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_fasilitas;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final String userId = BaseActivity.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("history").child(userId);
        recyclerView = root.findViewById(R.id.recyclerview1);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        btn_semua = root.findViewById(R.id.btn_historysemua);
        btn_wisata = root.findViewById(R.id.btn_historywisata);
        btn_belanja = root.findViewById(R.id.btn_historybelanja);
        btn_kuliner = root.findViewById(R.id.btn_historykuliner);
        btn_fasilitas = root.findViewById(R.id.btn_historyfasilitas);
        btn_transportasi = root.findViewById(R.id.btn_historytransport);
        btn_semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
                btn_semua.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        btn_wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = FirebaseDatabase.getInstance().getReference().child("history").child(userId).orderByChild("id").startAt("tempatwisata").endAt("tempatwisata" + "\uf8ff");
                tampilan(query);
                btn_semua.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        btn_belanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = FirebaseDatabase.getInstance().getReference().child("history").child(userId).orderByChild("id").startAt("belanja").endAt("belanja" + "\uf8ff");
                tampilan(query);
                btn_semua.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        btn_kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = FirebaseDatabase.getInstance().getReference().child("history").child(userId).orderByChild("id").startAt("kuliner").endAt("kuliner" + "\uf8ff");
                tampilan(query);
                btn_semua.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        btn_transportasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = FirebaseDatabase.getInstance().getReference().child("history").child(userId).orderByChild("id").startAt("transportasi").endAt("transportasi" + "\uf8ff");
                tampilan(query);
                btn_semua.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        btn_fasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = FirebaseDatabase.getInstance().getReference().child("history").child(userId).orderByChild("id").startAt("fasilitas").endAt("fasilitas" + "\uf8ff");
                tampilan(query);
                btn_semua.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_belanja.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_kuliner.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_transportasi.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_fasilitas.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_wisata.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Wisata, Adapter3> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, Adapter3>(
                        Wisata.class,
                        R.layout.historycard,
                        Adapter3.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(Adapter3 viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getContext(), model.getNama(), model.getGambar(), model.getAlamat(),model.getId(),model.getRating());
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
                                    Intent intent = new Intent (getContext(), DetailedActivity.class);
                                    intent.putExtra("child", child);
                                    intent.putExtra("lokasi", mLokasi);
                                    intent.putExtra("child1", child);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent (getContext(), DetailedFasilitasActivity.class);
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
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(getContext(), "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void tampilan(Query query){
        FirebaseRecyclerAdapter<Wisata, Adapter3> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Wisata, Adapter3>(
                        Wisata.class,
                        R.layout.historycard,
                        Adapter3.class,
                        query
                ) {
                    @Override
                    protected void populateViewHolder(Adapter3 viewHolder, Wisata model, int i) {
                        viewHolder.setDetails(getContext(), model.getNama(), model.getGambar(), model.getAlamat(),model.getId(),model.getRating());
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
                                    Intent intent = new Intent (getContext(), DetailedActivity.class);
                                    intent.putExtra("child", child);
                                    intent.putExtra("lokasi", mLokasi);
                                    intent.putExtra("child1", child);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent (getContext(), DetailedFasilitasActivity.class);
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
}