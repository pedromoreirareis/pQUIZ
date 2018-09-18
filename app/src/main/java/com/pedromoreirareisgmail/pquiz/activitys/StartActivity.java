package com.pedromoreirareisgmail.pquiz.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.common.Network;
import com.pedromoreirareisgmail.pquiz.common.TimeDelay;
import com.pedromoreirareisgmail.pquiz.models.Answer;
import com.pedromoreirareisgmail.pquiz.models.Question;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private DatabaseReference mRefQuestions;
    private DatabaseReference mRefAnswers;
    private Button mButPlay;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mContext = StartActivity.this;
    }

    private void initViews() {

        mDialog = new ProgressDialog(mContext);

        //  Cria um layout para Dialog
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog, null);

        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Baixando perguntas...");
        mDialog.setView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mButPlay = findViewById(R.id.but_start_play);
        mButPlay.setVisibility(View.GONE);
        mButPlay.setOnClickListener(this);
    }

    private void initFirebase() {

        //  Referencia as questoes
        mRefQuestions = Fire.getRefQuestions();
        mRefQuestions.keepSynced(true);

        //  Referencia as respostas
        mRefAnswers = Fire.getRefAnswer();
        mRefAnswers.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initViews();

        if (Network.hasInternet(mContext)) {

            initFirebase();

            //  Download das perguntas
            loadQuestionsID(Common.categoryId);

        } else {

            noInternetFinish();
        }
    }

    private void noInternetFinish() {

        Toast.makeText(mContext, "Não foi possível baixar perguntas. Tente novamente.", Toast.LENGTH_SHORT).show();
        TimeDelay.delay(2000);
        finish();
    }

    /*  Baixa as perguntas da categoria selecionada */
    private void loadQuestionsID(String categoryId) {

        //  Se lista tiver dados, então apaga os dados
        if (Common.questionsList.size() > 0) {

            Common.questionsList.clear();
        }

        //  Baixa novas perguntas
        mRefQuestions.orderByChild(Common.CATEGORY_ID).equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {

                            Question question = questionSnapshot.getValue(Question.class);
                            Common.questionsList.add(question);

                            //  download das respostas
                            loadAnswerID(Common.categoryId);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    /*  Baixa as respostas da categoria selecionada */
    private void loadAnswerID(String categoryId) {

        //  Se lista tiver dados, então apaga os dados
        if (Common.answersList.size() > 0) {

            Common.answersList.clear();
        }

        mRefAnswers.orderByChild(Common.CATEGORY_ID).equalTo(categoryId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot answerSnapshot : dataSnapshot.getChildren()) {

                            Answer answer = answerSnapshot.getValue(Answer.class);
                            Common.answersList.add(answer);

                            mDialog.dismiss();

                            mButPlay.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_start_play:

                Intent toPlaying = new Intent(mContext, PlayingActivity.class);
                startActivity(toPlaying);
                finish();

                break;
        }
    }

}
