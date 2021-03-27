package com.example.julian_lelang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetugasResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<PetugasModel> petugasModel = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PetugasModel> getPetugasModel() {
        return petugasModel;
    }

    public void setPetugasModel(List<PetugasModel> petugasModel) {
        this.petugasModel = petugasModel;
    }
}
