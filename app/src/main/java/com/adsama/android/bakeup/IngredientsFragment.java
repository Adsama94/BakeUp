package com.adsama.android.bakeup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsama.android.bakeup.Adapter.IngredientsAdapter;
import com.adsama.android.bakeup.Model.Ingredients;
import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncListener;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment implements NetworkAsyncListener {

    @BindView(R.id.tv_recipe_ingredient_heading)
    TextView mIngredientHeading;
    @BindView(R.id.recipe_detail_ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;
    IngredientsAdapter mIngredientsAdapter;
    List<Recipes> mRecipes;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientHeading.setTypeface(EasyFonts.droidSerifBold(getContext()));
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mIngredientsRecyclerView.setLayoutManager(ingredientLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<Ingredients>(), getContext());
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
        return rootView;
    }

    @Override
    public void returnRecipeList(List<Recipes> recipesList) {
        mRecipes = recipesList;
    }

    public boolean setIngredientsData(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_pie) {
            mIngredientsAdapter.getIngredients().get(0);
        } else if (id == R.id.nav_brownie) {
            mIngredientsAdapter.getIngredients().get(1);
        } else if (id == R.id.nav_yellow) {
            mIngredientsAdapter.getIngredients().get(2);
        } else if (id == R.id.nav_cheese) {
            mIngredientsAdapter.getIngredients().get(3);
        }
        return true;
    }
}
