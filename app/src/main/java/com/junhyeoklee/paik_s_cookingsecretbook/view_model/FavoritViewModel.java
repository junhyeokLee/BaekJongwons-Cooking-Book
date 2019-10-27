package com.junhyeoklee.paik_s_cookingsecretbook.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.junhyeoklee.paik_s_cookingsecretbook.db.AppDatabase;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

public class FavoritViewModel extends AndroidViewModel {

    AppDatabase appDatabase;

    public FavoritViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
    }

    private static class Insert extends AsyncTask<ModelHome, Void, Void> {

        private AppDatabase database;

        public Insert(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(ModelHome... homes) {
            database.modelHomeDAO().insertmodelHomeId(homes[0]);
            return null;
        }
    }

    public void insertItem(ModelHome home){
        new Insert(appDatabase).execute(home);
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



    private static class Update extends AsyncTask<ModelHome, Void, Void> {

        private AppDatabase database;

        public Update(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(ModelHome... homes) {
            database.modelHomeDAO().updatemodelHomeId(homes[0]);
            return null;
        }
    }

    public void updateItem(ModelHome modelHome){
        new Update(appDatabase).execute(modelHome);
    }

}
