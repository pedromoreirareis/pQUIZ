package com.pedromoreirareisgmail.pquiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pedromoreirareisgmail.pquiz.R;

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder  {

    public TextView categoryName;
    public TextView score;


    public ScoreDetailViewHolder(View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.tv_score_detail_category_name);
        score   = itemView.findViewById(R.id.tv_score_detail_score);

    }

}
