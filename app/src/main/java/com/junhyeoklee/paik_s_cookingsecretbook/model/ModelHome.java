package com.junhyeoklee.paik_s_cookingsecretbook.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "cooks")

public class ModelHome {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @Ignore
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

    public ModelHome(){

    }

    public ModelHome(@NonNull Integer id,String title,String img_url,String sub_title,String detail_link) {
        this.id = id;
        this.title = title;
        this.img_url = img_url;
        this.detail_link = detail_link;
        this.sub_title = sub_title;

    }
    public ModelHome(String title,String img_url,String sub_title,String detail_link) {
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
}
