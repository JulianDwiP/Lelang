package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityPetugas extends AppCompatActivity {

    private ProgressDialog julianLoading;
    EditText julian_etUsername, julian_etPassword;
    Button julian_btnMasuk;
    ApiService julian_apiService;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petugas);

        julian_apiService = ApiClient.getApiService();
        init();
    }

    private void init() {
        julian_etPassword = findViewById(R.id.et_passwordLoginPetugas);
        julian_etUsername = findViewById(R.id.et_usernameLoginPetugas);
        julian_btnMasuk = findViewById(R.id.btn_masukLoginPetugas);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);

        julian_btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (julian_etUsername.length()==0){
                    julian_etUsername.setError("Username harus diisi");
                    if ( julian_etPassword.length()==0){
                        julian_etPassword.setError("Password harus diisi");
                    }
                }else if (julian_etPassword.length()==0) {
                    julian_etPassword.setError("Password harus diisi");
                    if (julian_etUsername.length() == 0) {
                        julian_etUsername.setError("Username harus diisi");
                    }
                }else{
                    julianLoading = ProgressDialog.show(LoginActivityPetugas.this, null, "Harap Tunggu..", true, false);
                    loginPetugas();
                }
            }
        });
    }

    private void loginPetugas() {
        julian_apiService.loginAdminRequest(julian_etUsername.getText().toString(), julian_etPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("true")){
                            if (jsonObject.getJSONObject("data").getString("id_level").equals("2")) {
                                sharedPref.simpanBoolean(SharedPref.CEK_SESSION, true);
                                sharedPref.simpanString(SharedPref.cek_idLevel, "2");
                                startActivity(new Intent(LoginActivityPetugas.this, BerandaPetugas.class));
                            }else{
                                sharedPref.simpanBoolean(SharedPref.CEK_SESSION, true);
                                sharedPref.simpanString(SharedPref.cek_idLevel, "3");
                                startActivity(new Intent(LoginActivityPetugas.this, BerandaAdmin.class));
                            }
                        } else {
                            if (jsonObject.getString("msg").equals("Akun belum terdaftar")) {
                                julianLoading.dismiss();
                                Toast.makeText(LoginActivityPetugas.this, "Akun belum terdaftar!", Toast.LENGTH_SHORT).show();
                            } else if (jsonObject.getString("msg").equals("Password salah")) {
                                julianLoading.dismiss();
                                Toast.makeText(LoginActivityPetugas.this, "Password salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    julianLoading.dismiss();
                    Toast.makeText(LoginActivityPetugas.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                julianLoading.dismiss();
                Toast.makeText(LoginActivityPetugas.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}