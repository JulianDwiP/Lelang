package com.example.julian_lelang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataLelangResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DataLelangModel> dataLelangModelList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataLelangModel> getDataLelangModelList() {
        return dataLelangModelList;
    }

    public void setDataLelangModelList(List<DataLelangModel> dataLelangModelList) {
        this.dataLelangModelList = dataLelangModelList;
    }
}