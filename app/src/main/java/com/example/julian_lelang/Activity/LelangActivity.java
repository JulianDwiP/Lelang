package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LelangActivity extends AppCompatActivity {

    TextView julian_tvHargaAwal, julian_tvHargaAkhir, julian_tvDeskripsi;
    Button julian_btnMulaiPenawaran, julian_btnKirim, julian_btnBatalkan;
    CardView julian_cvPenawaran;
    ApiService julian_apiService;
    ImageView julian_imgLelang;
    EditText julian_etPenawaran;
    SharedPref sharedPref;

    public static final String id_lelang ="id_lelang", hargaAkhir ="harga_akhir", hargaAwal="harga_awal", id_barang="id_barang", idUser ="id_user";

    String julian_deskripsi, julian_foto, julian_namaBarang, julian_namaLengkap, julian_status, julian_tanggal, julian_namaPetugas;
    int julian_idLelang,julian_hargaAkhir, julian_hargaAwal, julian_idBarang,julian_idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lelang);

        init();
    }

    private void init() {
        julian_tvHargaAwal = findViewById(R.id.tv_hargaAwalLelang);
        julian_tvHargaAkhir = findViewById(R.id.tv_hargaAkhirLelang);
        julian_tvDeskripsi = findViewById(R.id.tv_deskripsiLelang);
        julian_btnKirim = findViewById(R.id.btn_kirimPenawaran);
        julian_btnBatalkan = findViewById(R.id.btn_batalkanPenawaran);
        julian_btnMulaiPenawaran = findViewById(R.id.btn_mulaiPenawaran);
        julian_cvPenawaran = findViewById(R.id.cv_penawaranLelang);
        julian_imgLelang = findViewById(R.id.img_lelang);
        julian_etPenawaran = findViewById(R.id.et_penawaranLelang);

        julian_apiService = ApiClient.getApiService();
        sharedPref = new SharedPref(this);

        julian_idLelang = getIntent().getIntExtra(id_lelang, 0);
        julian_deskripsi = getIntent().getStringExtra("deskripsi");
        julian_foto = getIntent().getStringExtra("foto");
        julian_hargaAkhir = getIntent().getIntExtra(hargaAkhir, 0);
        julian_hargaAwal = getIntent().getIntExtra(hargaAwal, 0);
        julian_idBarang = getIntent().getIntExtra(id_barang, 0);
        julian_idUser = getIntent().getIntExtra(idUser,0);
        julian_namaBarang = getIntent().getStringExtra("nama_barang");
        julian_namaLengkap = getIntent().getStringExtra("nama_lengkap");
        julian_status = getIntent().getStringExtra("status");
        julian_tanggal = getIntent().getStringExtra("tanggal");
        julian_namaPetugas = getIntent().getStringExtra("nama_petugas");

        julian_cvPenawaran.setVisibility(View.GONE);

        Locale lokal = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(lokal);
        int hargaAwal = Integer.valueOf(julian_hargaAwal), hargaAkhir = Integer.valueOf(julian_hargaAkhir);
        julian_tvHargaAwal.setText(": "+rupiah.format(hargaAwal));
        julian_tvHargaAkhir.setText(": "+rupiah.format(hargaAkhir));
        julian_tvDeskripsi.setText(""+julian_deskripsi);

        Bitmap foto = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            URL url=new URL(ApiClient.Base_url_files+julian_foto);
            foto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        julian_imgLelang.setImageBitmap(foto);



        julian_btnBatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                julian_cvPenawaran.setVisibility(View.GONE);
            }
        });

        julian_btnMulaiPenawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                julian_cvPenawaran.setVisibility(View.VISIBLE);
            }
        });

        julian_btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPenawaran();
            }
        });
    }

    private void postPenawaran() {
        julian_apiService.penawaranLelang(String.valueOf(julian_idLelang),julian_etPenawaran.getText().toString(), sharedPref.getId_user()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(LelangActivity.this, "Berhasil memberikan penawaran", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", "Error activity lelang post penawaran : "+t.getMessage());
            }
        });
    }
}