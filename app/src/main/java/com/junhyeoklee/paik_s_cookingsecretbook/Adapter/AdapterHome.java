package com.junhyeoklee.paik_s_cookingsecretbook.Adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.junhyeoklee.paik_s_cookingsecretbook.DetailAtivity;
import com.junhyeoklee.paik_s_cookingsecretbook.GlideApp;
import com.junhyeoklee.paik_s_cookingsecretbook.R;
import com.junhyeoklee.paik_s_cookingsecretbook.model.ModelHome;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<ModelHome> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_release, texView_director;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_release = (TextView) itemView.findViewById(R.id.textView_release);

        }
    }

    //생성자
    public AdapterHome(ArrayList<ModelHome> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public AdapterHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.ViewHolder holder, final int position) {

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_release.setText(String.valueOf(mList.get(position).getSub_title()));
        String detailURL = mList.get(position).getDetail_link();
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
        GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
                .override(460,520)
                .error(R.drawable.error_loading)
                .into(holder.imageView_img);


        holder.textView_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailAtivity.class);
                String detailLink = mList.get(position).getDetail_link();
                intent.putExtra("Detail",detailLink);
                view.getContext().startActivity(intent);
                Log.e("Detail",detailLink);
            }
        });
        holder.textView_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DetailAtivity.class);
                String detailLink = mList.get(position).getDetail_link();
                intent.putExtra("Detail",detailLink);
                view.getContext().startActivity(intent);
                Log.e("Detail",detailLink);
            }
        });
        holder.imageView_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DetailAtivity.class);
                String detailLink = mList.get(position).getDetail_link();
                intent.putExtra("Detail",detailLink);
                view.getContext().startActivity(intent);
                Log.e("Detail",detailLink);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}