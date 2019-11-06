package com.junhyeoklee.paik_s_cookingsecretbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;

public class Intro extends Activity {
    private InterstitialAd mInterstitialAd;
    private Timer waitTimer = new Timer();
    private boolean interstitialCanceled ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1438205576140129/5031047208");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener()
                                      {
                                          @Override
                                          public void onAdClosed() {
                                              super.onAdClosed();
                                                doFunc();
                                          }
                                      }
        );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                } else{
                    doFunc();
                }
            }
        },3000);

//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                if (!interstitialCanceled) {
//                    waitTimer.cancel();
//                    runOnUiThread(new Runnable() {
//                        @Override public void run() {
//                            if (mInterstitialAd.isLoaded()) {
//                                mInterstitialAd.show();
//                            }
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                startMainActivity();
//            }
//        });

//        AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd = new InterstitialAd(getApplicationContext());
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // * 자신의 전면광고 단위 아이디
//        mInterstitialAd.loadAd(adRequest);
//
//        try{
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdClosed() {
//                        // 사용자가 광고를 닫으면 뒤로가기 이벤트를 발생시킨다.
//                        finish();
//                    }
//                });
//            } else {
//                Thread.sleep(1000);
//                startActivity(new Intent(Intro.this, MainActivity.class));
//                finish();
//            }
//        }
//        catch (InterruptedException e){
//            e.printStackTrace();
//        }


//        waitTimer = new Timer();
//        waitTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                interstitialCanceled = true;
//                Intro.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        startMainActivity();
//                    }
//                });
//            }
//        }, 2000);
    }

    private void doFunc() {
        startActivity(new Intent(Intro.this,MainActivity.class));
    }
    // end of onCreate implementation.

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    protected void onPause() {
//        waitTimer.cancel();
//        interstitialCanceled = true;
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else if (interstitialCanceled) {
//            startMainActivity();
//        }
//    }
}
