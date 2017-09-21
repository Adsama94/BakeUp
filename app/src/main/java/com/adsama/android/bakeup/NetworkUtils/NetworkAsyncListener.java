package com.adsama.android.bakeup.NetworkUtils;

import com.adsama.android.bakeup.Model.Recipes;

import java.util.List;

public interface NetworkAsyncListener {
    void returnRecipeList(List<Recipes> recipesList);
}
