package com.example.julian_lelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarPetugas.daftarPetugasFragment;
import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarBarang.daftarBarangFragmentAdmin;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BerandaAdmin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView julian_bottomNavView;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_admin);
        init();
    }

    private void init() {
        setFragment(new daftarPetugasFragment());
        julian_bottomNavView = findViewById(R.id.nav_view);
        sharedPref = new SharedPref(this);

        julian_bottomNavView.setOnNavigationItemSelectedListener(this);
        loadFragment(new daftarPetugasFragment());
    }

    private boolean loadFragment(Fragment daftarPetugasFragment) {
        if (daftarPetugasFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_berandaAdmin, daftarPetugasFragment).addToBackStack(null)
                    .commit();
            return  true;
        }
        return false;
    }


    private void setFragment(daftarPetugasFragment daftarPetugasFragment) {
        Class FragmentClass = daftarPetugasFragment.class;
        setTitle("Daftar Petugas");
        try {
            daftarPetugasFragment = (com.example.julian_lelang.Fragment.BerandaAdmin.DaftarPetugas.daftarPetugasFragment) FragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_berandaAdmin, daftarPetugasFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_tambahPetugas:
                Intent intent = new Intent(BerandaAdmin.this, TambahPetugasActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_tambahBarang:
                Intent intent1 = new Intent(BerandaAdmin.this, TambahBarangActivity.class);
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
        case R.id.nav_daftarPetugas:
            fragmentClass = daftarPetugasFragment.class;
            break;
        case R.id.nav_daftarBarang:
            fragmentClass = daftarBarangFragmentAdmin.class;
            break;
        default:
            fragmentClass = daftarPetugasFragment.class;
    }
    try {
        fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e){
        e.printStackTrace();
    }

    if (fragment !=  null){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_berandaAdmin, fragment).commit();
        setTitle(menuItem.getTitle());
        return true;
    }
    menuItem.setChecked(true);
    return onOptionsItemSelected(menuItem);
    }
}
