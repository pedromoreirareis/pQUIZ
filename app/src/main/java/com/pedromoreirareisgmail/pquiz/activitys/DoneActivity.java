package com.pedromoreirareisgmail.pquiz.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.models.Score;

import java.util.Objects;

public class DoneActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private DatabaseReference mRefScoresNew;
    private DatabaseReference mRefScoresOld;

    private TextView mTvScoreResult;
    private TextView mTvQuestionResultText;
    private ProgressBar mPbProgress;
    private Button mButTryAgain;

    private String mScoreOld = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        mContext = DoneActivity.this;

        initViews();
        initFirebase();
        initExtras();

        mButTryAgain.setOnClickListener(this);
    }

    private void initFirebase() {

        mRefScoresNew = Fire.getRefScores();
        mRefScoresNew.keepSynced(true);

        mRefScoresOld = Fire.getRefScores();
        mRefScoresOld.keepSynced(true);
    }

    private void initViews() {

        mTvScoreResult = findViewById(R.id.tv_done_total_score);
        mTvQuestionResultText = findViewById(R.id.tv_done_total_question);
        mPbProgress = findViewById(R.id.pb_done_progress);
        mButTryAgain = findViewById(R.id.but_done_try_again);
    }

    private void initExtras() {

        //  Recebendo dados
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            //  Coloca dados em váriaveis
            int score = extras.getInt(Common.INTENT_SCORE);
            int quantity = extras.getInt(Common.INTENT_TOTAL);
            int corrects = extras.getInt(Common.INTENT_CORRECT);

            //  Mostra resultados a usuário no Display
            showResultDisplay(score, quantity, corrects);


            String userCategory = String.format(
                    getString(R.string.uploadUserCategory),
                    Common.currentUser.getUserName(),
                    Common.categoryId
            );

            String categoryId = Common.categoryId;
            String categoryName = Common.categoryName;

            //  Atualiza dados no Firebase se for novo recorde
            updateScoreFirebase(score, quantity, userCategory, categoryId, categoryName);
        }
    }

    private void showResultDisplay(int score, int quantity, int corrects) {

        //  Apresenta pontuação
        mTvScoreResult.setText(String.format(getString(R.string.showScoreResult), score));

        //  Apresenta quantidade de CERTAS / TOTAL
        mTvQuestionResultText.setText(String.format(getString(R.string.showQuestionResult),
                corrects,
                quantity)
        );

        //  Mostra em um ProgressBar a relação de CERTAS / TOTAL
        mPbProgress.setMax(quantity);
        mPbProgress.setProgress(corrects);
    }

    private void updateScoreFirebase(final int score, final int quantity, final String userCategory, final String categoryId, final String categoryName) {

        mRefScoresOld.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userCategory).exists()) {

                    Score scoreOld = dataSnapshot.child(userCategory).getValue(Score.class);

                    mScoreOld = Objects.requireNonNull(scoreOld).getScore();

                    int scoreOldInt = Integer.parseInt(mScoreOld);

                    //  Se nova pontuação maior que anterior
                    if (score >= scoreOldInt) {

                        //  Objeto com nova pontuação
                        Score newScore = new Score(
                                userCategory,
                                Common.currentUser.getUserName(),
                                String.valueOf(score),
                                categoryId,
                                categoryName
                        );

                        //  Salva no firebase
                        mRefScoresNew.child(userCategory).setValue(newScore);


                        if (score == quantity * 10) {

                            showSnackBar(
                                    score + " pontos. " + "Pontuação máxima. RECORDE.",
                                    getDrawable(R.drawable.background_correct)
                            );

                        } else if (score > scoreOldInt) {

                            showSnackBar(
                                    score + " pontos. " + "Novo RECORDE.\n" +
                                            "O recorde anterior era de " + String.valueOf(mScoreOld) + " pontos!!!",
                                    getDrawable(R.drawable.background_correct)
                            );
                        } else if (score == scoreOldInt) {

                            showSnackBar(
                                    score + " pontos. " + "Igual ao recorde anterior.\n" +
                                            "Você vai consegui, tente novamente.",
                                    getDrawable(R.drawable.background_correct)
                            );
                        }

                    } else {

                        showSnackBar(
                                "Não foi dessa vez, tente novamente cabeção!!!\nSeu atual recorde atual é: " + String.valueOf(mScoreOld) + "pontos!!!",
                                getDrawable(R.drawable.background_error)
                        );

                    }
                } else {


                    Score newScore = new Score(
                            userCategory,
                            Common.currentUser.getUserName(),
                            String.valueOf(score),
                            categoryId,
                            categoryName
                    );

                    mRefScoresNew.child(userCategory).setValue(newScore);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showSnackBar(String textSnackbar, Drawable drawable) {


        Snackbar snackbar = Snackbar.make(
                mButTryAgain,
                textSnackbar,
                Snackbar.LENGTH_INDEFINITE
        );

        View view = snackbar.getView();

        view.setBackground(drawable);

        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_done_try_again:

                Intent toHome = new Intent(mContext, HomeActivity.class);
                startActivity(toHome);
                finish();
                break;
        }

    }
}
