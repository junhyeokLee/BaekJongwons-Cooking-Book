package com.junhyeoklee.paik_s_cookingsecretbook.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;
import com.junhyeoklee.paik_s_cookingsecretbook.db.AppDatabase;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    private LiveData<List<ModelHome>> modelHomeList;
    private LiveData<List<ModelHome>> modelHome;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());

        modelHomeList = appDatabase.modelHomeDAO().getAll();
    }

    public LiveData<List<ModelHome>> getModelHomes(){
        return  modelHomeList;
    }

    public LiveData<List<ModelHome>> getModelHome(int ModelHomeId){
        modelHome = appDatabase.modelHomeDAO().getModelHomeById(ModelHomeId);
        return  modelHome;
    }


}
