package com.junhyeoklee.paik_s_cookingsecretbook.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.junhyeoklee.paik_s_cookingsecretbook.db.AppDatabase;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    AppDatabase appDatabase;

    private LiveData<List<ModelHome>> homeList;
    private LiveData<List<ModelHome>> home;
    private LiveData<ModelHome> favorit;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());

        homeList = appDatabase.modelHomeDAO().getAll();

    }

    public LiveData<List<ModelHome>> getHomes(){return homeList;}




    public LiveData<List<ModelHome>> getHome(int homeId){
        home = appDatabase.modelHomeDAO().getModelHomeById(homeId);

        return home;
    }


}
