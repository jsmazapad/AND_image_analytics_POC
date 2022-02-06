package com.jsm.and_image_analytics_poc.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.ui.Camera2BasicFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }
}

