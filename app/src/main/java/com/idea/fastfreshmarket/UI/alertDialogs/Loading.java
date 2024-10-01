package com.idea.fastfreshmarket.UI.alertDialogs;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.idea.fastfreshmarket.R;

import androidx.appcompat.app.AlertDialog;

public class Loading {

    Activity activity;
    AlertDialog alertDialog;

    public Loading(Activity activity) {
        this.activity = activity;
    }

    public void start (){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.loading_alert_dialog , null);

        builder.setView(dialogView);

        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    public  void dismiss(){
        alertDialog.dismiss();
    }
}
