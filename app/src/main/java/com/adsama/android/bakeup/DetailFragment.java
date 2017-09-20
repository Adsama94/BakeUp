package com.adsama.android.bakeup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.tv_recipe_ingredient_heading)
    TextView mIngredientsHeader;
    @BindView(R.id.tv_recipe_steps_heading)
    TextView mStepsHeader;
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsList;


    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientsHeader.setTypeface(EasyFonts.droidSerifRegular(getContext()));
        mStepsHeader.setTypeface(EasyFonts.droidSerifRegular(getContext()));
        return rootView;
    }

}
