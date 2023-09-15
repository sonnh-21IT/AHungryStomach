package com.example.ahungrystomach.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahungrystomach.R;
import com.example.ahungrystomach.activities.HomeActivity;
import com.example.ahungrystomach.listener.SettingListener;

public class LogoutDialog {
    Context context;
    SettingListener listener;
    Dialog dialog;
    public LogoutDialog(Context context,SettingListener listener){
        this.context=context;
        this.listener=listener;
    }
    public void showDialog(){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgremove=dialog.findViewById(R.id.imgremove);
        imgremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView tvNo=dialog.findViewById(R.id.tvNo);
        TextView tvYes=dialog.findViewById(R.id.tvYes);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSayYesDialog();
            }
        });


        dialog.create();
        dialog.show();
    }
    public void hindDialog(){
        dialog.dismiss();
    }
}
