package com.junhyeoklee.paik_s_cookingsecretbook.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.junhyeoklee.paik_s_cookingsecretbook.Adapter.AdapterHome;
import com.junhyeoklee.paik_s_cookingsecretbook.R;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class HomeFragment extends Fragment {
    WebView webView;
    String source;
    private RecyclerView recyclerView;
    private ArrayList<ModelHome> list = new ArrayList();
    private final String detailURL = "https://paik-s-cookingsecretbook.tistory.com/m";

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        new Description().execute();

        return mView;
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://paik-s-cookingsecretbook.tistory.com/category/%EC%9A%94%EB%A6%AC%EB%B9%84%EC%B1%85").get();
                Elements mElementDataSize = doc.select("ul.list_horizontal").select("li"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.
                Log.e("dqwdq",mElementDataSize.text()) ;
                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    String Thumb;
                    String urlThumb;
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("li.list_horizontal_item").select("strong.title_post").text();
                    String my_imgUrl = elem.select("li.list_horizontal_item").select("div.thumbnail_zone").select("a.thumbnail_post").attr("style");
                    String href= elem.select("li.list_horizontal_item").select("div.thumbnail_zone").select("a.thumbnail_post").attr("href");
                    String release = elem.select("li.list_horizontal_item").select("a.link_article").text();
                    Thumb = my_imgUrl.replace("background-image:url('","");
                    urlThumb = Thumb.replace("') !important","");
                    String detail = detailURL + href;
                    Log.e("detailURL : ",detailURL+href);

                    list.add(new ModelHome(my_title, urlThumb,release,detail));
                }

                //추출한 전체 <li> 출력해 보자.
//                Log.d("debug :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            AdapterHome myAdapter = new AdapterHome(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }

}
