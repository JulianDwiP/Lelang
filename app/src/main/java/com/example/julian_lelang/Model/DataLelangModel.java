package com.example.julian_lelang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLelangModel {
    @SerializedName("id_lelang")
    @Expose
    private int id_lelang;
    @SerializedName("tgl_lelang")
    @Expose
    private String tgl_lelang;
    @SerializedName("harga_akhir")
    @Expose
    private int harga_akhir;
    @SerializedName("id_user")
    @Expose
    private int id_user;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("id_barang")
    @Expose
    private int id_barang;
    @SerializedName("nama_barang")
    @Expose
    private String nama_barang;
    @SerializedName("harga_awal")
    @Expose
    private int harga_awal;
    @SerializedName("deskripsi_barang")
    @Expose
    private String deskripsi_barang;
    @SerializedName("nama_lengkap")
    @Expose
    private String nama_lengkap;
    @SerializedName("nama_petugas")
    @Expose
    private String nama_petugas;

    public String getNama_petugas() {
        return nama_petugas;
    }

    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }

    public int getId_lelang() {
        return id_lelang;
    }

    public void setId_lelang(int id_lelang) {
        this.id_lelang = id_lelang;
    }

    public String getTgl_lelang() {
        return tgl_lelang;
    }

    public void setTgl_lelang(String tgl_lelang) {
        this.tgl_lelang = tgl_lelang;
    }

    public int getHarga_akhir() {
        return harga_akhir;
    }

    public void setHarga_akhir(int harga_akhir) {
        this.harga_akhir = harga_akhir;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }
}
