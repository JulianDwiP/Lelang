package com.example.julian_lelang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarBarang.daftarBarangFragmentAdmin;
import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarPetugas.daftarPetugasFragment;
import com.example.julian_lelang.Fragment.berandaPetugas.daftarLelang.daftarLelang;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BerandaPetugas extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView julian_bottomNav;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_petugas);

        init();
    }

    private void init() {
        julian_bottomNav = findViewById(R.id.bottom_navPetugas);
        sharedPref = new SharedPref(this);

        julian_bottomNav.setOnNavigationItemSelectedListener(this);
        setFragment(new daftarBarangFragmentAdmin());
        loadFragment(new daftarBarangFragmentAdmin());
    }

    private boolean loadFragment(Fragment daftarBarangFragment) {
        if (daftarBarangFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_berandaPetugas, daftarBarangFragment).addToBackStack(null)
                    .commit();
            return  true;
        }
        return false;
    }

    private void setFragment(daftarBarangFragmentAdmin daftarBarangFragmentAdmin) {
        Class FragmentClass = daftarPetugasFragment.class;
        setTitle("Daftar Barang");
        try {
            daftarBarangFragmentAdmin = (com.example.julian_lelang.Fragment.BerandaAdmin.DaftarBarang.daftarBarangFragmentAdmin) FragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_berandaPetugas, daftarBarangFragmentAdmin).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_tambahBarang:
                Intent intent1 = new Intent(BerandaPetugas.this, TambahBarangActivity.class);
                startActivity(intent1);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId() ){
            case R.id.nav_daftarBarangPetugas:
                fragmentClass = daftarBarangFragmentAdmin.class;
                break;
            case R.id.nav_daftarLelangPetugas:
                fragmentClass = daftarLelang.class;
                break;
            default:
                fragmentClass = daftarBarangFragmentAdmin.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        if (fragment !=  null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_berandaPetugas, fragment).commit();
            setTitle(menuItem.getTitle());
            return true;
        }
        menuItem.setChecked(true);
        return onOptionsItemSelected(menuItem);
    }

}