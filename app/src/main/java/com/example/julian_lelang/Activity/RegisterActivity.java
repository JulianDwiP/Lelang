package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText julian_etNamaLengkap;
    EditText julian_etUsername;
    EditText julian_etPassword;
    EditText julian_etNoTelp;
    Button julian_btnDaftar;
    ApiService julian_apiService;
    ProgressDialog julian_progressDialog;
    Toolbar julian_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        julian_apiService = ApiClient.getApiService();

        julian_toolbar = findViewById(R.id.toolbarRegisterMasyarakat);
        julian_etNamaLengkap = findViewById(R.id.et_namaLengkapRegisterMasyarakat);
        julian_etUsername = findViewById(R.id.et_usernameRegisterMasyarakat);
        julian_etPassword = findViewById(R.id.et_passwordRegisterMasyarakat);
        julian_etNoTelp = findViewById(R.id.et_noTelpRegisterMasyarakat);
        julian_btnDaftar = findViewById(R.id.btn_daftarRegisterMasyarakat);
        getSupportActionBar().hide();

        //toolbar
        julian_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //click
        julian_btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiInput();
            }
        });
    }

    private void validasiInput() {
        if (julian_etUsername.length() == 0) {
            julian_etUsername.setError("Field harus diisi");
            if (julian_etPassword.length() == 0) {
                julian_etPassword.setError("Field harus diisi");
            }
            if (julian_etNamaLengkap.length() == 0) {
                julian_etNamaLengkap.setError("Field harus diisi");
            }
            if (julian_etNoTelp.length() == 0) {
                julian_etNoTelp.setError("Field harus diisi");
            }
        }else if (julian_etPassword.length() == 0) {
            julian_etPassword.setError("Field harus diisi");
            if (julian_etUsername.length() == 0) {
                julian_etUsername.setError("Field harus diisi");
            }
            if (julian_etNamaLengkap.length() == 0) {
                julian_etNamaLengkap.setError("Field harus diisi");
            }
            if (julian_etNoTelp.length() == 0) {
                julian_etNoTelp.setError("Field harus diisi");
            }
        }else if (julian_etNamaLengkap.length() == 0) {
            julian_etNamaLengkap.setError("Field harus diisi");
            if (julian_etUsername.length() == 0) {
                julian_etUsername.setError("Field harus diisi");
            }
            if (julian_etPassword.length() == 0) {
                julian_etPassword.setError("Field harus diisi");
            }
            if (julian_etNoTelp.length() == 0) {
                julian_etNoTelp.setError("Field harus diisi");
            }
        }else if (julian_etNoTelp.length() == 0) {
            julian_etNoTelp.setError("Field harus diisi");
            if (julian_etUsername.length() == 0) {
                julian_etUsername.setError("Field harus diisi");
            }
            if (julian_etPassword.length() == 0) {
                julian_etPassword.setError("Field harus diisi");
            }
            if (julian_etNamaLengkap.length() == 0) {
                julian_etNamaLengkap.setError("Field harus diisi");
            }
        }else{
            julian_progressDialog = ProgressDialog.show(RegisterActivity.this, null, "Harap Tunggu..", true, false);
            register();
            }
        }


        private void register() {
            julian_apiService.registerRequest(
                    julian_etNamaLengkap.getText().toString(),
                    julian_etUsername.getText().toString(),
                    julian_etPassword.getText().toString(),
                    julian_etNoTelp.getText().toString()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        julian_progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("status").equals(true)|| jsonObject.getString("msg").equals("Pendaftaran berhasil")){
                                Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivityMasyarakat.class));
                            }else{
                                String error = jsonObject.getString("msg");
                                Toast.makeText(RegisterActivity.this, ""+error,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        julian_progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    julian_progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            });
        }
}