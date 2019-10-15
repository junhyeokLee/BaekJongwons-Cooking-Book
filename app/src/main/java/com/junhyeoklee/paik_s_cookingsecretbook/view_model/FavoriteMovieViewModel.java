package com.junhyeoklee.paik_s_cookingsecretbook.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.junhyeoklee.paik_s_cookingsecretbook.db.AppDatabase;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

public class FavoriteMovieViewModel extends AndroidViewModel {

    AppDatabase appDatabase;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
    }

    private static class Insert extends AsyncTask<ModelHome, Void, Void> {

        private AppDatabase database;

        public Insert(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(ModelHome... movies) {
            database.modelHomeDAO().insertmodelHomeId(movies[0]);
            return null;
        }
    }

    public void insertItem(ModelHome movie){
        new Insert(appDatabase).execute(movie);
    }

    private static class Delete extends AsyncTask<Integer, Void, Void> {

        private AppDatabase database;

        public Delete(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int position = integers[0];
            database.modelHomeDAO().deletemodelHomeIdById(position);
            return null;
        }
    }

    public void deleteItem(int id){
        new Delete(appDatabase).execute(id);
    }

}
