package com.adsama.android.bakeup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncListener;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncTask;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NetworkAsyncListener {

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
    ConnectivityManager connManager;
    NetworkInfo networkInfo;
    List<Recipes> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(i);
            }
        });
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.ingredients_fragment_container, ingredientsFragment).commit();
        StepsFragment stepsFragment = new StepsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.steps_fragment_container, stepsFragment).commit();
        mToolBar.setTitle(R.string.nutella_pie);
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_pie);
        mRecipeName.setText(getString(R.string.nutella_pie));
        mRecipeName.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeSizeCount.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeStepCount.setTypeface(EasyFonts.droidSerifBold(this));
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            NetworkAsyncTask httpRequest = new NetworkAsyncTask(this);
            httpRequest.execute();
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
            mRecipeName.setText(mRecipes.get(0).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipes.get(0).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipes.get(0).getSteps().size()));
        } else if (id == R.id.nav_brownie) {
            mToolBar.setTitle(R.string.brownies);
            mRecipeName.setText(mRecipes.get(1).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipes.get(1).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipes.get(1).getSteps().size()));
        } else if (id == R.id.nav_yellow) {
            mToolBar.setTitle(R.string.yellow_cake);
            mRecipeName.setText(mRecipes.get(2).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipes.get(2).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipes.get(2).getSteps().size()));
        } else if (id == R.id.nav_cheese) {
            mToolBar.setTitle(R.string.cheesecake);
            mRecipeName.setText(mRecipes.get(3).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipes.get(3).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipes.get(3).getSteps().size()));
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void returnRecipeList(List<Recipes> recipesList) {
        mRecipes = recipesList;
    }
}