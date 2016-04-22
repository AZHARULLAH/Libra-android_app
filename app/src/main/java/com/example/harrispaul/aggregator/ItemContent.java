package com.example.harrispaul.aggregator;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Harrispaul on 3/25/2016.
 */
public class ItemContent implements Serializable {
    private String title = "";
    private String imgid = "";
    private String description = "";
    private String brand="";
    private String Maxprice = "";
    private String Sellingprice = "";
    private String id="";
    public void setBrand (String id) {
        this.brand = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setSellingPrice(String price ) {
        this.Sellingprice =price;
        Log.i("price","price is" + this.Sellingprice);
    }

    public String getSellingPrice() {
        return Sellingprice;
    }
    public void setMaxPrice(String price ) {
        this.Maxprice =price;
        Log.i("price","price is" + this.Maxprice);
    }

    public String getmaxPrice() {
        return Maxprice;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getImgid() {
        return imgid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}



