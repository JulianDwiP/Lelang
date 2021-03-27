package com.example.julian_lelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.julian_lelang.Activity.LelangActivity;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.Model.DataBarangModel;
import com.example.julian_lelang.Model.DataLelangModel;
import com.example.julian_lelang.R;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LelangAdapter extends RecyclerView.Adapter<LelangAdapter.LelangViewHolder> {

    ApiService apiService;
    List<DataLelangModel> dataLelangArray;
    Context jcontext;

    public LelangAdapter(Context context, List<DataLelangModel> dataLelangModels) {
        jcontext = context;
        dataLelangArray = dataLelangModels;
    }

    @NonNull
    @Override
    public LelangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_rv_lelang, parent, false);

        return new LelangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LelangViewHolder holder, int position) {
        final DataLelangModel dataLelangModel = dataLelangArray.get(position);

        Locale lokal = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(lokal);
        holder.julian_tvStatusLelang.setText(""+dataLelangModel.getStatus());
        holder.julian_tvNamaLelang.setText(""+dataLelangModel.getNama_barang());
        holder.julian_tvHargaAwalLelang.setText(""+rupiah.format(dataLelangModel.getHarga_awal()));
        holder.julian_tvHargaAkhirLelang.setText(""+rupiah.format(dataLelangModel.getHarga_akhir()));

        Bitmap setFoto = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String foto;
        if (dataLelangModel.getFoto().equals("")){
            foto = "barang.png";
        }else{
            foto = ""+dataLelangModel.getFoto();
        }
        try{
            URL url=new URL(ApiClient.Base_url_files+foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

        holder.julian_imgLelang.setImageBitmap(setFoto);

        holder.julian_cvLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(jcontext, LelangActivity.class);
                intent.putExtra(LelangActivity.id_lelang, dataLelangModel.getId_lelang());
                intent.putExtra("deskripsi", dataLelangModel.getDeskripsi_barang());
                intent.putExtra("foto", dataLelangModel.getFoto());
                intent.putExtra(LelangActivity.hargaAkhir, dataLelangModel.getHarga_akhir());
                intent.putExtra(LelangActivity.hargaAwal, dataLelangModel.getHarga_awal());
                intent.putExtra(LelangActivity.id_barang, dataLelangModel.getId_barang());
                intent.putExtra(LelangActivity.idUser, dataLelangModel.getId_user());
                intent.putExtra("nama_barang", dataLelangModel.getNama_barang());
                intent.putExtra("nama_lengkap", dataLelangModel.getNama_lengkap());
                intent.putExtra("status", dataLelangModel.getStatus());
                intent.putExtra("tanggal", dataLelangModel.getTgl_lelang());
                intent.putExtra("nama_petugas", dataLelangModel.getNama_petugas());
                jcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataLelangArray.size();
    }

    public class LelangViewHolder extends RecyclerView.ViewHolder {
        ImageView julian_imgLelang;
        TextView julian_tvNamaLelang, julian_tvStatusLelang, julian_tvHargaAwalLelang, julian_tvHargaAkhirLelang;
        CardView julian_cvLelang;
        public LelangViewHolder(@NonNull View itemView) {
            super(itemView);
            julian_imgLelang = itemView.findViewById(R.id.img_fotoRvLelang);
            julian_tvHargaAwalLelang = itemView.findViewById(R.id.tv_hargaAwalRvLelang);
            julian_tvNamaLelang = itemView.findViewById(R.id.tv_namaBarangLelang);
            julian_tvStatusLelang = itemView.findViewById(R.id.tv_statusLelang);
            julian_cvLelang = itemView.findViewById(R.id.cv_daftarLelang);
            julian_tvHargaAkhirLelang = itemView.findViewById(R.id.tv_hargaAkhirRvLelang);
        }
    }
}
