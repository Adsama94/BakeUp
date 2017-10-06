package com.adsama.android.bakeup.Widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.adsama.android.bakeup.R;
import com.vstechlab.easyfonts.EasyFonts;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeWidgetSelector extends AppCompatActivity {

    private static final String RADIO_KEY = "radio_key";
    private static final String LOG_TAG = RecipeWidgetSelector.class.getSimpleName();
    @BindView(R.id.rb_pie)
    RadioButton mRadioPie;
    @BindView(R.id.rb_brownie)
    RadioButton mRadioBrownie;
    @BindView(R.id.rb_yellow)
    RadioButton mRadioYellow;
    @BindView(R.id.rb_cheesecake)
    RadioButton mRadioCheese;
    private int mRadioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_selector);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            savedInstanceState.getInt(RADIO_KEY);
        }
        mRadioPie.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioBrownie.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioYellow.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioCheese.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonId = 1;
                mRadioBrownie.setChecked(false);
                mRadioYellow.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioBrownie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonId = 2;
                mRadioPie.setChecked(false);
                mRadioYellow.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonId = 3;
                mRadioPie.setChecked(false);
                mRadioBrownie.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonId = 4;
                mRadioPie.setChecked(false);
                mRadioBrownie.setChecked(false);
                mRadioYellow.setChecked(false);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RADIO_KEY, mRadioButtonId);
    }
}