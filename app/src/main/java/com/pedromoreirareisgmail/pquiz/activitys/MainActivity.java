package com.pedromoreirareisgmail.pquiz.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.Fire;
import com.pedromoreirareisgmail.pquiz.models.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mRefUsers;
    private Context mContext;

    private MaterialEditText mEtNewUserName;
    private MaterialEditText mEtNewPassword;
    private MaterialEditText mEtNewEmail;

    private MaterialEditText mEtUserName;
    private MaterialEditText mEtPassword;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        initFirebase();
        initViews();
    }

    private void initViews() {

        mEtUserName = findViewById(R.id.et_user_name);
        mEtPassword = findViewById(R.id.et_password);

        Button butSignIn = findViewById(R.id.but_sign_in);
        TextView tvSignUp = findViewById(R.id.tv_sign_up);

        butSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

        mDialog = new ProgressDialog(mContext);
    }

    private void initFirebase() {

        mRefUsers = Fire.getRefUsers();
        mRefUsers.keepSynced(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // Cadastrar
            case R.id.tv_sign_up:
                showSignUpDialog();
                break;

            // Entrar
            case R.id.but_sign_in:
                SignIn(
                        mEtUserName.getText().toString(),
                        mEtPassword.getText().toString()
                );

                break;
        }
    }

    private void SignIn(final String userName, final String password) {

        //  Verifica se nome e passwrod estão vazio
        if (userName.isEmpty() && password.isEmpty()) {

            Toast.makeText(mContext, "Digite o nome de usuário e a senha", Toast.LENGTH_SHORT).show();
            mEtUserName.requestFocus();
            return;
        }

        //  Verifica se nome esta vazio
        if (userName.isEmpty()) {

            Toast.makeText(mContext, "Digite o nome de usuário", Toast.LENGTH_SHORT).show();
            mEtUserName.requestFocus();
            return;
        }

        //  Verifica se password esta vazio
        if (password.isEmpty()) {

            Toast.makeText(mContext, "Digite a senha", Toast.LENGTH_SHORT).show();
            mEtPassword.requestFocus();
            return;
        }

        //  Cria um layout para Dialog
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog, null);

        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Fazendo login...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setView(view);
        mDialog.show();

        mRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userName).exists()) {

                    //  Dados do usuario
                    User dataUser = dataSnapshot.child(userName).getValue(User.class);

                    //  Se a senha estiver correta
                    if (Objects.requireNonNull(dataUser).getPassword().equals(password)) {

                        //  Nome do usuario
                        Common.currentUser = dataUser;

                        //  Fecha Dialog
                        mDialog.dismiss();

                        Intent toHome = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(toHome);
                        finish();

                    } else {

                        Toast.makeText(mContext, "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }

                } else {

                    Toast.makeText(mContext, "Usuário não existe. Cadastre-se.", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {

        //  Cria Layout para Dialog
        LayoutInflater inflater = this.getLayoutInflater();
        View signUpLayout = inflater.inflate(R.layout.sign_up_layout, null);

        //  Referencia Views do layout do Dialog
        mEtNewUserName = signUpLayout.findViewById(R.id.et_new_user_name);
        mEtNewPassword = signUpLayout.findViewById(R.id.et_new_password);
        mEtNewEmail = signUpLayout.findViewById(R.id.et_new_email);

        //  Cria Dialog para cadastro
        AlertDialog.Builder dialogSignUp = new AlertDialog.Builder(mContext);
        dialogSignUp.setTitle("Cadastro");
        dialogSignUp.setMessage("Por favor, preencha todas as informações");
        dialogSignUp.setView(signUpLayout);
        dialogSignUp.setIcon(R.drawable.ic_account_circle_black);

        //  Cancela o cadastro
        dialogSignUp.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        //  Efetua cadastro salvando dados no firebase
        dialogSignUp.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //  Objetos com dados do usuario
                final User user = new User(
                        mEtNewUserName.getText().toString(),
                        mEtNewPassword.getText().toString(),
                        mEtNewEmail.getText().toString()
                );

                //  Salva dados
                mRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(user.getUserName()).exists()) {

                            Toast.makeText(mContext, "Usuário já existe!", Toast.LENGTH_LONG).show();
                        } else {

                            mRefUsers.child(user.getUserName()).setValue(user);
                            Toast.makeText(mContext, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialog.dismiss();
            }
        });

        dialogSignUp.show();
    }
}
