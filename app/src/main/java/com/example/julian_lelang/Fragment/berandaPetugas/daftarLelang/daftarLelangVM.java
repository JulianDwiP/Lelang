package com.example.julian_lelang.Fragment.berandaPetugas.daftarLelang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class daftarLelangVM extends ViewModel {

    private MutableLiveData<String> mText;

    public daftarLelangVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}