package com.idea.fastfreshmarket.UI.alertDialogs;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.Models.Rating;

import androidx.appcompat.app.AlertDialog;

public class Rate {

    Activity activity;
    AlertDialog alertDialog;
    String orderId;

    public Rate(Activity activity , String orderId) {
        this.activity = activity;
        this.orderId = orderId;
    }

    public void start (){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.rate_alert_dialog , null);

        builder.setView(dialogView);

        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        final Button submit = dialogView.findViewById(R.id.submit);
        TextView skip = dialogView.findViewById(R.id.skip);
        final EditText comments = dialogView.findViewById(R.id.comments);
        final RatingBar deliveryBar = dialogView.findViewById(R.id.delivery_bar);
        final RatingBar packageBar = dialogView.findViewById(R.id.package_bar);
        final RatingBar productsBar = dialogView.findViewById(R.id.products_bar);

        comments.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                submit.callOnClick();
                return true;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Loading loading = new Loading(activity);
                loading.start();
                Rating rating  = new Rating(deliveryBar.getRating() , packageBar.getRating() , productsBar.getRating() , comments.getText().toString());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
                databaseReference.child(orderId).child("Info").child("rating").setValue(rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                    }
                });
                Toast.makeText(activity, activity.getString(R.string.thank_you), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public  void dismiss(){
        alertDialog.dismiss();
    }
}
