package com.adsama.android.bakeup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.adsama.android.bakeup.Model.Steps;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY = "bundle_key";
    private static final String BUNDLE_POSITION = "bundle_position";
    private ArrayList<Steps> mStepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        mStepsList = getIntent().getParcelableArrayListExtra("stepsData");
        int stepsPosition = getIntent().getIntExtra("stepsPosition", 0);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(BUNDLE_KEY, mStepsList);
            arguments.putInt(BUNDLE_POSITION, stepsPosition);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment_container, fragment).commit();
        }
    }
}