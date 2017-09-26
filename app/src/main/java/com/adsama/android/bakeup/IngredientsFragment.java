package com.adsama.android.bakeup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsama.android.bakeup.Adapter.IngredientsAdapter;
import com.adsama.android.bakeup.Model.Ingredients;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    private static final String LOG_TAG = IngredientsFragment.class.getSimpleName();

    @BindView(R.id.tv_recipe_ingredient_heading)
    TextView mIngredientHeading;
    @BindView(R.id.recipe_detail_ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;
    IngredientsAdapter mIngredientsAdapter;
    ArrayList<Ingredients> mIngredientsList;

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        mIngredientsList = new ArrayList<>();
        mIngredientsList = extras.getParcelableArrayList("arraylist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientHeading.setTypeface(EasyFonts.droidSerifBold(getContext()));
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mIngredientsRecyclerView.setLayoutManager(ingredientLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(mIngredientsList, getContext());
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
        return rootView;
    }
}