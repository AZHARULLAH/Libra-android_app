package com.example.harrispaul.aggregator;

import java.util.ArrayList;

/**
 * Created by saksham on 21/4/16.
 */
public class Searchitem {
    private ArrayList<Searchitem> dotdList;
    private String title, description, url;
    private ArrayList<ImageLink> imageUrls;

    public ArrayList<ImageLink> getImageUrls() {
        return imageUrls;
    }

    public ArrayList<Searchitem> getItemList() {
        return dotdList;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public class ImageLink {
        private String url;

        public String getUrl() {
            return url;
        }
    }


}
