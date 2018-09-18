package com.pedromoreirareisgmail.pquiz.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.fragments.CategoryFragment;
import com.pedromoreirareisgmail.pquiz.fragments.RankingFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //  Referencia o bottomNavigaton
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setDefaultFragment();
    }

    private void setDefaultFragment() {

        //  Fragmento padrão
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home_layout, CategoryFragment.newInstance());
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()) {

            //  Fragmento category
            case R.id.action_category:
                selectedFragment = CategoryFragment.newInstance();
                break;

            //  Fragmento Raking
            case R.id.action_ranking:
                selectedFragment = RankingFragment.newInstance();
                break;
        }

        //  Ativa o fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home_layout, selectedFragment);
        transaction.commit();

        return true;
    }
}
