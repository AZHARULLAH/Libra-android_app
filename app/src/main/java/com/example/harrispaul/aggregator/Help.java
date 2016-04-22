package com.example.harrispaul.aggregator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Harrispaul on 4/19/2016.
 */
public class Help extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

    }
    public void dealIntent(View v){
        Intent activityChangeIntent = new Intent(Help.this, Deals.class);
        Help.this.startActivity(activityChangeIntent);
    }
    public void HomeIntent(View v){
        Intent activityChangeIntent = new Intent(Help.this, MainActivity.class);
        Help.this.startActivity(activityChangeIntent);
    }
    public void HelpIntent(View v){
        return;
    }
}
