package com.idea.fastfreshmarket.UI.activities;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.idea.fastfreshmarket.R;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final ImageView logo = findViewById(R.id.logo);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SpringAnimation animY = new SpringAnimation(logo, DynamicAnimation.SCALE_Y, 0.7f), animX = new SpringAnimation(logo, DynamicAnimation.SCALE_X, 0.7f);

                animX.setStartValue(0);
                animX.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);

                animY.setStartValue(0);
                animY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
                animY.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        try {
                            openFileInput("userId");

                            startActivity(new Intent(Welcome.this, Home.class));
                            overridePendingTransition(R.anim.left_to_right,
                                    R.anim.right_to_left);
                            finish();
                        } catch (Exception e) {
                            startActivity(new Intent(Welcome.this, SignUp.class));
                            overridePendingTransition(R.anim.left_to_right,
                                    R.anim.right_to_left);
                            finish();
                        }

                    }
                });
                animX.start();
                animY.start();
            }
        }, 100);
    }
}