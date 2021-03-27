package com.example.julian_lelang.SharedPrefManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String nama_aplikasi = "Julian_lelang";

    public static final String CEK_SESSION = "CekSession";
    public static final String cek_idLevel = "cekIdLevel";


    public static final String Nama = "nama";
    public static final String Username = "username";
    public static final String Password = "password";
    public static final String Telp = "telp";
    public static final String Id_user = "id";



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    public SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences(nama_aplikasi, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void simpanBoolean(String sp, boolean value){
        editor.putBoolean(sp, value);
        editor.commit();
    }
    public void simpanInt(String sp, int value){
        editor.putInt(sp, value);
        editor.commit();
    }

    public void simpanString(String sp, String value){
        editor.putString(sp, value);
        editor.commit();
    }


    public boolean getSession(){
        return sharedPreferences.getBoolean(CEK_SESSION, false);
    }

    public String getCek_idLevel(){
        return sharedPreferences.getString(cek_idLevel, "");
    }

    public String getNama() {
        return sharedPreferences.getString(Nama,"");
    }

    public String getUsername() {
        return sharedPreferences.getString(Username,"");
    }
    public String getPassword() {
        return sharedPreferences.getString(Password,"");
    }

    public String getTelp() {
        return sharedPreferences.getString(Telp,"");
    }
    public  String getId_user(){
        return  sharedPreferences.getString(Id_user, "");
    }
}
