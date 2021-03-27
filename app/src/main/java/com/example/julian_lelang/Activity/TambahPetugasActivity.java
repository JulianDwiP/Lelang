package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPetugasActivity extends AppCompatActivity {

    EditText julian_etUsername, julian_etPassword, julian_etNamaPetugas;
    Button julian_btnTambahPetugas;
    ApiService julian_apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_petugas);
        init();
    }

    private void init() {
        julian_apiService = ApiClient.getApiService();
        julian_btnTambahPetugas = findViewById(R.id.btn_tambahPetugas);
        julian_etUsername = findViewById(R.id.et_usernameTambahPetugas);
        julian_etPassword = findViewById(R.id.et_passwordTambahPetugas);
        julian_etNamaPetugas = findViewById(R.id.et_namaPetugas_TambahPetugas);
        final Spinner julian_spinnerJabatan = findViewById(R.id.spinner_levelPetugas_TambahPetugas);

        String julian_level;
        if (julian_spinnerJabatan.getSelectedItem().equals("Administrator")){
            julian_level = "1";
        }else{
            julian_level = "2";
        }

        julian_btnTambahPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                julian_apiService.registerAdminRequest(
                        julian_etNamaPetugas.getText().toString(),
                        julian_etUsername.getText().toString(),
                        julian_etPassword.getText().toString(),
                        julian_level).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(TambahPetugasActivity.this, "Berhasil menambah", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TambahPetugasActivity.this, "Gagal menambah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(TambahPetugasActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahPetugasActivity.this, BerandaAdmin.class);
        startActivity(intent);
    }
}