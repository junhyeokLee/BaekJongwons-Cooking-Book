package com.junhyeoklee.paik_s_cookingsecretbook.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;


@Database(entities = {ModelHome.class},version = 10 )

public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_Name = "cooks";
    private static AppDatabase instance;
    private static final Object OBJECT = new Object();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (OBJECT){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,DB_Name)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract ModelHomeDAO modelHomeDAO();
}
