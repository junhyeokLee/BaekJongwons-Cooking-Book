package com.junhyeoklee.paik_s_cookingsecretbook.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.junhyeoklee.paik_s_cookingsecretbook.Fragment.FavoriteFragment;
import com.junhyeoklee.paik_s_cookingsecretbook.Fragment.HomeFragment;
import com.junhyeoklee.paik_s_cookingsecretbook.R;


public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                return favoriteFragment;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
