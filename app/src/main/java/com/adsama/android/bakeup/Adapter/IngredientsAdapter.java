package com.adsama.android.bakeup.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsama.android.bakeup.Model.Ingredients;
import com.adsama.android.bakeup.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.InstructionsHolder> {

    private ArrayList<Ingredients> mIngredientsList;
    private Context mContext;

    public IngredientsAdapter(ArrayList<Ingredients> ingredientsList, Context context) {
        mIngredientsList = ingredientsList;
        mContext = context;
    }

    @Override
    public InstructionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new InstructionsHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionsHolder holder, int position) {
        Ingredients ingredients = mIngredientsList.get(position);
        holder.mActualIngredientTv.setText(mContext.getResources().getString(R.string.bulletPoint) + ingredients.getIngredient());
        holder.mIngredientMeasureTv.setText(ingredients.getMeasure());
        holder.mIngredientQuantityTv.setText(String.valueOf(ingredients.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    public void setIngredientsData(ArrayList<Ingredients> ingredientsList) {
        mIngredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    public ArrayList<Ingredients> getIngredients() {
        return mIngredientsList;
    }

    public void clear() {
        notifyDataSetChanged();
    }

    class InstructionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout mIngredientsHolder;
        TextView mActualIngredientTv, mIngredientMeasureTv, mIngredientQuantityTv;

        public InstructionsHolder(View itemView) {
            super(itemView);
            mIngredientsHolder = itemView.findViewById(R.id.ingredients_constraint_holder);
            mActualIngredientTv = itemView.findViewById(R.id.actualIngredient_tv);
            mActualIngredientTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mIngredientMeasureTv = itemView.findViewById(R.id.measure_tv);
            mIngredientMeasureTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mIngredientQuantityTv = itemView.findViewById(R.id.quantity_tv);
            mIngredientQuantityTv.setTypeface(EasyFonts.droidSerifBold(mContext));
        }

        @Override
        public void onClick(View view) {

        }
    }
}
