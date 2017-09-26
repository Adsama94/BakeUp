package com.adsama.android.bakeup.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsama.android.bakeup.Model.Steps;
import com.adsama.android.bakeup.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.InstructionsHolder> {

    private ArrayList<Steps> mStepsList;
    private Context mContext;

    public StepsAdapter(ArrayList<Steps> stepsList, Context context) {
        mStepsList = stepsList;
        mContext = context;
    }

    @Override
    public InstructionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item, parent, false);
        return new InstructionsHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionsHolder holder, int position) {
        Steps steps = mStepsList.get(position);
        holder.mStepsIdTv.setText(steps.getId());
        holder.mShortDescTv.setText(steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    public void setStepsData(ArrayList<Steps> stepsList) {
        mStepsList = stepsList;
        notifyDataSetChanged();
    }

    public ArrayList<Steps> getSteps(){
        return mStepsList;
    }

    class InstructionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout mStepsLayout;
        TextView mStepsIdTv;
        TextView mShortDescTv;
        ImageView mPlayButton;

        InstructionsHolder(View itemView) {
            super(itemView);
            mStepsLayout = itemView.findViewById(R.id.steps_layout);
            mStepsIdTv = itemView.findViewById(R.id.steps_id_tv);
            mStepsIdTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mShortDescTv = itemView.findViewById(R.id.shortDesc_tv);
            mShortDescTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mPlayButton = itemView.findViewById(R.id.forward_arrow);
        }


        @Override
        public void onClick(View view) {

        }
    }

}
