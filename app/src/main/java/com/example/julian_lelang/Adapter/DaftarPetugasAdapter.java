package com.example.julian_lelang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;

import java.util.List;

import com.example.julian_lelang.Model.PetugasModel;
import com.example.julian_lelang.Model.PetugasResponse;
import com.example.julian_lelang.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPetugasAdapter extends RecyclerView.Adapter<DaftarPetugasAdapter.DaftarPetugasViewHolder> {

    List<PetugasModel> julian_petugasModels;
    ApiService julian_apiService;
    Context julian_context;

    public DaftarPetugasAdapter(Context context, List<PetugasModel> petugasModels) {
        julian_context = context;
        julian_petugasModels = petugasModels;
    }

    public class DaftarPetugasViewHolder extends RecyclerView.ViewHolder {
        TextView julian_namaPetugas, julian_jabatanPetugas;
        ImageView julian_hapusPetugas;
        CardView julian_cvDaftarPetugas;
        public DaftarPetugasViewHolder(@NonNull View itemView) {
            super(itemView);
            julian_namaPetugas = itemView.findViewById(R.id.tv_namaLengkapPetugas);
            julian_jabatanPetugas = itemView.findViewById(R.id.tv_jabatanPetugas);
            julian_hapusPetugas = itemView.findViewById(R.id.img_hapusPetugas);
            julian_cvDaftarPetugas = itemView.findViewById(R.id.cv_daftarPetugasFragmentAdmin);
            julian_apiService = ApiClient.getApiService();
        }
    }

    @NonNull
    @Override
    public DaftarPetugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_rv_daftar_petugas_admin, parent,false);
        return new DaftarPetugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarPetugasViewHolder holder, int position) {
        final PetugasModel petugasModel = julian_petugasModels.get(position);

        holder.julian_namaPetugas.setText(": "+petugasModel.getNama_petugas());
        String julian_jabatan;
        if (petugasModel.getId_level() ==1 ){
            julian_jabatan = "Administrator";
            holder.julian_hapusPetugas.setVisibility(View.GONE);
        }else{
            julian_jabatan = "Petugas";
            holder.julian_hapusPetugas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    julian_apiService.hapusPetugas(petugasModel.getId_petugas()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            julian_petugasModels.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, julian_petugasModels.size());
                            Toast.makeText(julian_context, "Berhasil", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });

                }
            });
        }
        holder.julian_jabatanPetugas.setText(": "+julian_jabatan);
    }

    @Override
    public int getItemCount() {
        return julian_petugasModels.size();
    }


}
