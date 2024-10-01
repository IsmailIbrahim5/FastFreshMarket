package com.idea.fastfreshmarket.UI.alertDialogs;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.activities.Home;

import androidx.appcompat.app.AlertDialog;

public class MyPoints {

    Activity activity;
    AlertDialog alertDialog;

    public MyPoints(Activity activity) {
        this.activity = activity;
    }

    public void start (){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.points_alert_dialog , null);

        final TextView pointsText = dialogView.findViewById(R.id.points);
        pointsText.setText(Home.user.getPoints() + " "+activity.getString(R.string.points));
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
