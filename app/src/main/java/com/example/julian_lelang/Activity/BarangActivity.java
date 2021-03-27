package com.example.julian_lelang.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian_lelang.Adapter.FileUtil;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;
import com.example.julian_lelang.SharedPrefManager.SharedPref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {

    ImageView julian_fotoBarang;
    EditText  julian_etNamaBarang, julian_etStatus;
    Button julian_btnHapus, julian_btnUpdate, julian_btnSimpan;
    ApiService julian_apiService;

    String julian_nama, julian_status, julian_foto, julian_id, julian_harga,julian_deskripsi,julian_tanggal;
    SharedPref sharedPref;
    Uri filePath;
    Bitmap bitmap;
    String path;
    File file;
    String cek = "2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        init();
    }

    private void init() {
        julian_etNamaBarang = findViewById(R.id.tv_namaBarangDetail);
        julian_etStatus = findViewById(R.id.tv_statusBarangDetail);
        julian_btnHapus =findViewById(R.id.btn_hapusBarangDetail);
        julian_fotoBarang = findViewById(R.id.img_fotoBarangDetail);
        julian_btnUpdate = findViewById(R.id.btn_updateBarang);
        julian_btnSimpan = findViewById(R.id.btn_simpanDataBarang);

        julian_apiService = ApiClient.getApiService();
        sharedPref = new SharedPref(this);

        julian_nama = getIntent().getStringExtra("nama_barang");
        julian_foto = getIntent().getStringExtra("foto_barang");
        julian_status = getIntent().getStringExtra("status_barang");
        julian_id = getIntent().getStringExtra("id_barang");
        julian_harga = getIntent().getStringExtra("harga_barang");
        julian_deskripsi = getIntent().getStringExtra("deskripsi_barang");
        julian_tanggal = getIntent().getStringExtra("tanggal");


        julian_etNamaBarang.setText(""+julian_nama);
        julian_etStatus.setText(""+julian_status);

        julian_etNamaBarang.setEnabled(false);
        julian_etStatus.setEnabled(false);
        julian_btnSimpan.setVisibility(View.GONE);

        Bitmap foto = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
            try{
                URL url=new URL(ApiClient.Base_url_files+julian_foto);
                foto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }catch (IOException e){
                e.printStackTrace();
            }
        julian_fotoBarang.setImageBitmap(foto);

        julian_btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusBarang();
            }
        });

        julian_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBarang();
            }
        });

    }

    private void editBarang() {

        julian_fotoBarang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectImage(BarangActivity.this);
                return true;
                }
            });

        julian_etStatus.setEnabled(true);
        julian_etNamaBarang.setEnabled(true);

        julian_btnSimpan.setVisibility(View.VISIBLE);
        julian_btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cek.equals("1")){
                    julian_apiService.updatebarang(julian_etNamaBarang.getText().toString(),
                            julian_tanggal,julian_deskripsi,julian_harga,file.getName(),
                            julian_etStatus.getText().toString(), julian_id).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(BarangActivity.this, "Berhasil menyimpan", Toast.LENGTH_SHORT).show();
                                uploadImage();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "Error BarangActivity : "+t.getMessage());
                        }
                    });
                }else{
                    julian_apiService.updatebarang(julian_etNamaBarang.getText().toString(),
                            julian_tanggal,julian_deskripsi,julian_harga,julian_foto,
                            julian_etStatus.getText().toString(), julian_id).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(BarangActivity.this, "Berhasil menyimpan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "Error BarangActivity : "+t.getMessage());
                        }
                    });
                }
            }
        });
    }


    private void hapusBarang() {
        julian_apiService.hapusBarang(julian_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(BarangActivity.this, "Berhasil menghapus", Toast.LENGTH_SHORT).show();
                    if (sharedPref.getCek_idLevel().equals("2")){
                        Intent intent = new Intent(BarangActivity.this, BerandaPetugas.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(BarangActivity.this, BerandaAdmin.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        julian_fotoBarang.setImageBitmap(bitmap);
                        Uri tempUri = getImageUri(BarangActivity.this.getApplicationContext(), bitmap);
                        file = new File(getRealPathFromURI(tempUri));
                        cek = "1";
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        filePath = data.getData();
                        path = FileUtil.getPath(BarangActivity.this, data.getData());
                        file = new File(path);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePath != null) {
                            Cursor cursor = BarangActivity.this.getContentResolver().query(filePath,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                cek = "1";
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                julian_fotoBarang.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void uploadImage() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        ApiService uploadApis = ApiClient.getApiService();
        Call<ResponseBody> call = uploadApis.tambahFoto(parts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call <ResponseBody> call, Throwable t) {

            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Camera", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (BarangActivity.this.getContentResolver() != null) {
            Cursor cursor = BarangActivity.this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
}