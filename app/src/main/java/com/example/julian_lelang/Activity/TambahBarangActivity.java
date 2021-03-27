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
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.julian_lelang.Adapter.FileUtil;
import com.example.julian_lelang.Api.ApiClient;
import com.example.julian_lelang.Api.ApiService;
import com.example.julian_lelang.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarangActivity extends AppCompatActivity {

    EditText julian_namaBarang, julian_hargaBarang, julian_deskripsiBarang;
    ImageView julian_fotoBarang;
    Button julian_tambahBarang;
    ApiService apiService;
    String Date;
    Uri filePath;
    Bitmap bitmap;
    String path;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        init();
    }

    private void init() {
        julian_namaBarang = findViewById(R.id.et_namaBarang);
        julian_hargaBarang = findViewById(R.id.et_hargaBarang);
        julian_deskripsiBarang = findViewById(R.id.et_hargaBarang);
        julian_fotoBarang = findViewById(R.id.img_uploadFoto);
        julian_tambahBarang = findViewById(R.id.btn_tambahBarang);
        apiService = ApiClient.getApiService();

        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());

        julian_fotoBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(TambahBarangActivity.this);
            }
        });

        julian_tambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirimBarang();
            }
        });
    }

    private void kirimBarang() {
        apiService.tambahBarang(julian_namaBarang.getText().toString(), Date, julian_deskripsiBarang.getText().toString(),
                julian_hargaBarang.getText().toString(), file.getName()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(TambahBarangActivity.this,"Add Item Succeeded",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TambahBarangActivity.this, "gagal : "+t, Toast.LENGTH_SHORT).show();
            }
        });
        uploadImage();
    }

    private void uploadImage() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        ApiService uploadApis = ApiClient.getApiService();
        Call<ResponseBody> call = uploadApis.tambahFoto(parts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(TambahBarangActivity.this,"Berhasil menambah item",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call <ResponseBody> call, Throwable t) {

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
                        Uri tempUri = getImageUri(TambahBarangActivity.this.getApplicationContext(), bitmap);
                        file = new File(getRealPathFromURI(tempUri));
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        filePath = data.getData();
                        path = FileUtil.getPath(TambahBarangActivity.this, data.getData());
                        file = new File(path);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePath != null) {
                            Cursor cursor = TambahBarangActivity.this.getContentResolver().query(filePath,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Camera", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (TambahBarangActivity.this.getContentResolver() != null) {
            Cursor cursor = TambahBarangActivity.this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahBarangActivity.this, BerandaAdmin.class);
        startActivity(intent);
    }
}