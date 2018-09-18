package com.pedromoreirareisgmail.pquiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.interfaces.ItemClickListener;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView rankingUSerName;
    public TextView rankingScore;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);

        rankingUSerName = itemView.findViewById(R.id.tv_ranking_name);
        rankingScore = itemView.findViewById(R.id.tv_ranking_score);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
