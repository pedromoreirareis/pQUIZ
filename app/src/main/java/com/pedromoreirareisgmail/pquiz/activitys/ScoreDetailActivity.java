package com.pedromoreirareisgmail.pquiz.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.adapter.AdapterScoreDetail;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.common.Network;
import com.pedromoreirareisgmail.pquiz.models.Score;

public class ScoreDetailActivity extends AppCompatActivity {

    private Context mContext;

    private RecyclerView mRvList;
    private AdapterScoreDetail mAdapter;
    private DatabaseReference mRefScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        mContext = ScoreDetailActivity.this;

        initFirebase();
        initViews();
        initIntents();
    }

    private void initFirebase(){

        mRefScores = Fire.getRefScores();
        mRefScores.keepSynced(true);
    }

    private void initViews(){

        mRvList = findViewById(R.id.rv_list_score_detail);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initIntents(){

        String viewUSer = "";

        if(getIntent()!= null){

            viewUSer = getIntent().getStringExtra(Common.INTENT_USER_NAME);

        }

        if(!viewUSer.isEmpty() && Network.hasInternet(mContext)){

            loadScoreDetail(viewUSer);
        }
    }

    private void loadScoreDetail(String viewUser) {

        mAdapter = new AdapterScoreDetail(firebaseOptions(viewUser),mContext,ScoreDetailActivity.this);

        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAdapter.stopListening();
    }

    private FirebaseRecyclerOptions<Score> firebaseOptions(String viewUser){

        Query queryScores = mRefScores.orderByChild(Common.USER).equalTo(viewUser);

        return new FirebaseRecyclerOptions.Builder<Score>()
                .setQuery(queryScores,Score.class)
                .build();
    }
}
