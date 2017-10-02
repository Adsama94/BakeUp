package com.adsama.android.bakeup.NetworkUtils;

import com.adsama.android.bakeup.Model.Recipes;

import java.util.ArrayList;

public interface NetworkAsyncListener {
    void returnRecipeList(ArrayList<Recipes> recipesList);
}
