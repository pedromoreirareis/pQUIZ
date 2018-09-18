package com.pedromoreirareisgmail.pquiz.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.PicassoDownload;
import com.pedromoreirareisgmail.pquiz.common.TimeDelay;
import com.pedromoreirareisgmail.pquiz.models.AnswerToBut;
import com.pedromoreirareisgmail.pquiz.models.ThreeWrongs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {

    private final static long INTERVAL = 1000;
    private final static long TIMEOUT = 12000;  //  12000 para ser de 10 segundos

    private Context mContext;

    private int mProgressValue = 0;
    private CountDownTimer mCountDown;

    private int mIndex = 0;         //  Pergunta que esta sendo mostrada no Dysplay
    private int mScore = 0;         //  Pontuação
    private int mCurrent = 0;       //  Numero da Pergunta atual em relação ao total
    private int mQuantity;          //  Quantidade de perguntas da categoria
    private int mCorrects;          //  Quantidade de respostas certas

    private ImageView mIvQuestionImage;

    private Button mButAnswerA;
    private Button mButAnswerB;
    private Button mButAnswerC;
    private Button mButAnswerD;

    private TextView mTvStop;
    private TextView mTvScore;
    private TextView mTvQuestionNum;
    private TextView mTvQuestionText;
    private TextView mTvDownTimer;

    private ProgressBar mPbProgress;

    private AnswerToBut mAnswerToBut;

    private boolean isEndQuestions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        mContext = PlayingActivity.this;

        //  Referencia as Views
        initViews();
    }

    private void initViews() {

        mPbProgress = findViewById(R.id.pb_playing_progress);

        mTvScore = findViewById(R.id.tv_playing_score);
        mTvQuestionNum = findViewById(R.id.tv_playing_total_question);
        mTvQuestionText = findViewById(R.id.tv_playing_question_text);
        mTvDownTimer = findViewById(R.id.tv_playing_downTime);
        mTvStop = findViewById(R.id.tv_playing_stop);

        mIvQuestionImage = findViewById(R.id.iv_playing_question_image);

        mButAnswerA = findViewById(R.id.but_playing_answer_a);
        mButAnswerB = findViewById(R.id.but_playing_answer_b);
        mButAnswerC = findViewById(R.id.but_playing_answer_c);
        mButAnswerD = findViewById(R.id.but_playing_answer_d);

        mButAnswerA.setOnClickListener(this);
        mButAnswerB.setOnClickListener(this);
        mButAnswerC.setOnClickListener(this);
        mButAnswerD.setOnClickListener(this);
        mTvStop.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //  Coloca perguntas em ordem aleatoria
        Collections.shuffle(Common.questionsList);
        Collections.shuffle(Common.questionsList);

        //  Quantidade de perguntas da categoria
        mQuantity = Common.questionsList.size();

        //  Contador - Tempo de resposta para cada pergunta
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {/*  Contador com intevalo = INTERVAL */

                //  Mostra o tempo restante em um progressBar e textview
                mTvDownTimer.setText(String.valueOf(10 - mProgressValue));
                mPbProgress.setProgress(mProgressValue);
                mProgressValue++;
            }

            @Override
            public void onFinish() {/*  Ao finalizar o TIMEOUT de onTick executa onFinish do CountDownTimer */

                //  Cancela o contador
                mCountDown.cancel();

                //  Proxima pergunta
                showQuestion(++mIndex);
            }
        };

        //  Apresenta a primeira pergunta
        showQuestion(mIndex);
    }

    private void showQuestion(int currentIdex) {

        //  Até a ultima pergunta
        if (currentIdex < mQuantity) {

            //  Zera o progressBar
            mPbProgress.setProgress(mProgressValue);
            mProgressValue = 0;

            //  Numero da pergunta Atual
            mCurrent++;

            //  Mostra a relação entre pergunta ATUAL / QUANTIDADE
            mTvQuestionNum.setText(String.format(getString(R.string.showQuestionNum), mCurrent, mQuantity));

            //  Se for do tipo IMAGEM
            if (Common.questionsList.get(currentIdex).getIsImageQuestion().equals(Common.TRUE)) {

                //  Baixa imagem
                PicassoDownload.ivChache(
                        mContext,
                        Common.questionsList.get(currentIdex).getQuestion(),
                        mIvQuestionImage
                );

                //  Mostra a Imagem e deixa TextView invisivel
                mIvQuestionImage.setVisibility(View.VISIBLE);
                mTvQuestionText.setVisibility(View.GONE);

            } else {//  Se pergunta for do tipo TEXTO

                //  Pergunta em formato texto
                mTvQuestionText.setText(Common.questionsList.get(currentIdex).getQuestion());

                //  Coloca a pergunta no Texto view
                String question = Common.questionsList.get(currentIdex).getQuestion();
                mTvQuestionText.setText(question);

                //  Mostra o TextView e deixa Image invisivel
                mTvQuestionText.setVisibility(View.VISIBLE);
                mIvQuestionImage.setVisibility(View.GONE);
            }

            //  Passa resposta correcta para receber uma lsita de respostas
            mAnswerToBut = getAnswerListToBut(Common.questionsList.get(currentIdex).getAnswer());

            //  Respostas - para apresentar ao usuário
            mButAnswerA.setText(mAnswerToBut.getAnswerA());
            mButAnswerB.setText(mAnswerToBut.getAnswerB());
            mButAnswerC.setText(mAnswerToBut.getAnswerC());
            mButAnswerD.setText(mAnswerToBut.getAnswerD());

            // Inicia o contador de tempo
            mCountDown.start();

            //  Habilita controles
            enableButtons();

        } else {

            isEndQuestions = true;

            //  Se essa for a questão final
            endQuestions();
        }
    }

    private AnswerToBut getAnswerListToBut(String answerCorrect) {

        //  Lista com todas as respostas
        List<String> allAnswerList = Common.answersList.get(0).getAnswerList();

        //  Somente respostas ERRADAS
        List<String> onlyWrongsList = new ArrayList<>();

        //  Adicionas somentes respostas ERRADAS
        for (String currentAnswer : allAnswerList) {

            if (!currentAnswer.equals(answerCorrect)) {

                //  ERRADAS
                onlyWrongsList.add(currentAnswer);
            }
        }

        //  Objeto com 3 respostas ERRADAS
        ThreeWrongs threeWrongsList = new ThreeWrongs();
        threeWrongsList.setFirst("");
        threeWrongsList.setSecond("");
        threeWrongsList.setThird("");

        //  Seleciona 3 respostas ERRADAS, distintas e de forma aleatória
        while (threeWrongsList.getFirst().isEmpty()
                || threeWrongsList.getSecond().isEmpty()
                || threeWrongsList.getThird().isEmpty()) {


            if (threeWrongsList.getFirst().isEmpty()) {  //  Seleciona 1ª resposta ERRADA

                //  Altera ordem dos elementos aleatoriamente
                Collections.shuffle(onlyWrongsList);
                Collections.shuffle(onlyWrongsList);

                threeWrongsList.setFirst(onlyWrongsList.get(0));

            } else if (threeWrongsList.getSecond().isEmpty()) {   //  Seleciona 2ª resposta ERRADA


                do {//  Garante que 2ª resposta não seja igual a 1ª

                    //  Altera ordem dos elementos aleatoriamente
                    Collections.shuffle(onlyWrongsList);
                    Collections.shuffle(onlyWrongsList);

                    threeWrongsList.setSecond(onlyWrongsList.get(0));

                } while (threeWrongsList.getSecond().equals(threeWrongsList.getFirst()));


            } else if (threeWrongsList.getThird().isEmpty()) {   //  Seleciona 3ª resposta ERRADA


                do {//  Garante que 3ª resposta não seja igual a 1ª e 2ª

                    //  Altera ordem dos elementos aleatoriamente
                    Collections.shuffle(onlyWrongsList);
                    Collections.shuffle(onlyWrongsList);

                    threeWrongsList.setThird(onlyWrongsList.get(0));

                } while (threeWrongsList.getThird().equals(threeWrongsList.getFirst())
                        || threeWrongsList.getThird().equals(threeWrongsList.getSecond()));

            }
        }

        //  Listas com respostas que serão apresentadas
        List<String> answerToButList = new ArrayList<>();

        answerToButList.add(answerCorrect);
        answerToButList.add(threeWrongsList.getFirst());
        answerToButList.add(threeWrongsList.getSecond());
        answerToButList.add(threeWrongsList.getThird());

        //  Coloca respostas de forma aleatória
        Collections.shuffle(answerToButList);
        Collections.shuffle(answerToButList);
        Collections.shuffle(answerToButList);

        //  Cria Objeto com respostas
        mAnswerToBut = new AnswerToBut();

        //  Respostas
        mAnswerToBut.setAnswerA(answerToButList.get(0));
        mAnswerToBut.setAnswerB(answerToButList.get(1));
        mAnswerToBut.setAnswerC(answerToButList.get(2));
        mAnswerToBut.setAnswerD(answerToButList.get(3));

        return mAnswerToBut;
    }


    @Override
    public void onClick(View viewCliked) {

        if (viewCliked.getId() == R.id.tv_playing_stop) {

            isEndQuestions = true;

            endQuestions();
            //finish();
            return;
        }

        //  Cancela o contador
        mCountDown.cancel();

        //  Verifica se é ultima pergunta
        if (mIndex < mQuantity) {

            //  Habilita controles
            disableButtons();


            //  Botão clicado
            Button clikedButton = (Button) viewCliked;

            //  Resposta do usuario
            String textButtonClicked = clikedButton.getText().toString();

            //  Resposta correta
            String answer = Common.questionsList.get(mIndex).getAnswer();


            //  Se o botão clicado for o da resposta correta
            if (textButtonClicked.equals(answer)) {


                clikedButton.setBackground(getResources().getDrawable(R.drawable.background_correct));

                //  Atribui pontos
                mScore += 10;

                //  Quantidade de perguntas certas
                mCorrects++;

                //  Atribui pontuação do quiz
                mTvScore.setText(String.format(getString(R.string.showScore), mScore));

                //  Tempo de espera para proxima pergunta
                delayAnswerResult(true, 1000);

            } else {//  Se for resposta errada


                clikedButton.setBackground(getResources().getDrawable(R.drawable.background_error));

                //  Colocar borda na resposta correta
                errorAnswerCorrect(answer);

                //  Atribui pontuação do quiz
                mTvScore.setText(String.format(getString(R.string.showScore), mScore));

                delayAnswerResult(false, 2000);
            }
        }
    }

    private void errorAnswerCorrect(String answerCorrect) {

        //  Coloca borda verde na resposta correta

        String a = mButAnswerA.getText().toString();
        String b = mButAnswerB.getText().toString();
        String c = mButAnswerC.getText().toString();
        String d = mButAnswerD.getText().toString();

        if (a.equals(answerCorrect)) {

            mButAnswerA.setBackground(getResources().getDrawable(R.drawable.background_error_correct));

        } else if (b.equals(answerCorrect)) {

            mButAnswerB.setBackground(getResources().getDrawable(R.drawable.background_error_correct));

        } else if (c.equals(answerCorrect)) {

            mButAnswerC.setBackground(getResources().getDrawable(R.drawable.background_error_correct));

        } else if (d.equals(answerCorrect)) {

            mButAnswerD.setBackground(getResources().getDrawable(R.drawable.background_error_correct));
        }
    }


    private void delayAnswerResult(final boolean isCorrect, long time) {

        Handler mHandler = new Handler();

        Runnable codeToRun = new Runnable() {
            @Override
            public void run() {

                if (isCorrect) {

                    correctNextQuestion();

                } else {

                    errorEndQuestions();
                }
            }
        };

        mHandler.postDelayed(codeToRun, time);
    }


    private void correctNextQuestion() {

        //  Mostra prxima pergunta
        showQuestion(++mIndex);
    }

    private void errorEndQuestions() {

        isEndQuestions = true;

        endQuestions();
    }

    private void endQuestions() {

        //  Encerra o contador
        mCountDown.cancel();

        //  Resposta Errada ou Fim do tempo
        Intent toDone = new Intent(mContext, DoneActivity.class);

        Bundle dataSend = new Bundle();

        //  Envia - Pontução, quantidade de perguntas, quantidade de acertos
        dataSend.putInt(Common.INTENT_SCORE, mScore);
        dataSend.putInt(Common.INTENT_CORRECT, mCorrects);
        dataSend.putInt(Common.INTENT_TOTAL, mQuantity);
        toDone.putExtras(dataSend);

        startActivity(toDone);
        finish();
    }

    @Override
    public void onBackPressed() {

        isEndQuestions = true;

        endQuestions();
    }

    @Override
    protected void onStop() {
        super.onStop();

        verifyEndQuestions();
    }

    private void verifyEndQuestions() {

        //  onBackPressed - Ir direto para done - endQuestions();
        //  Parar - ir direto para done - endQuestions();
        //  Acabar todas questoes - endQuestions();
        //  Stop -

        if (!isEndQuestions) {

            TimeDelay.delay(200);
            endQuestions();
        }
    }


    private void enableButtons() {

        //  Habilita buttons, textview Stop e coloca cor de fundo

        mButAnswerA.setBackground(getDrawable(R.drawable.background_button));
        mButAnswerB.setBackground(getDrawable(R.drawable.background_button));
        mButAnswerC.setBackground(getDrawable(R.drawable.background_button));
        mButAnswerD.setBackground(getDrawable(R.drawable.background_button));

        mButAnswerA.setEnabled(true);
        mButAnswerB.setEnabled(true);
        mButAnswerC.setEnabled(true);
        mButAnswerD.setEnabled(true);

        mTvStop.setEnabled(true);
    }

    private void disableButtons() {

        //  Desabilita buttons e textview Stop

        mButAnswerA.setEnabled(false);
        mButAnswerB.setEnabled(false);
        mButAnswerC.setEnabled(false);
        mButAnswerD.setEnabled(false);

        mTvStop.setEnabled(false);
    }
}
