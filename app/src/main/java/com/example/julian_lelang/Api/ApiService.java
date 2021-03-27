package com.example.julian_lelang.Api;

import com.example.julian_lelang.Model.DataBarangResponse;
import com.example.julian_lelang.Model.DataLelangResponse;
import com.example.julian_lelang.Model.PetugasResponse;

import java.io.File;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("login/masyarakat")
    Call<ResponseBody> loginRequest(@Field("username") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("login/admin")
    Call<ResponseBody> loginAdminRequest(@Field("username") String username,
                                         @Field("password") String password);

    @FormUrlEncoded
    @POST("register/admin")
    Call<ResponseBody> registerAdminRequest(@Field("nama_petugas") String nama_petugas,
                                       @Field("username") String username,
                                       @Field("password") String password,
                                       @Field("id_level") String id_level);

    @FormUrlEncoded
    @POST("register/masyarakat")
    Call<ResponseBody> registerRequest(@Field("nama_lengkap") String nama_lengkap,
                                       @Field("username") String username,
                                       @Field("password") String password,
                                       @Field("telp") String telp);

    @FormUrlEncoded
    @POST("tambah/barang")
    Call<ResponseBody> tambahBarang(@Field("nama_barang") String nama_barang,
                                    @Field("tanggal") String tanggal,
                                    @Field("deskripsi_barang") String deskripsi_barang,
                                    @Field("harga_awal") String harga_awal,
                                    @Field("foto") String foto);

    @FormUrlEncoded
    @POST("update/barang")
    Call<ResponseBody> updatebarang(@Field("nama_barang") String nama_barang,
                                    @Field("tanggal") String tanggal,
                                    @Field("deskripsi_barang") String deskripsi_barang,
                                    @Field("harga_awal") String harga_awal,
                                    @Field("foto") String foto,
                                    @Field("status") String status,
                                    @Field("id_barang") String id_barang);


    @Multipart
    @POST("tambah/foto/barang")
    Call<ResponseBody> tambahFoto(@Part MultipartBody.Part part);


    @GET("data/petugas")
    Call<PetugasResponse> dataPetugas();

    @FormUrlEncoded
    @POST("hapus/petugas")
    Call<ResponseBody> hapusPetugas(@Field("id_petugas") String id_petugas);

    @FormUrlEncoded
    @POST("hapus/barang")
    Call<ResponseBody> hapusBarang(@Field("id_barang") String id_barang);

    @GET("data/barang")
    Call<DataBarangResponse> dataBarang();

    @GET("data/lelang")
    Call<DataLelangResponse> dataLelang();

    @FormUrlEncoded
    @POST("penawaran/lelang")
    Call<ResponseBody> penawaranLelang(@Field("id_lelang") String id_lelang,
                                       @Field("harga_akhir") String harga_akhir,
                                       @Field("id_user") String id_user);

}
