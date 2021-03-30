package com.tpeleg.heroeshackathon2021;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class LauncherActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mTextView = (TextView) findViewById(R.id.main_text);

        // Enables Always-on
        setAmbientEnabled();
    }
}