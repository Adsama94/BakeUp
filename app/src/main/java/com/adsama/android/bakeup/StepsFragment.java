package com.adsama.android.bakeup;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

    private static final String BUNDLE_KEY = "bundle_key";
    private static final String BUNDLE_POSITION = "bundle_position";
    private static final String LAYOUT_STATE = "layout_state";
    @BindView(R.id.tv_recipe_steps_heading)
    TextView mStepsHeading;
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsRecyclerView;
    StepsAdapter mStepsAdapter;
    ArrayList<Steps> mStepsList;
    LinearLayoutManager stepsLayoutManager;
    private Parcelable mState;
    private boolean mTwoPane;

    public StepsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mState != null)
            stepsLayoutManager.onRestoreInstanceState(mState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mState = savedInstanceState.getParcelable(LAYOUT_STATE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        mStepsList = new ArrayList<>();
        mStepsList = extras.getParcelableArrayList("stepslist");
        mTwoPane = extras.getBoolean("tabLayout");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        mStepsHeading.setTypeface(EasyFonts.droidSerifBold(getContext()));
        stepsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepsAdapter = new StepsAdapter(mStepsList, getContext(), this);
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mState = stepsLayoutManager.onSaveInstanceState();
        outState.putParcelable(LAYOUT_STATE, mState);
    }

    @Override
    public void playStepVideo(Steps stepsModel, int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(BUNDLE_KEY, mStepsList);
            arguments.putInt(BUNDLE_POSITION, position);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, fragment).commit();
        } else {
            Intent i = new Intent(getContext(), DetailActivity.class);
            i.putParcelableArrayListExtra("stepsData", mStepsList);
            i.putExtra("stepsPosition", position);
            startActivity(i);
        }
    }
}