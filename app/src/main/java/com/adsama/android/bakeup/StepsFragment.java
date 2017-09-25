package com.adsama.android.bakeup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsama.android.bakeup.Adapter.StepsAdapter;
import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.Model.Steps;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncListener;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment implements NetworkAsyncListener {

    @BindView(R.id.tv_recipe_steps_heading)
    TextView mStepsHeading;
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsRecyclerView;
    StepsAdapter mStepsAdapter;

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        mStepsHeading.setTypeface(EasyFonts.droidSerifBold(getContext()));
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepsAdapter = new StepsAdapter(new ArrayList<Steps>(), getContext());
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        return rootView;
    }

    @Override
    public void returnRecipeList(List<Recipes> recipesList) {

    }
}
