package com.example.julian_lelang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarBarang.daftarBarangFragmentAdmin;
import com.example.julian_lelang.Fragment.BerandaAdmin.DaftarPetugas.daftarPetugasFragment;
import com.example.julian_lelang.Fragment.fragment_historyLelang;
import com.example.julian_lelang.Fragment.fragment_lelang;
import com.example.julian_lelang.Fragment.fragment_profileMasyarakat;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BerandaMasyarakat extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView julian_bottomNav;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_masyarakat);

        init();
    }

    private void init() {
        julian_bottomNav = findViewById(R.id.bottom_navMasyarakat);
        sharedPref = new SharedPref(this);

        julian_bottomNav.setOnNavigationItemSelectedListener(this);
        setFragment(new fragment_lelang());
        loadFragment(new fragment_lelang());
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_berandaMasyarakat, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    private void setFragment(fragment_lelang fragment_lelang) {
        Class FragmentClass = daftarPetugasFragment.class;
        setTitle("Lelang");
        try {
            fragment_lelang = (com.example.julian_lelang.Fragment.fragment_lelang) FragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_berandaMasyarakat, fragment_lelang).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId() ){
            case R.id.nav_lelangMasyarakat:
                fragmentClass = fragment_lelang.class;
                break;
            case R.id.nav_historyMasyarakat:
                fragmentClass = fragment_historyLelang.class;
                break;
            case R.id.nav_profileMasyarakat:
                fragmentClass = fragment_profileMasyarakat.class;
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
            fragmentManager.beginTransaction().replace(R.id.nav_berandaMasyarakat, fragment).commit();
            setTitle(item.getTitle());
            return true;
        }
        item.setChecked(true);
        return onOptionsItemSelected(item);
    }
}