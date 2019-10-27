package com.junhyeoklee.paik_s_cookingsecretbook.Fragment;

import android.os.Build;
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
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.junhyeoklee.paik_s_cookingsecretbook.Adapter.AdapterFavorite;
import com.junhyeoklee.paik_s_cookingsecretbook.R;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.HomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    AdapterFavorite adapterFavorite;
    List<ModelHome> modelHomes = new ArrayList<>();
    private RecyclerView mRecyclerList;
    private final static String FAVORITLIST = "favoritList";
    private final static String HOMELIST = "homeList";
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(HOMELIST, (ArrayList<? extends Parcelable>) modelHomes);
        super.onSaveInstanceState(outState);
    }

    public FavoriteFragment() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        setHasOptionsMenu(true);

        mRecyclerList = (RecyclerView)mView.findViewById(R.id.favoritRecyclerList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_layout);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerList.setLayoutManager(layoutManager);
        mRecyclerList.setHasFixedSize(true);

        adapterFavorite = new AdapterFavorite(getContext(),modelHomes);
        getFavorite();

        mRecyclerList.setAdapter(adapterFavorite);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffle();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterFavorite.getFilter().filter(newText);
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getFavorite(){

        HomeViewModel movieViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        movieViewModel.getHomes().observe(this, new Observer<List<ModelHome>>() {
            @Override
            public void onChanged(@Nullable List<ModelHome> homes) {

                modelHomes.clear();


                for (ModelHome favorite : homes) {
                    ModelHome movie = new ModelHome(favorite.getId(),favorite.getTitle(),
                            favorite.getImg_url(), favorite.getSub_title(), favorite.getDetail_link());

                    modelHomes.add(movie);

                    Log.e("LIST AMOUNT =",""+movie.getId());
                }
            }
        });
    }

    public void shuffle(){
        Collections.shuffle(modelHomes,new Random(System.currentTimeMillis()));
        AdapterFavorite adapterFavorite = new AdapterFavorite(getContext(),modelHomes);
        mRecyclerList.setAdapter(adapterFavorite);
        mSwipeRefreshLayout.setRefreshing(false);


    }





}
