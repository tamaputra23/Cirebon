package com.example.cirebon.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cirebon.R;
import com.example.cirebon.model.Wisata;
import com.squareup.picasso.Picasso;

public class Adapter2 extends RecyclerView.ViewHolder {
    View mView;
    ImageView imageView;
    public Button navigasi;
    TextView tvnama, tvalamat, textrating, tvtiket, tvkoorlat, tvkoorlong;
    public Adapter2(View itemView) {
        super(itemView);
        mView = itemView;
        navigasi = itemView.findViewById(R.id.btn_navigasi);

    }
    public void setDetails(Context ctx, String nama, String gambar, String alamat, String tiket, double rating, double koorlat, double koorlong){
        imageView = mView.findViewById(R.id.iv_cardview);
        Picasso.get().load(gambar).into(imageView);
        tvnama = mView.findViewById(R.id.tv_cardnama);
        tvalamat = mView.findViewById(R.id.tv_cardalamat);
        tvalamat.setText(alamat);
        tvnama.setText(nama);
    }
    private Adapter2.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
    }
}
