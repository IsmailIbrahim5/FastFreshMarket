package com.idea.fastfreshmarket.UI.alertDialogs;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.idea.fastfreshmarket.R;

import androidx.appcompat.app.AlertDialog;

public class GPS {

    Activity activity;
    AlertDialog alertDialog;
    public GPS(Activity activity) {
        this.activity = activity;
    }

    public void start (){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.gps_alert_dialog , null);

        Button turnOn = dialogView.findViewById(R.id.turn_on);
        Button dontTurnOn = dialogView.findViewById(R.id.dont_turn_on);

        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        dontTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
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
