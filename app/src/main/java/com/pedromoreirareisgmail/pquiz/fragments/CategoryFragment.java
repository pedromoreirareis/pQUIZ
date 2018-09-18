package com.pedromoreirareisgmail.pquiz.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.adapter.AdapterCategory;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.models.Category;

import java.util.Objects;

public class CategoryFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRvList;
    private AdapterCategory mAdapter;

    private DatabaseReference mRefCategory;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {

        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();

        initFirebase();
    }

    private void initFirebase(){

        // Referencia as categorias do Quiz
        mRefCategory = Fire.getRefCategorys();
        mRefCategory.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragment = inflater.inflate(R.layout.fragment_category, container, false);

        mRvList = myFragment.findViewById(R.id.rv_list_category);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return myFragment;
    }

    @Override
    public void onStart() {
        super.onStart();



        mAdapter = new AdapterCategory(firebaseRecyclerOptions(), mContext, Objects.requireNonNull(getActivity()));

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

    private FirebaseRecyclerOptions<Category> firebaseRecyclerOptions() {

        return new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mRefCategory, Category.class)
                .build();
    }

}
