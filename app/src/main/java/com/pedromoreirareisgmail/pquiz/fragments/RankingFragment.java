package com.pedromoreirareisgmail.pquiz.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.adapter.AdapterRanking;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.interfaces.RankingCallBack;
import com.pedromoreirareisgmail.pquiz.models.Ranking;
import com.pedromoreirareisgmail.pquiz.models.Score;

import java.util.Objects;


public class RankingFragment extends Fragment {

    private Context mContext;
    private DatabaseReference mRefScores;
    private DatabaseReference mRefRanking;
    private int mSum = 0;

    private AdapterRanking mAdapter;
    private RecyclerView mRvList;

    public RankingFragment() {
    }

    public static RankingFragment newInstance() {

        return new RankingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();

        initFirebase();
    }

    private void initFirebase(){

        mRefScores = Fire.getRefScores();
        mRefScores.keepSynced(true);

        mRefRanking = Fire.getRefRanking();
        mRefRanking.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_ranking, container, false);

        mRvList = myFragment.findViewById(R.id.rv_list_ranking);
        mRvList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mRvList.setLayoutManager(linearLayoutManager);

        // Agora vamor implementar callback
        updateScore(Common.currentUser.getUserName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {

                // Atualizar a tabela de Ranking

                mRefRanking.child(ranking.getUserName())
                        .setValue(ranking);

                showRanking();
            }
        });

        return myFragment;
    }

    private void showRanking() {

        //  Busca Ranking por ordem de pontuação
        mRefRanking.orderByChild(Common.SCORE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot data: dataSnapshot.getChildren()){

                            Ranking rankingLocal = data.getValue(Ranking.class);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void updateScore(final String userName, final RankingCallBack<Ranking> callBack) {

        //  Busca pontuação por usuario
        mRefScores.orderByChild(Common.USER).equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            Score scoresUSer = data.getValue(Score.class);

                            mSum += Integer.parseInt(Objects.requireNonNull(scoresUSer).getScore());
                        }

                        //Depois de somar todos os pontos, vamor processar a variavel mSum
                        Ranking ranking = new Ranking(userName, mSum);
                        callBack.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    @Override
    public void onStart() {
        super.onStart();

        mAdapter = new AdapterRanking(firebaseOptions(),mContext, Objects.requireNonNull(getActivity()));

        mRvList.setAdapter(mAdapter);

        if(mAdapter != null){

            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mAdapter != null){

            mAdapter.stopListening();
        }
    }

    private FirebaseRecyclerOptions<Ranking> firebaseOptions(){

        Query queryRanking = mRefRanking.orderByChild(Common.SCORE);

        return new FirebaseRecyclerOptions.Builder<Ranking>()
                .setQuery(queryRanking,Ranking.class)
                .build();
    }

}
