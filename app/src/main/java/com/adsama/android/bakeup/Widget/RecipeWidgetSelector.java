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

    @BindView(R.id.rb_pie)
    RadioButton mRadioPie;
    @BindView(R.id.rb_brownie)
    RadioButton mRadioBrownie;
    @BindView(R.id.rb_yellow)
    RadioButton mRadioYellow;
    @BindView(R.id.rb_cheesecake)
    RadioButton mRadioCheese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_selector);
        ButterKnife.bind(this);
        mRadioPie.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioBrownie.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioYellow.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioCheese.setTypeface(EasyFonts.droidSerifBold(this));
        mRadioPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioBrownie.setChecked(false);
                mRadioYellow.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioBrownie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioPie.setChecked(false);
                mRadioYellow.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioPie.setChecked(false);
                mRadioBrownie.setChecked(false);
                mRadioCheese.setChecked(false);
            }
        });
        mRadioCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioPie.setChecked(false);
                mRadioBrownie.setChecked(false);
                mRadioYellow.setChecked(false);
            }
        });
    }
}
