package com.example.cirebon.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cirebon.R;
import com.squareup.picasso.Picasso;

public class Adapter3 extends RecyclerView.ViewHolder {
    View mView;
    ImageView imageView;
    public Button navigasi;
    public LinearLayout ll_rating;
    TextView tvnama, tvalamat, textrating, tvid;
    public Adapter3(View itemView) {
        super(itemView);
        mView = itemView;
        ll_rating = itemView.findViewById(R.id.ll_rating);
        navigasi = itemView.findViewById(R.id.btn_historynavigasi);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }
    public void setDetails(Context ctx, String nama, String gambar, String alamat, String id,double rating){
        imageView = mView.findViewById(R.id.iv_historycard);
        Picasso.get().load(gambar).into(imageView);
        tvnama = mView.findViewById(R.id.tv_historytittle);
        tvid = mView.findViewById(R.id.tv_historyid);
        tvalamat = mView.findViewById(R.id.tv_historyalamat);
        textrating = mView.findViewById(R.id.tv_historyrating);
        textrating.setText(String.valueOf(rating));
        tvalamat.setText(alamat);
        tvnama.setText(nama);
        tvid.setText(id);
    }
    private Adapter3.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(Adapter3.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
