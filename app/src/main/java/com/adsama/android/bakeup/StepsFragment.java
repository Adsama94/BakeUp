package com.adsama.android.bakeup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsama.android.bakeup.Adapter.StepsAdapter;
import com.adsama.android.bakeup.Model.Steps;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment implements StepsAdapter.Callbacks {

    private static final String LOG_TAG = StepsFragment.class.getSimpleName();

    @BindView(R.id.tv_recipe_steps_heading)
    TextView mStepsHeading;
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsRecyclerView;
    StepsAdapter mStepsAdapter;
    ArrayList<Steps> mStepsList;

    public StepsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        mStepsList = new ArrayList<>();
        mStepsList = extras.getParcelableArrayList("stepslist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        mStepsHeading.setTypeface(EasyFonts.droidSerifBold(getContext()));
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepsAdapter = new StepsAdapter(mStepsList, getContext(), this);
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        return rootView;
    }

    @Override
    public void playStepVideo(Steps stepsModel, int position) {
        Intent i = new Intent(getContext(), DetailActivity.class);
        i.putParcelableArrayListExtra("stepsData", mStepsList);
        i.putExtra("stepsPosition", position);
        startActivity(i);
    }
}