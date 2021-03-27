package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;

public class TampilanAwal extends AppCompatActivity {

    Button julian_btnPengguna, julian_btnPetugas;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_awal);
        julian_btnPengguna = findViewById(R.id.btn_penggunaTampilanAwal);
        julian_btnPetugas = findViewById(R.id.btn_petugasTampilanAwal);

        sharedPref = new SharedPref(this);

        if (sharedPref.getSession() == true){
            if (sharedPref.getCek_idLevel().equals("3")){
                Intent i = new Intent(TampilanAwal.this, BerandaAdmin.class);
                startActivity(i);
            }else if(sharedPref.getCek_idLevel().equals("2")){
                Intent i = new Intent(TampilanAwal.this, BerandaPetugas.class);
                startActivity(i);
            }else if (sharedPref.getCek_idLevel().equals("1")){
                Intent i = new Intent(TampilanAwal.this, BerandaMasyarakat.class);
                startActivity(i);
            }
        }else{

        }

        julian_btnPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TampilanAwal.this, LoginActivityMasyarakat.class);
                startActivity(intent);
            }
        });
        julian_btnPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TampilanAwal.this, LoginActivityPetugas.class);
                startActivity(intent);
            }
        });
    }
}