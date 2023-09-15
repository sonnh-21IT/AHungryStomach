package com.example.ahungrystomach.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

import com.example.ahungrystomach.R;

public class LoadingMainDialog {
    Context context;
    private Dialog dialog;

    public LoadingMainDialog(Context context) {
        this.context = context;
    }
    public void showDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_main);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
    }
    public void hindDialog(){
        dialog.dismiss();
    }
}
