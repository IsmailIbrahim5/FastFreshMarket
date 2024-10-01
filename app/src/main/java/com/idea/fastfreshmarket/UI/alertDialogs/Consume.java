package com.idea.fastfreshmarket.UI.alertDialogs;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.SelectingLocation;
import com.idea.fastfreshmarket.UI.activities.Home;

import androidx.appcompat.app.AlertDialog;

public class Consume {

    Activity activity;
    AlertDialog alertDialog;
    float totalPrice;
    public Consume(Activity activity , float totalPrice) {
        this.activity = activity;
        this.totalPrice = totalPrice;
    }

    public void start (){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.consume_points_alert_dialog , null);

        MaterialButton button100 = dialogView.findViewById(R.id.button_100);
        MaterialButton button1000 = dialogView.findViewById(R.id.button_1000);
        MaterialButton button10000 = dialogView.findViewById(R.id.button_10000);

        MaterialButton skip = dialogView.findViewById(R.id.skip);

        TextView text1000 = dialogView.findViewById(R.id.text_1000);
        TextView text10000 = dialogView.findViewById(R.id.text_10000);

        TextView current_points = dialogView.findViewById(R.id.current_points);

        int points = Home.user.getPoints();
        current_points.setText(activity.getString(R.string.you_have)+" "+points+" "+activity.getString(R.string.points));
        if(points > 1000){
            button1000.setEnabled(true);
            button1000.setTextColor(activity.getResources().getColor(R.color.green));
            button1000.setStrokeColorResource(R.color.green);
            text1000.setTextColor(activity.getResources().getColor(R.color.green));
        }

        if(points > 10000){
            button10000.setEnabled(true);
            button10000.setTextColor(activity.getResources().getColor(R.color.green));
            button10000.setStrokeColorResource(R.color.green);
            text10000.setTextColor(activity.getResources().getColor(R.color.green));
        }


        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Continue(100);
            }
        });

        button1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Continue(1000);
            }
        });

        button10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Continue(10000);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Continue(0);
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


    public void Continue (int coupon){
        dismiss();
        Intent intent = new Intent(activity  , SelectingLocation.class);
        intent.putExtra("totalPrice" , totalPrice);
        intent.putExtra("coupon" , coupon);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.left_to_right , R.anim.right_to_left);
    }

}
