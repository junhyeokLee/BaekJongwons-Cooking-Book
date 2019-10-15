package com.junhyeoklee.paik_s_cookingsecretbook;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailAtivity extends AppCompatActivity {

    private Intent detailIntent;
    private String DetailUrl;
    private WebView webView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = ni.isConnected();
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isMobileConn = ni.isConnected();


        if (!isWifiConn && !isMobileConn) {
            Toast.makeText(this, "인터넷에 접속되어 있지 않습니다!", Toast.LENGTH_SHORT).show();
            finish();//액티비티 종료
        }
        else{
            setContentView(R.layout.activity_detail_ativity);
            progress = (ProgressBar) findViewById(R.id.web_progress);
            webView = findViewById(R.id.webview);
            detailIntent = getIntent();
            DetailUrl = detailIntent.getStringExtra("Detail");
            webView.loadUrl(DetailUrl);
            Log.e("DetailActivity",DetailUrl);


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
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('kakao_head')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('link_cmtwrite')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('section_differ')[0].style.display=\"none\"; " +
                            "})()");
//                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('section_relation')[0].style.display=\"none\"; " +
////                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsById('comment')[0].style.display=\"none\"; " +
                            "})()");
                    webView.loadUrl("javascript:(function() { "+"document.getElementsByClassName('blogview_head')[0].style.display=\"none\"; " +
                            "})()");


                    progress.setVisibility(View.GONE);
                }
            });

        }
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(DetailAtivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            try {

                detailIntent = getIntent();
                DetailUrl = detailIntent.getStringExtra("Detail");
                Log.e("DetailActivity",DetailUrl);
                Document doc = Jsoup.connect(DetailUrl).get();
                Elements mElementDataSize = doc.select("div.blogview_info"); //필요한 녀석만 꼬집어서 지정
                mElementDataSize.remove();
//                for(Element element : doc.select("div.k_head")){
//                    Log.e("div",element.toString());
//                    element.remove();
//                }


//                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.
//                Log.e("dqwdq",mElementDataSize.text()) ;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }

}

class MyWebClient extends WebViewClient{

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl("javascript:(function() { " +
                "document.getElementsByTagName('header')[0].style.display=\"none\"; " +
                "})()");
    }
}