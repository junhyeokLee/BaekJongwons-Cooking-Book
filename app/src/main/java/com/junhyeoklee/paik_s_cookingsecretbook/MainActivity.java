package com.junhyeoklee.paik_s_cookingsecretbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.junhyeoklee.paik_s_cookingsecretbook.Adapter.TabPagerAdapter;
import com.junhyeoklee.paik_s_cookingsecretbook.Fragment.FavoriteFragment;
import com.junhyeoklee.paik_s_cookingsecretbook.Fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // 애드몹 광고
   private InterstitialAd mInterstitialAd;
   private AdView adView;

    private HomeFragment homeFragment = new HomeFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private String TistoryAceessKey ;
    private BottomNavigationView menuBawah;
    private SharedPreferences preferenceManager;

    // layout main2
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter tabPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1438205576140129/5031047208"); // * 자신의 전면광고 단위 아이디
        mInterstitialAd.loadAd(adRequest);


//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//            MobileAds.initialize(this,
//                    "ca-app-pub-3940256099942544~3347511713");
//            mInterstitialAd = new InterstitialAd(this);
//            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    // Load the next interstitial.
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                }
//
//            });

//        //구글애드몹
//        MobileAds.initialize(this,
//                "ca-app-pub-3940256099942544~3347511713");
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.show();
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
        //        MobileAds.initialize(this, getString(R.string.front_ad_unit_id));
//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        if(mInterstitialAd.isLoaded()){
//            mInterstitialAd.show();
//        }
//        else{
//            Log.e("Ad Error","애드몹광고 에러");
//        }

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sync = preferenceManager.getBoolean("sync",true);

        if(sync == true) {
            FirebaseMessaging.getInstance().subscribeToTopic("send");
        }
        else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("send");

        }
//            FirebaseInstanceId.getInstance().getInstanceId()
//                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w("FCM Log", "getInstanceId failed", task.getException());
//                                return;
//                            }
//                            String token = task.getResult().getToken();
//                            Log.e("FCM Log", "FCM 토큰:" + token);
////                    Toast.makeText(MainActivity.this,token,Toast.LENGTH_SHORT).show();
//                        }
//                    });
        ActionBar ab = getSupportActionBar();
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle("");
        ab.setLogo(R.drawable.paiktoolbar4);

        ab.setElevation(0);
        // layout main2
        viewPager = findViewById(R.id.viewPager);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("요리비책");
        tabLayout.getTabAt(1).setText("보관함");

        if(!internetConnected()){
            Toast.makeText(MainActivity.this, "Please ensure your internet is connection and try again..", Toast.LENGTH_LONG).show();
            networkDialog();
        }
    }

    private void networkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Connect to Internet")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean internetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        return networkInfo != null && networkInfo.isConnected();
    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // 사용자가 광고를 닫으면 뒤로가기 이벤트를 발생시킨다.
                    finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,fragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 1:
                refresh();
                tabLayout.getTabAt(0).setText("요리비책");
                tabLayout.getTabAt(1).setText("보관함");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void refresh() {
        tabPagerAdapter.notifyDataSetChanged();

    }

}
