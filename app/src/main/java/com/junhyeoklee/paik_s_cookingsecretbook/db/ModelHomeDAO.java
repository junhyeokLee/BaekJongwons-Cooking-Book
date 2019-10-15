package com.junhyeoklee.paik_s_cookingsecretbook.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

import java.util.List;

@Dao
public interface ModelHomeDAO {

    @Query("SELECT * FROM cooks")
    LiveData<List<ModelHome>> getAll();

    @Query("SELECT * FROM cooks WHERE id = :modelHomeId")
    LiveData<List<ModelHome>> getModelHomeById(int modelHomeId);

    @Insert
    void insertmodelHomeId(ModelHome modelHomeId);

    @Query("DELETE FROM cooks WHERE id = :modelHomeId")
    void deletemodelHomeIdById(int modelHomeId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatemodelHomeId(ModelHome modelHomeId);

}
