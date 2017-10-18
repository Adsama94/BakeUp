package com.adsama.android.bakeup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.Toast;

import com.adsama.android.bakeup.Model.Ingredients;
import com.adsama.android.bakeup.Model.Recipes;
import com.adsama.android.bakeup.Model.Steps;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncListener;
import com.adsama.android.bakeup.NetworkUtils.NetworkAsyncTask;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NetworkAsyncListener {

    private static final String MENU_SELECTED = "selected";
    private static final String LIST_KEY = "list_key";
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
    @BindView(R.id.instructions_tv)
    TextView mInstructionsTv;
    @BindView(R.id.iv_recipe_image)
    ImageView mRecipePlaceHolder;
    @BindView(R.id.cv_recipe_list)
    CardView mRecipeCardView;
    @BindView(R.id.empty_layout)
    ConstraintLayout mEmptyLayout;
    @BindView(R.id.bake_img_logo)
    ImageView mLogoView;
    ConnectivityManager connManager;
    NetworkInfo networkInfo;
    ArrayList<Recipes> mRecipesList;
    ArrayList<Ingredients> mIngredientsList;
    ArrayList<Steps> mStepsList;
    private int id;
    private boolean mTwoPane;
    private IngredientsFragment mIngredientsFragment;
    private StepsFragment mStepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mEmptyLayout.setVisibility(View.VISIBLE);
        mRecipeCardView.setVisibility(View.GONE);
        mInstructionsTv.setTypeface(EasyFonts.droidSerifBold(this));
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
        if (savedInstanceState != null) {
            mRecipesList = savedInstanceState.getParcelableArrayList(LIST_KEY);
            id = savedInstanceState.getInt(MENU_SELECTED);
            if (id == R.id.nav_pie) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setPie();
                setupIngredientFragment();
                setupStepsFragment();
            } else if (id == R.id.nav_brownie) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setBrownie();
                setupIngredientFragment();
                setupStepsFragment();
            } else if (id == R.id.nav_yellow) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setYellow();
                setupIngredientFragment();
                setupStepsFragment();
            } else if (id == R.id.nav_cheese) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setCheese();
                setupIngredientFragment();
                setupStepsFragment();
            }
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mRecipeCardView.setVisibility(View.GONE);
        }
        mTwoPane = findViewById(R.id.recipe_detail_container) != null;
        mLogoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        mRecipeName.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeSizeCount.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeStepCount.setTypeface(EasyFonts.droidSerifBold(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, id);
        outState.putParcelableArrayList(LIST_KEY, mRecipesList);
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
        id = item.getItemId();
        if (id == R.id.nav_pie) {
            setPie();
            setupIngredientFragment();
            setupStepsFragment();
        } else if (id == R.id.nav_brownie) {
            setBrownie();
            setupIngredientFragment();
            setupStepsFragment();
        } else if (id == R.id.nav_yellow) {
            setYellow();
            setupIngredientFragment();
            setupStepsFragment();
        } else if (id == R.id.nav_cheese) {
            setCheese();
            setupIngredientFragment();
            setupStepsFragment();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void returnRecipeList(ArrayList<Recipes> recipesList) {
        mRecipesList = recipesList;
    }

    public void setPie() {
        mEmptyLayout.setVisibility(View.GONE);
        mRecipeCardView.setVisibility(View.VISIBLE);
        mToolBar.setTitle(R.string.nutella_pie);
        if (mRecipesList != null) {
            mRecipeName.setText(mRecipesList.get(0).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipesList.get(0).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipesList.get(0).getSteps().size()));
            mIngredientsList = mRecipesList.get(0).getIngredients();
            mStepsList = mRecipesList.get(0).getSteps();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    public void setBrownie() {
        mEmptyLayout.setVisibility(View.GONE);
        mRecipeCardView.setVisibility(View.VISIBLE);
        mToolBar.setTitle(R.string.brownies);
        if (mRecipesList != null) {
            mRecipeName.setText(mRecipesList.get(1).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipesList.get(1).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipesList.get(1).getSteps().size()));
            mIngredientsList = mRecipesList.get(1).getIngredients();
            mStepsList = mRecipesList.get(1).getSteps();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    public void setYellow() {
        mEmptyLayout.setVisibility(View.GONE);
        mRecipeCardView.setVisibility(View.VISIBLE);
        mToolBar.setTitle(R.string.yellow_cake);
        if (mRecipesList != null) {
            mRecipeName.setText(mRecipesList.get(2).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipesList.get(2).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipesList.get(2).getSteps().size()));
            mIngredientsList = mRecipesList.get(2).getIngredients();
            mStepsList = mRecipesList.get(2).getSteps();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    public void setCheese() {
        mEmptyLayout.setVisibility(View.GONE);
        mRecipeCardView.setVisibility(View.VISIBLE);
        mToolBar.setTitle(R.string.cheesecake);
        if (mRecipesList != null) {
            mRecipeName.setText(mRecipesList.get(3).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipesList.get(3).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipesList.get(3).getSteps().size()));
            mIngredientsList = mRecipesList.get(3).getIngredients();
            mStepsList = mRecipesList.get(3).getSteps();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupIngredientFragment() {
        Bundle argumentsForIngredients = new Bundle();
        argumentsForIngredients.putParcelableArrayList("arraylist", mIngredientsList);
        if (mIngredientsList != null) {
            mIngredientsFragment = new IngredientsFragment();
            mIngredientsFragment.setArguments(argumentsForIngredients);
            getSupportFragmentManager().beginTransaction().replace(R.id.ingredients_fragment_container, mIngredientsFragment).commit();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupStepsFragment() {
        Bundle argumentsForSteps = new Bundle();
        argumentsForSteps.putParcelableArrayList("stepslist", mStepsList);
        argumentsForSteps.putBoolean("tabLayout", mTwoPane);
        if (mStepsList != null) {
            mStepsFragment = new StepsFragment();
            mStepsFragment.setArguments(argumentsForSteps);
            getSupportFragmentManager().beginTransaction().replace(R.id.steps_fragment_container, mStepsFragment).commit();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }
}