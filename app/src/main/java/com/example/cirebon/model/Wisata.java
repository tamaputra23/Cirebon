package com.example.cirebon.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Wisata {
    public String uid;
    public String nama;
    public String id;
    public String gambar;
    public String alamat;
    public String tiket;
    public String hari;
    public String phone;
    public String parent;
    public double rating;
    public double koorlat;
    public double koorlong;
    public Wisata(){}
    public Wisata( String nama, String gambar, String alamat, String tiket, String id,String hari,String phone,String parent, double rating, double koorlat, double koorlong) {
        this.uid = uid;
        this.alamat = alamat;
        this.nama = nama;
        this.gambar = gambar;
        this.tiket = tiket;
        this.rating = rating;
        this.koorlat = koorlat;
        this.koorlong = koorlong;
        this.id = id;
        this.phone= phone;
        this.parent= parent;


        this.hari = hari;

    }
    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    public  String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public  String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
    public  String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public  String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public  String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }
    public  String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    public  String getId() {
        return id;
    }

    public void setUid(String id) {
        this.id = id;
    }
    public  double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public  double getKoorlat() {
        return koorlat;
    }

    public void setKoorlat(double koorlat) {
        this.koorlat = koorlat;
    }
    public  double getKoorlong() {
        return koorlong;
    }

    public void setKoorlong(double koorlong) {
        this.koorlong = koorlong;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nama", nama);
        result.put("gambar", gambar);
        result.put("alamat", alamat);
        result.put("id", id);
        result.put("hari", hari);
        result.put("rating", rating);
        result.put("koorlat", koorlat);
        result.put("koorlong", koorlong);
        return result;
    }
}
