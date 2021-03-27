package com.example.julian_lelang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataBarangModel {
    @SerializedName("id_barang")
    @Expose
    private int id_barang;
    @SerializedName("nama_barang")
    @Expose
    private String nama_barang;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("harga_awal")
    @Expose
    private int harga_awal;
    @SerializedName("deskripsi_barang")
    @Expose
    private String deskripsi_barang;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status")
    @Expose
    private String status;

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getHarga_awal() {
        return harga_awal;
    }

    public void setHarga_awal(int harga_awal) {
        this.harga_awal = harga_awal;
    }

    public String getDeskripsi_barang() {
        return deskripsi_barang;
    }

    public void setDeskripsi_barang(String deskripsi_barang) {
        this.deskripsi_barang = deskripsi_barang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
