package com.example.cirebon.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cirebon.DetailedActivity;
import com.example.cirebon.FasilitasActivity;
import com.example.cirebon.KulinerActivity;
import com.example.cirebon.MapsActivity;
import com.example.cirebon.OleholehActivity;
import com.example.cirebon.R;
import com.example.cirebon.SearchActivity;
import com.example.cirebon.TempatWisataActivity;
import com.example.cirebon.TransportasiActivity;
import com.example.cirebon.adapter.WisataAdapter;
import com.example.cirebon.model.Wisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeFragment extends Fragment {
    LinearLayout btn_wisata, btn_belanja, btn_kuliner, btn_transportasi, btn_hospital;
    private HomeViewModel homeViewModel;
    CarouselView carouselView;
    EditText editText;
    Uri gmmIntentUri;
    Intent mapIntent;
    String search;
    String goolgeMap = "com.google.android.apps.maps";
    int[] sampleImages = {R.drawable.carousel1, R.drawable.carousel2,R.drawable.caropusel3};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = root.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        btn_wisata = root.findViewById(R.id.btn_wisata);
        btn_belanja = root.findViewById(R.id.btn_belanja);
        btn_hospital = root.findViewById(R.id.btn_hospital);
        btn_kuliner = root.findViewById(R.id.btn_kuliner);
        btn_transportasi = root.findViewById(R.id.btn_transportasi);
        btn_wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TempatWisataActivity.class);
                startActivity(intent);
            }
        });
        btn_belanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OleholehActivity.class);
                startActivity(intent);
            }
        });
        btn_transportasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), TransportasiActivity.class);
                startActivity(intent);
            }
        });
        btn_kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), KulinerActivity.class);
                startActivity(intent);
            }
        });
        btn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), FasilitasActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.mesjid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesjid();
            }
        });
        root.findViewById(R.id.hotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotel();
            }
        });
        root.findViewById(R.id.goa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goa();
            }
        });
        root.findViewById(R.id.keraton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keraton();
            }
        });
        root.findViewById(R.id.stasiun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stasiun();
            }
        });
        root.findViewById(R.id.pantai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantai();
            }
        });
        editText = root.findViewById(R.id.editText1);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search = editText.getText().toString();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra("search", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        return root;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    public void mesjid(){
        String lokasi = "Masjid Shiratal Mustaqim, Cirebon";
        navigasi(0, 0, lokasi);
    }
    public void stasiun(){
        String lokasi = "Stasiun Parujakan";
        navigasi(0, 0, lokasi);
    }
    public void pantai(){
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Pantai Kejawanan";
        intent.putExtra("child", child);
        intent.putExtra("child1", child);
        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
    }
    public void goa(){
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Taman Sari Gua Sunyaragi";
        intent.putExtra("child", child);
        intent.putExtra("child1", child);
        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
    }
    public void keraton(){
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        String child = "tempatwisata";
        String lokasi = "Keraton Kesepuhan";
        intent.putExtra("child", child);
        intent.putExtra("child1", child);

        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
    }
    public void hotel(){
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        String child = "fasilitas";
        String child1 = "hotel";
        intent.putExtra("child1", child1);
        String lokasi = "hotel/Swiss-Belhotel Cirebon";
        intent.putExtra("child", child);
        intent.putExtra("lokasi", lokasi);
        startActivity(intent);
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
            Toast.makeText(getActivity(), "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }
}