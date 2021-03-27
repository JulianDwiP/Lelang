package com.example.julian_lelang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.julian_lelang.Activity.BerandaMasyarakat;
import com.example.julian_lelang.Activity.TampilanAwal;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;

public class fragment_profileMasyarakat extends Fragment {

    EditText julian_etNama, julian_etUsername, julian_etTelp, julian_etAlamat;
    SharedPref sharedPref;

    public fragment_profileMasyarakat(){
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_masyarakat, container, false);
        setHasOptionsMenu(true);

        julian_etNama = view.findViewById(R.id.et_namaMasyarakat);
        julian_etTelp  = view.findViewById(R.id.et_telpMasyarakat);
        julian_etUsername = view.findViewById(R.id.et_usernameMasyarakat);
        julian_etAlamat = view.findViewById(R.id.et_alamatMasyarakat);
        sharedPref = new SharedPref(getContext());

        julian_etUsername.setEnabled(false);
        julian_etNama.setEnabled(false);
        julian_etTelp.setEnabled(false);
        julian_etAlamat.setEnabled(false);

        setUser();
        return view;
    }

    private void setUser() {
        julian_etTelp.setText(""+sharedPref.getTelp());
        julian_etNama.setText(""+sharedPref.getNama());
        julian_etUsername.setText(""+sharedPref.getUsername());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.profile_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_editProfil:
                julian_etAlamat.setEnabled(true);
                julian_etTelp.setEnabled(true);
                julian_etNama.setEnabled(true);
                julian_etUsername.setEnabled(true);
                return true;
            case R.id.menu_keluarProfil:
                sharedPref.simpanString(sharedPref.Nama, "");
                sharedPref.simpanString(sharedPref.Password, "");
                sharedPref.simpanString(sharedPref.Id_user, "");
                sharedPref.simpanString(sharedPref.Telp, "");
                sharedPref.simpanString(sharedPref.Username, "");
                sharedPref.simpanBoolean(SharedPref.CEK_SESSION, false);
                sharedPref.simpanString(SharedPref.cek_idLevel, "");
                Intent intent = new Intent(getContext(), TampilanAwal.class);
                startActivity(intent);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
