package com.example.harrispaul.aggregator;

import android.graphics.Bitmap;

/**
 * Created by Harrispaul on 4/4/2016.
 */
public class ImageId {
    private Bitmap bitmap;
    private String position;
    public ImageId(Bitmap bm,String id){
        this.bitmap=bm;
        this.position=id;
    }

    public String getId(){
        return position;
    }
    public Bitmap getBm(){
        return bitmap;
    }

}
