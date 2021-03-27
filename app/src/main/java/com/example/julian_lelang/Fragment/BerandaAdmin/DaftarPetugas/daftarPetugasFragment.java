package com.example.julian_lelang.Fragment.BerandaAdmin.DaftarPetugas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Activity.BerandaAdmin;
import com.example.julian_lelang.Adapter.DaftarPetugasAdapter;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.Model.PetugasModel;
import com.example.julian_lelang.Model.PetugasResponse;
import com.example.julian_lelang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftarPetugasFragment extends Fragment {


    ApiService julian_apiService;
    DaftarPetugasAdapter julian_daftarPetugasAdapter;
    RecyclerView julian_rv;
    FragmentActivity julian_fragmentActivity;

    public daftarPetugasFragment(){
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daftar_petugas, container, false);
        setHasOptionsMenu(true);
        julian_apiService = ApiClient.getApiService();
        julian_rv = view.findViewById(R.id.rv_fragmentPetugasAdmin);
        julian_fragmentActivity = getActivity();
        julian_rv.setLayoutManager(new LinearLayoutManager(julian_fragmentActivity));

        dataPetugas();

        return view;
    }

    private void dataPetugas() {
        julian_apiService.dataPetugas().enqueue(new Callback<PetugasResponse>() {
            @Override
            public void onResponse(Call<PetugasResponse> call, Response<PetugasResponse> response) {
                if (response.isSuccessful()){
                    List<PetugasModel> petugasModels = response.body().getPetugasModel();
                    julian_daftarPetugasAdapter = new DaftarPetugasAdapter(getContext(), petugasModels);
                    julian_rv.setAdapter(julian_daftarPetugasAdapter);
                    julian_daftarPetugasAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetugasResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.tambah_petugas_menu, menu);
    }
}