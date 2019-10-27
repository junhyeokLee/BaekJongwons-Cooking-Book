package com.junhyeoklee.paik_s_cookingsecretbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.junhyeoklee.paik_s_cookingsecretbook.DetailAtivity;
import com.junhyeoklee.paik_s_cookingsecretbook.GlideApp;
import com.junhyeoklee.paik_s_cookingsecretbook.R;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.FavoritViewModel;
import com.junhyeoklee.paik_s_cookingsecretbook.view_model.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> implements Filterable {

    //데이터 배열 선언
    private List<ModelHome> list;
    private List<ModelHome> list2;
    private DetailAtivity getFavorite = new DetailAtivity();
    public boolean favoritHome;
    private Context context;
    private final static String homeItem = "homeItem";
    private static AdapterFavorite.MyClickListener sClickListener;
    private int mLastPosition = -1;
    public boolean favorite = false;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img, img_fav, img_fav_empty,snsBtn;
        private CheckBox favoritBtn;
        private TextView textView_title, textView_release;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_release = (TextView) itemView.findViewById(R.id.textView_release);
            favoritBtn = (CheckBox) itemView.findViewById(R.id.favoriteBtn);
            img_fav = (ImageView) itemView.findViewById(R.id.img_fav);
            img_fav_empty = (ImageView) itemView.findViewById(R.id.img_fav_empty);
            snsBtn = (ImageView)itemView.findViewById(R.id.snsBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        ModelHome itemPosition = list.get(position);
                        Intent intent = new Intent(context, DetailAtivity.class);
                        intent.putExtra(homeItem, itemPosition);
                        context.startActivity(intent);

                        Log.e("RecyclerPosition", "" + getAdapterPosition());
                    }
                }
            });

        }

        public Context getContext() {
            return context;
        }
    }

    public void setOnItemClickListener(AdapterFavorite.MyClickListener myClickListener) {
        sClickListener = myClickListener;
    }

    //생성자
    public AdapterFavorite(Context context, List<ModelHome> list) {
        this.list = list;
        this.context = context;
        list2 = new ArrayList<>(list);

    }


    @NonNull
    @Override
    public AdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_card, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavorite.ViewHolder holder, final int position) {
        ModelHome modelHome = (ModelHome) list.get(position);
        int id = list.get(position).getId();

        holder.textView_title.setText(modelHome.getTitle());
        holder.textView_release.setText(modelHome.getSub_title());

        holder.snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "[백종원의 요리비책]");
                intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.junhyeoklee.paik_s_cookingsecretbook");

                Intent chooser = Intent.createChooser(intent, "공유");
                holder.getContext().startActivity(chooser);

            }
        });

        String detailURL = list.get(position).getDetail_link();
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
        GlideApp.with(holder.itemView).load(list.get(position).getImg_url())
                .override(460, 520)
                .error(R.drawable.error_loading)
                .into(holder.imageView_img);


        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritViewModel favoriteMovieViewModel = ViewModelProviders.of((FragmentActivity) holder.getContext()).get(FavoritViewModel.class);
                favoriteMovieViewModel.deleteItem(id);
                list.remove(position);
                Toast.makeText((FragmentActivity) holder.getContext(), "즐겨찾기 해제", Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();

            }
        });


        HomeViewModel movieViewModel = ViewModelProviders.of((FragmentActivity) holder.getContext()).get(HomeViewModel.class);
        movieViewModel.getHome(id).observe((FragmentActivity) holder.getContext(), new Observer<List<ModelHome>>() {
            @Override
            public void onChanged(List<ModelHome> modelHomes) {

                holder.img_fav.setVisibility(View.VISIBLE);
                holder.img_fav_empty.setVisibility(View.GONE);

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, boolean addItem);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        if (list.size() > 0) {
            list.clear();
        }
    }

    // fillter

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelHome> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(list2);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelHome modelHome : list2) {
                    if (modelHome.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(modelHome);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

}