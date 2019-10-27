package com.junhyeoklee.paik_s_cookingsecretbook.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Toast;


import com.junhyeoklee.paik_s_cookingsecretbook.Adapter.AdapterHome;
import com.junhyeoklee.paik_s_cookingsecretbook.DetailAtivity;
import com.junhyeoklee.paik_s_cookingsecretbook.FavoritManager;
import com.junhyeoklee.paik_s_cookingsecretbook.R;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.FavoritViewModel;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.HomeViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private final static String HOMELIST = "homeList";

    WebView webView;
    String source;
    private RecyclerView recyclerView;
    private List<ModelHome> list = new ArrayList();
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterHome mAdapter;
    private final String detailURL = "https://paik-s-cookingsecretbook.tistory.com/m";
    private int id;
    private View mView;
    private DetailAtivity getFavorit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList(HOMELIST);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(HOMELIST, (ArrayList<? extends Parcelable>) list);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_layout);
        list.clear();
        new Description().execute();
        return mView;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return false;
            }
        });


        MenuItem item = menu.add(0, 0, 0, "알림설정");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private class Description extends AsyncTask<Void, Void, Void> {
        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            try {
                int i = 0;
                Document doc = Jsoup.connect("https://paik-s-cookingsecretbook.tistory.com/category/%EC%9A%94%EB%A6%AC%EB%B9%84%EC%B1%85").get();
                Elements mElementDataSize = doc.select("ul.list_horizontal").select("li"); //필요한 녀석만 꼬집어서 지정

                for (Element elem : mElementDataSize) { //이렇게 요긴한 기능이
                    Log.e("home size",""+mElementDataSize.size());
                    String Thumb;
                    String urlThumb;
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("li.list_horizontal_item").select("strong.title_post").text();
                    String my_imgUrl = elem.select("li.list_horizontal_item").select("div.thumbnail_zone").select("a.thumbnail_post").attr("style");
                    String href = elem.select("li.list_horizontal_item").select("div.thumbnail_zone").select("a.thumbnail_post").attr("href");
                    String release = elem.select("li.list_horizontal_item").select("a.link_article").text();
                    Thumb = my_imgUrl.replace("background-image:url('", "");
                    urlThumb = Thumb.replace("') !important", "");
                    String detail = detailURL + href;

                    String result = href.substring(href.lastIndexOf("/") + 1);
//                    href = href.replaceAll("[^0-9]", "");
                    int idx = href.indexOf("?");
                    String id_value = href.substring(0, idx);
                    String id;
                    id = id_value.replaceAll("[^0-9]", "");

                    Log.e("id", id);

                    ModelHome modelHome = new ModelHome();

                      modelHome = new ModelHome(Integer.parseInt(id), my_title, urlThumb, release, detail);


                    list.add(modelHome);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            mAdapter = new AdapterHome(getContext(), list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            Handler handler = new Handler();

            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            };
            handler.post(r);

            recyclerView.setAdapter(mAdapter);


            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    shuffle();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

//            progressDialog.dismiss();


        }
    }

    public void shuffle() {
        Collections.shuffle(list, new Random(System.currentTimeMillis()));
        mAdapter = new AdapterHome(getContext(), list);
        recyclerView.setAdapter(mAdapter);

    }

}
