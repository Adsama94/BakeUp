package com.adsama.android.bakeup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsama.android.bakeup.Adapter.IngredientsAdapter;
import com.adsama.android.bakeup.Adapter.StepsAdapter;
import com.adsama.android.bakeup.Model.Ingredients;
import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.Model.Steps;
import com.adsama.android.bakeup.NetworkUtils.NetworkParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.tv_recipe_name)
    TextView mRecipeName;
    @BindView(R.id.tv_serving_count)
    TextView mRecipeSizeCount;
    @BindView(R.id.tv_step_count)
    TextView mRecipeStepCount;
    @BindView(R.id.iv_recipe_image)
    ImageView mRecipePlaceHolder;
    @BindView(R.id.cv_recipe_list)
    CardView mRecipeCardView;
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsRecyclerView;
    @BindView(R.id.recipe_detail_ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;
    ConnectivityManager connManager;
    NetworkInfo networkInfo;
    Recipes mRecipes;
    StepsAdapter mStepsAdapter;
    IngredientsAdapter mIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolBar.setTitle(R.string.nutella_pie);
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_pie);
        mRecipePlaceHolder.setImageResource(R.drawable.pie_place);
        mRecipePlaceHolder.setScaleType(ImageView.ScaleType.FIT_XY);
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            makeHttpRequest();
            updateIngredientsDetails();
            updateStepsDetails();
        } else {
            Snackbar snackbar = Snackbar.make(mNavigationView, getString(R.string.check_connection), Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            snackbar.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void updateRecipeDetails() {
        Picasso.with(getApplicationContext()).load(mRecipes.getImage()).into(mRecipePlaceHolder);
        mRecipeName.setText(mRecipes.getName());
        mRecipeSizeCount.setText(mRecipes.getServings());
        mRecipeStepCount.setText(String.valueOf(mRecipes.getSteps()));
    }

    private void updateIngredientsDetails() {
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<Ingredients>(), this);
        mIngredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
    }

    private void updateStepsDetails() {
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStepsAdapter = new StepsAdapter(new ArrayList<Steps>(), this);
        mStepsRecyclerView.setLayoutManager(stepsLayoutManager);
        mStepsRecyclerView.setAdapter(mStepsAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_pie) {
            mToolBar.setTitle(R.string.nutella_pie);
        } else if (id == R.id.nav_brownie) {
            mToolBar.setTitle(R.string.brownies);
        } else if (id == R.id.nav_yellow) {
            mToolBar.setTitle(R.string.yellow_cake);
        } else if (id == R.id.nav_cheese) {
            mToolBar.setTitle(R.string.cheesecake);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeHttpRequest() {
        new AsyncTask<Void, Void, List<Recipes>>() {
            @Override
            protected List<Recipes> doInBackground(Void... voids) {
                ArrayList<Recipes> recipeList = new ArrayList<>();
                JSONArray recipeArray = NetworkParser.getRecipeData();
                try {
                    for (int i = 0; i < recipeArray.length(); i++) {
                        JSONObject currentRecipe = recipeArray.getJSONObject(i);
                        int recipeId = currentRecipe.getInt("id");
                        String recipeName = currentRecipe.getString("name");
                        JSONArray ingredientsList = currentRecipe.getJSONArray("ingredients");
                        List<Ingredients> ingredientData = new ArrayList<>();
                        for (int j = 0; j < recipeArray.length(); j++) {
                            JSONObject currentIngredient = ingredientsList.getJSONObject(j);
                            int ingredientQuantity = currentIngredient.getInt("quantity");
                            String ingredientMeasure = currentIngredient.getString("measure");
                            String ingredientName = currentIngredient.getString("ingredient");
                            Ingredients ingredients = new Ingredients(ingredientQuantity, ingredientMeasure, ingredientName);
                            ingredientData.add(ingredients);
                        }
                        JSONArray stepsList = currentRecipe.getJSONArray("steps");
                        ArrayList<Steps> stepsData = new ArrayList<>();
                        for (int k = 0; k < stepsList.length(); k++) {
                            JSONObject currentStep = stepsList.getJSONObject(k);
                            int stepId = currentStep.getInt("id");
                            String stepShortDescription = currentStep.getString("shortDescription");
                            String stepLongDescription = currentStep.getString("description");
                            String stepVideoUrl = currentStep.getString("videoURL");
                            String stepImageUrl = currentStep.getString("thumbnailURl");
                            Steps steps = new Steps(stepId, stepShortDescription, stepLongDescription, stepVideoUrl, stepImageUrl);
                            stepsData.add(steps);
                        }
                        int recipeServings = currentRecipe.getInt("servings");
                        String recipeImage = currentRecipe.getString("image");
                        Recipes recipes = new Recipes(recipeId, recipeName, ingredientData, stepsData, recipeServings, recipeImage);
                        recipeList.add(recipes);
                        Log.e(LOG_TAG, "JSON DETAILS " + currentRecipe);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return recipeList;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}