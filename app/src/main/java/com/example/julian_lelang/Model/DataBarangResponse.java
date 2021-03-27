package com.example.julian_lelang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataBarangResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<DataBarangModel> dataBarangModels = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBarangModel> getDataBarangModels() {
        return dataBarangModels;
    }

    public void setDataBarangModels(List<DataBarangModel> dataBarangModels) {
        this.dataBarangModels = dataBarangModels;
    }
}
