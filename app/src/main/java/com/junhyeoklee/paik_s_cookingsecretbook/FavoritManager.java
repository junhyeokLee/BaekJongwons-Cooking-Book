package com.junhyeoklee.paik_s_cookingsecretbook;

import android.content.Context;

import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

import java.util.ArrayList;
import java.util.List;

public class FavoritManager {

    private Context mContext = null;

    private ModelHome modelHome = new ModelHome();

    private List<ModelHome> modelHomeList = new ArrayList<>();



    // -------------------------------------------------------------
    // start singleton

    private static FavoritManager gHpGameManager;

    public static FavoritManager get() {
        if (null == gHpGameManager) {
            gHpGameManager = new FavoritManager();
        }
        return gHpGameManager;
    }

    private FavoritManager() {
    }
    // -------------------------------------------------------------


    public ModelHome getModelHome() {
        return modelHome;
    }

    public void setModelHome(ModelHome modelHome) {
        this.modelHome = modelHome;
    }

    public List<ModelHome> getModelHomeList() {
        return modelHomeList;
    }

    public void setModelHomeList(List<ModelHome> modelHomeList) {
        this.modelHomeList = modelHomeList;
    }
}
