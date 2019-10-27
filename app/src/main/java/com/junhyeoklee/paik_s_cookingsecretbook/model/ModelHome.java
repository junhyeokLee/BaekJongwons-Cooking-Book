package com.junhyeoklee.paik_s_cookingsecretbook.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "cooks")
public class ModelHome implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("img_url")
    @Expose
    private String img_url;

    @SerializedName("detail_link")
    @Expose
    private String detail_link;

    @SerializedName("sub_title")
    @Expose
    private String sub_title;

    @SerializedName("favorit")
    @Expose
    private boolean favorit = false;


    public ModelHome(){

    }

    public ModelHome(@NonNull Integer id ,String title,String img_url,String sub_title,String detail_link) {
        this.id = id;
        this.title = title;
        this.img_url = img_url;
        this.detail_link = detail_link;
        this.sub_title = sub_title;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public boolean isFavorit() {
        return favorit;
    }

    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }

    protected ModelHome(Parcel in){
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        img_url = in.readString();
        sub_title = in.readString();
        detail_link = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        parcel.writeString(img_url);
        parcel.writeString(sub_title);
        parcel.writeString(detail_link);
    }

    public static final Parcelable.Creator<ModelHome> CREATOR = new Creator<ModelHome>() {
        @Override
        public ModelHome createFromParcel(Parcel in) {
            return new ModelHome(in);
        }

        @Override
        public ModelHome[] newArray(int size) {
            return new ModelHome[size];
        }
    };

}
