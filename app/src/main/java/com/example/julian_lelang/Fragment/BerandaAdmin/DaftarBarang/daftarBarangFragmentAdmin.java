package com.example.julian_lelang.Fragment.BerandaAdmin.DaftarBarang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Activity.BerandaAdmin;
import com.example.julian_lelang.Adapter.DaftarBarangAdapter;
import com.example.julian_lelang.Adapter.DaftarPetugasAdapter;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.Model.DataBarangModel;
import com.example.julian_lelang.Model.DataBarangResponse;
import com.example.julian_lelang.Model.PetugasModel;
import com.example.julian_lelang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftarBarangFragmentAdmin extends Fragment {

    RecyclerView julian_rv;
    ApiService julian_apiService;
    DaftarBarangAdapter daftarBarangAdapter;

    public daftarBarangFragmentAdmin(){
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar_barang, container, false);
        setHasOptionsMenu(true);
        julian_rv = view.findViewById(R.id.rv_fragmentBarangAdmin);
        julian_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        julian_apiService = ApiClient.getApiService();

        dataBarang();

        return view;
    }

    private void dataBarang() {
        julian_apiService.dataBarang().enqueue(new Callback<DataBarangResponse>() {
            @Override
            public void onResponse(Call<DataBarangResponse> call, Response<DataBarangResponse> response) {
                if (response.isSuccessful()){
                    List<DataBarangModel> dataBarangModel = response.body().getDataBarangModels();
                    daftarBarangAdapter = new DaftarBarangAdapter(getContext(), dataBarangModel);
                    julian_rv.setAdapter(daftarBarangAdapter);
                    daftarBarangAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DataBarangResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.tambah_barang_menu, menu);
    }
}