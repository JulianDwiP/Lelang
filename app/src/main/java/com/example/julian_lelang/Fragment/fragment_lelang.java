package com.example.julian_lelang.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Adapter.DaftarBarangAdapter;
import com.example.julian_lelang.Adapter.LelangAdapter;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.Model.DataBarangModel;
import com.example.julian_lelang.Model.DataLelangModel;
import com.example.julian_lelang.Model.DataLelangResponse;
import com.example.julian_lelang.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_lelang extends Fragment {

    RecyclerView julian_rv;
    ApiService julian_apiService;
    LelangAdapter lelangAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lelang, container, false);
        setHasOptionsMenu(true);

        julian_rv = view.findViewById(R.id.rv_fragmentLelang);
        julian_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        julian_apiService = ApiClient.getApiService();

        dataLelang();
        return view;
    }

    private void dataLelang() {
        julian_apiService.dataLelang().enqueue(new Callback<DataLelangResponse>() {
            @Override
            public void onResponse(Call<DataLelangResponse> call, Response<DataLelangResponse> response) {
                if (response.isSuccessful()){
                    List<DataLelangModel> dataLelangModels = response.body().getDataLelangModelList();
                    lelangAdapter = new LelangAdapter(getContext(), dataLelangModels);
                    julian_rv.setAdapter(lelangAdapter);
                    lelangAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DataLelangResponse> call, Throwable t) {
                Log.e("Debug", "Fragment Lelang Error : "+t.getMessage());
            }
        });
    }
}
