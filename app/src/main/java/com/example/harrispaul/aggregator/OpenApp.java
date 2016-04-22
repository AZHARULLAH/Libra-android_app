package com.example.harrispaul.aggregator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;

/**
 * Created by Harrispaul on 3/14/2016.
 */
public class OpenApp extends Activity {
    public void onCreate(Bundle savedinstances) {
        super.onCreate(savedinstances);
        setContentView(R.layout.openapp);
        Thread x = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                } catch (InterruptedException i) {
                    i.printStackTrace();

                } finally {
                    Intent activiyt = new Intent(OpenApp.this, MainActivity.class);
                    activiyt.putExtra("message","");
                    startActivity(activiyt);
                }
            }
        };
        x.start();
    }
    public void onPause() {
        super.onPause();
        finish();
    }
}


