package com.adsama.android.bakeup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.adsama.android.bakeup.Model.Steps;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import butterknife.BindView;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "com.adsama.android.bakeup";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.exoPlayer)
    SimpleExoPlayerView mStepExoPlayerView;
    @BindView(R.id.tv_step_instruction)
    TextView mStepInstructionTextView;
    private SimpleExoPlayer mStepExoPlayer;
    private ArrayList<Steps> mStepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        mStepsList = getIntent().getParcelableArrayListExtra("stepsData");
        int stepsPosition = getIntent().getIntExtra("stepsPosition", 0);
        Toast.makeText(this, "RECEIVED POSITION " + stepsPosition, Toast.LENGTH_LONG).show();
    }
}
