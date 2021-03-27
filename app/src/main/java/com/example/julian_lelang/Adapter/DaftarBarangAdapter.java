package com.example.julian_lelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Activity.BarangActivity;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.Model.DataBarangModel;
import com.example.julian_lelang.Model.PetugasModel;
import com.example.julian_lelang.R;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;

public class DaftarBarangAdapter extends RecyclerView.Adapter<DaftarBarangAdapter.DaftarBarangViewHolder> {

    List<DataBarangModel> dataBarangModels;
    ApiService julian_apiService;
    Context julian_context;

    public DaftarBarangAdapter(Context context, List<DataBarangModel> dataBarangModel) {
        julian_context = context;
        dataBarangModels = dataBarangModel;
    }
    @NonNull
    @Override
    public DaftarBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_rv_daftar_barang, parent, false);

        return new DaftarBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarBarangViewHolder holder, int position) {
        final DataBarangModel dataBarangModel = dataBarangModels.get(position);

        holder.julian_tvNamaBarang.setText(": "+dataBarangModel.getNama_barang());
        Locale lokal = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(lokal);
        holder.julian_tvHargaBarang.setText(": "+rupiah.format(dataBarangModel.getHarga_awal()));
        holder.julian_tvStatusBarang.setText(": "+dataBarangModel.getStatus());

        Bitmap setFoto = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String foto;
        if (dataBarangModel.getFoto().equals("")){
            foto = "barang.png";
        }else{
            foto = ""+dataBarangModel.getFoto();
        }
        try{
            URL url=new URL(ApiClient.Base_url_files+foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

        holder.julian_imgBarang.setImageBitmap(setFoto);

        holder.julian_cvBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(julian_context, BarangActivity.class);
                intent.putExtra("nama_barang", dataBarangModel.getNama_barang());
                intent.putExtra("harga_barang", String.valueOf(dataBarangModel.getHarga_awal()));
                intent.putExtra("foto_barang", foto);
                intent.putExtra("status_barang", dataBarangModel.getStatus());
                intent.putExtra("deskripsi_barang", dataBarangModel.getDeskripsi_barang());
                intent.putExtra("id_barang", String.valueOf(dataBarangModel.getId_barang()));
                intent.putExtra("tanggal", dataBarangModel.getTanggal());
                Log.e("debug", "harga : "+dataBarangModel.getHarga_awal());

                julian_context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBarangModels.size();
    }

    public class DaftarBarangViewHolder extends RecyclerView.ViewHolder {
        ImageView julian_imgBarang;
        TextView julian_tvNamaBarang, julian_tvStatusBarang, julian_tvHargaBarang;
        CardView julian_cvBarang;
        public DaftarBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            julian_imgBarang = itemView.findViewById(R.id.img_fotoBarangAdmin);
            julian_tvHargaBarang = itemView.findViewById(R.id.tv_hargaBarangAdmin);
            julian_tvNamaBarang = itemView.findViewById(R.id.tv_namaBarangAdmin);
            julian_tvStatusBarang = itemView.findViewById(R.id.tv_statusBarangAdmin);
            julian_cvBarang = itemView.findViewById(R.id.cv_daftarBarangFragmentAdmin);
        }
    }
}
