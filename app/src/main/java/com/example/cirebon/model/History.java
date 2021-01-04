package com.example.cirebon.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class History {
    public String uid;
    public double rating;
    public String alamat;
    public String gambar;
    public String id;
    public String nama;
    public History(){}
    public History(String uid, String nama, String alamat, double rating, String id, String gambar) {
        this.uid = uid;
        this.nama = nama;
        this.alamat = alamat;
        this.rating = rating;
        this.id = id;
        this.gambar = gambar;

    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGambar() {
        return gambar;
    }
    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nama", nama);
        result.put("alamat", alamat);
        result.put("id", id);
        result.put("gambar", gambar);
        result.put("rating", rating);
        return result;
    }
}
