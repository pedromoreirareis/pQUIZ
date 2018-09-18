package com.pedromoreirareisgmail.pquiz.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.activitys.StartActivity;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.common.PicassoDownload;
import com.pedromoreirareisgmail.pquiz.interfaces.ItemClickListener;
import com.pedromoreirareisgmail.pquiz.models.Category;
import com.pedromoreirareisgmail.pquiz.viewHolder.CategoryViewHolder;

public class AdapterCategory extends FirebaseRecyclerAdapter<Category, CategoryViewHolder> {

    private Context mContext;
    private ProgressDialog mDialog;

    public AdapterCategory(@NonNull FirebaseRecyclerOptions<Category> options, Context context, Activity activity) {
        super(options);
        mContext = context;

        mDialog = new ProgressDialog(mContext);

        //  Cria um layout para Dialog
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog, null);

        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Baixando categorias...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setView(view);
        mDialog.show();
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final Category model) {

        //  Nome da categoria
        holder.categoryName.setText(model.getName());

        //  Imagem que representa a categoria
        PicassoDownload.ivChache(mContext, model.getImage(), holder.categoryImage);

        //  Ao clicar
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                //  Nome do usuario
                Common.categoryName = model.getName();

                //  ID da categoria que usuario escolheu
                Common.categoryId = getRef(position).getKey();

                //  Abre activity para iniciar jogo
                Intent startGame = new Intent(mContext, StartActivity.class);
                mContext.startActivity(startGame);
            }
        });
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);


        return new CategoryViewHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

        mDialog.dismiss();


    }
}
