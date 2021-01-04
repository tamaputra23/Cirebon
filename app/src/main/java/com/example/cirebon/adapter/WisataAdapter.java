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

public class WisataAdapter extends RecyclerView.ViewHolder {
    View mView;
    ImageView imageView;
    Button navigasi;
    TextView tvnama, tvalamat, textrating, tvtiket, tvkoorlat, tvkoorlong, tvgambar;
    public WisataAdapter(View itemView) {
        super(itemView);
        mView = itemView;
        navigasi = itemView.findViewById(R.id.btn_navigasi);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }
    public void setDetails(Context ctx, String nama, String gambar, String alamat, String tiket, double rating, double koorlat, double koorlong){
        imageView = mView.findViewById(R.id.iv_cardview);
        Picasso.get().load(gambar).into(imageView);
        tvnama = mView.findViewById(R.id.tv_cardnama);
        textrating = mView.findViewById(R.id.tv_cardrating);
        tvalamat = mView.findViewById(R.id.tv_cardalamat);
        tvtiket = mView.findViewById(R.id.tv_cardtiket);
        tvkoorlat = mView.findViewById(R.id.tvkoorlat);
        tvkoorlong = mView.findViewById(R.id.tvkoorlong);
        tvgambar = mView.findViewById(R.id.tvgambar);
        tvgambar.setText(gambar);
        tvtiket.setText(tiket);
        tvalamat.setText(alamat);
        tvnama.setText(nama);
        textrating.setText(String.valueOf(rating));
        tvkoorlat.setText(String.valueOf(koorlat));
        tvkoorlong.setText(String.valueOf(koorlong));
    }
    private WisataAdapter.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(WisataAdapter.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
