package com.example.harrispaul.aggregator;

import java.io.Serializable;

/**
 * Created by Harrispaul on 4/19/2016.
 */
public class Checkbox implements Serializable {
    private String name = "";
    private Integer toggle=0;
    public void setName (String id) {
        this.name = id;
    }
    public String getName() {
        return name;
    }
    public void setToggle (Integer id) {
        this.toggle = id;
    }
    public Integer getToggle() {
        return toggle;
    }

}
