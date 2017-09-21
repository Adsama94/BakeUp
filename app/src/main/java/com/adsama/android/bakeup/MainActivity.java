package com.adsama.android.bakeup;

import android.content.Context;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncListener;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncTask;

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
    @BindView(R.id.recipe_detail_steps_recycler_view)
    RecyclerView mStepsRecyclerView;
    @BindView(R.id.recipe_detail_ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;
    ConnectivityManager connManager;
    NetworkInfo networkInfo;
    List<Recipes> mRecipes;

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
            mRecipeName.setText(mRecipes.set(0, null).getName());
        } else if (id == R.id.nav_brownie) {
            mToolBar.setTitle(R.string.brownies);
            mRecipeName.setText(mRecipes.set(1, null).getName());
        } else if (id == R.id.nav_yellow) {
            mToolBar.setTitle(R.string.yellow_cake);
            mRecipeName.setText(mRecipes.set(2, null).getName());
        } else if (id == R.id.nav_cheese) {
            mToolBar.setTitle(R.string.cheesecake);
            mRecipeName.setText(mRecipes.set(3, null).getName());
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void returnRecipeList(List<Recipes> recipesList) {
        mRecipes = recipesList;
    }
}