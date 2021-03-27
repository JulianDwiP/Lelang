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

public class LoginActivityMasyarakat extends AppCompatActivity {

    private ProgressDialog julianLoading;
    EditText julian_etUsername, julian_etPassword;
    Button julian_btnMasuk;
    TextView julian_tvDaftar, julian_tvLupa;
    ApiService julian_apiService;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        julian_apiService = ApiClient.getApiService();
        init();
    }

    private void init() {
        julian_etPassword = findViewById(R.id.et_passwordLogin);
        julian_etUsername = findViewById(R.id.et_usernameLogin);
        julian_btnMasuk = findViewById(R.id.btn_masukLogin);
        julian_tvDaftar = findViewById(R.id.tv_daftarLogin);
        julian_tvLupa = findViewById(R.id.tv_lupaPasswordLogin);
        sharedPref = new SharedPref(this);

        getSupportActionBar().hide();

        julian_tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityMasyarakat.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

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
                    julianLoading = ProgressDialog.show(LoginActivityMasyarakat.this, null, "Harap Tunggu..", true, false);
                    loginMasyarakat();
                }
            }
        });
    }
    private void loginMasyarakat() {
        julian_apiService.loginRequest(
                julian_etUsername.getText().toString(),
                julian_etPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        if (jsonObject.getString("status").equals("true")){
                            int user_id = jsonObject.getJSONObject("data").getInt("id_user");
                                String julian_username = jsonObject.getJSONObject("data").getString("username");
                                String julian_password = jsonObject.getJSONObject("data").getString("password");
                                String julian_telp = jsonObject.getJSONObject("data").getString("telp");
                                String id_user = jsonObject.getJSONObject("data").getString("id_user");
                                String julian_nama = jsonObject.getJSONObject("data").getString("nama_lengkap");

                                sharedPref.simpanString(sharedPref.Nama, julian_nama);
                                sharedPref.simpanString(sharedPref.Password, julian_password);
                                sharedPref.simpanString(sharedPref.Id_user, id_user);
                                sharedPref.simpanString(sharedPref.Telp, julian_telp);
                                sharedPref.simpanString(sharedPref.Username, julian_username);
                                sharedPref.simpanBoolean(SharedPref.CEK_SESSION, true);
                                sharedPref.simpanString(SharedPref.cek_idLevel, "1");

                                Toast.makeText(LoginActivityMasyarakat.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivityMasyarakat.this, BerandaMasyarakat.class));
                        } else {
                            if (jsonObject.getString("msg").equals("Akun belum terdaftar")) {
                                julianLoading.dismiss();
                                Toast.makeText(LoginActivityMasyarakat.this, "Akun belum terdaftar!", Toast.LENGTH_SHORT).show();
                            } else if (jsonObject.getString("msg").equals("Password salah")) {
                                julianLoading.dismiss();
                                Toast.makeText(LoginActivityMasyarakat.this, "Password salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    julianLoading.dismiss();
                    Toast.makeText(LoginActivityMasyarakat.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                julianLoading.dismiss();
                Toast.makeText(LoginActivityMasyarakat.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}