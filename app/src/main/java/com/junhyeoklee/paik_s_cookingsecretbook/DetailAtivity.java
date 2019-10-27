package com.junhyeoklee.paik_s_cookingsecretbook;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.FavoritViewModel;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.HomeViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class DetailAtivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progress;
    private ImageView favoritBtn;
    private final static String HOME_ITEM = "homeItem";

    private final static String HOME = "home";
    private final static String FAVORITE = "favorite";

    ModelHome modelHome;
    private String DetailUrl,mTitle;

    int mModelHome_id;
    public boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayUI();
//        loadFavorite();

        if(savedInstanceState != null){
            modelHome = savedInstanceState.getParcelable(HOME);
            favorite = savedInstanceState.getBoolean(FAVORITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favorit,menu);
        menu.findItem(R.id.favoriteBtn).getActionView();


        HomeViewModel movieViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        movieViewModel.getHome(modelHome.getId()).observe(this, new Observer<List<ModelHome>>() {
            @Override
            public void onChanged(List<ModelHome> modelHomes) {
                if(modelHomes != null && modelHomes.size() == 0){
                    favorite = false;
                }else {
                    favorite = true;
                }
                if(favorite){
//                    favoritBtn.setImageResource(R.drawable.star);
                    menu.findItem(R.id.favoriteBtn).setIcon(R.drawable.star);
                }else {
//                    favoritBtn.setImageResource(R.drawable.star_empty);
                    menu.findItem(R.id.favoriteBtn).setIcon(R.drawable.star_empty2);
                }
            }

        });
        menu.findItem(R.id.favoriteBtn).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(favorite) {
                    menuItem.setIcon(R.drawable.star);
                    FavoritViewModel favoriteMovieViewModel = ViewModelProviders.of(DetailAtivity.this).get(FavoritViewModel.class);
                    favoriteMovieViewModel.deleteItem(modelHome.getId());
                    favorite = false;
                }
                else{
                    menuItem.setIcon(R.drawable.star_empty2);
                    ModelHome movieModel = new ModelHome(modelHome.getId(),modelHome.getTitle(),modelHome.getImg_url(),modelHome.getSub_title(),modelHome.getDetail_link());
                    FavoritViewModel favoriteMovieViewModel = ViewModelProviders.of(DetailAtivity.this).get(FavoritViewModel.class);
                    favoriteMovieViewModel.insertItem(movieModel);
                    favorite = true;
                }

                return false;

            }
        });

        MenuItem item = menu.add(0,0,0,"알림설정");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent lIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(lIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent lIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(lIntent);
    }

    //    private void loadFavorite() {
//
//        HomeViewModel movieViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
//        movieViewModel.getHome(modelHome.getId()).observe(this, new Observer<List<ModelHome>>() {
//            @Override
//            public void onChanged(List<ModelHome> modelHomes) {
//                if(modelHomes != null && modelHomes.size() == 0){
//                    favorite = false;
//                }else {
//                    favorite = true;
//                }
//
//                updatefavorite();
//
//            }
//
//        });
//    }

    private void displayUI(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = ni.isConnected();
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isMobileConn = ni.isConnected();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (!isWifiConn && !isMobileConn) {
            Toast.makeText(this, "인터넷에 접속되어 있지 않습니다!", Toast.LENGTH_SHORT).show();
            finish();//액티비티 종료
        }
        else{
            setContentView(R.layout.activity_detail_ativity);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            modelHome = getIntent().getParcelableExtra(HOME_ITEM);

            progress = (ProgressBar) findViewById(R.id.web_progress);
//            favoritBtn = (ImageView) findViewById(R.id.favoritBtn);
//            favoritBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    clickFavorite();
//
//                }
//            });

            webView = findViewById(R.id.webview);

            mTitle = modelHome.getTitle();
            DetailUrl = modelHome.getDetail_link();

            Log.e("mTitle DetailURL = ",mTitle+DetailUrl);

            webView.loadUrl(DetailUrl);



//            new DetailAtivity.Description().execute();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(DetailUrl);
                    return true;
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progress.setVisibility(View.VISIBLE);
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('by_blog')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('blogview_info')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('container_postbtn #post_button_group')[0].style.display=\"none\"; " +
                            "})()");
//                    webView.loadUrl("javascript:(function() { "+"document.getElementsById('kakaoContent')[0].style.display=\"initial\"; " +
//                            "})()");
                    webView.loadUrl("javascript:(function(){ document.body.style.marginTop = '-48px'})();");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('kakao_head')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('link_cmtwrite')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('section_differ')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('section_relation')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsById('comment')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('blogview_head')[0].style.display=\"none\"; " +
                            "})()");


                    progress.setVisibility(View.GONE);
                }
            });

        }
    }
//    private void updatefavorite() {
//        if(favorite){
//            favoritBtn.setImageResource(R.drawable.star);
//
//        }else {
//            favoritBtn.setImageResource(R.drawable.star_empty);
//        }
//    }

//    private void clickFavorite(){
//        if(favorite){
//            FavoritViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoritViewModel.class);
//            favoriteMovieViewModel.deleteItem(modelHome.getId());
//            favorite = false;
//        }else {
//
//            ModelHome movieModel = new ModelHome(modelHome.getId(),modelHome.getTitle(),modelHome.getImg_url(),modelHome.getSub_title(),modelHome.getDetail_link());
//
//            FavoritViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoritViewModel.class);
//            favoriteMovieViewModel.insertItem(movieModel);
//            favorite = true;
//        }
//
//        updatefavorite();
//    }


    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
