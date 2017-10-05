package com.adsama.android.bakeup.Widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import com.adsama.android.bakeup.R;

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
    }
}
