package com.example.ahungrystomach.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ahungrystomach.R;

public class LoadingDialogBar {
    Context context;
    public static Dialog dialog;

    public static ImageView imageView;
    public static ProgressBar progressBar;
    public static TextView textTitle;

    public LoadingDialogBar(Context context) {
        this.context = context;
    }
    public void showDialog(String title){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textTitle=dialog.findViewById(R.id.tv_title);
        textTitle.setText(title);
        imageView=dialog.findViewById(R.id.dialogimg);
        imageView.setVisibility(View.GONE);
        progressBar=dialog.findViewById(R.id.progress);
        ImageView img=dialog.findViewById(R.id.imgremove);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

}
