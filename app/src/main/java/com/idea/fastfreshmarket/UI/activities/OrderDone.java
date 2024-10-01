package com.idea.fastfreshmarket.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.Tools.Services.RatingService;

public class OrderDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_done);

        ImageView back = findViewById(R.id.back);
        Button moreShopping = findViewById(R.id.moreShopping);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Home.navView.setSelectedItemId(R.id.navigation_products);
                        Home.orders.clear();
                        Home.badgeDrawable.setVisible(false);
                    }
                } , 100);
            }
        };
        back.setOnClickListener(onClickListener);

        moreShopping.setOnClickListener(onClickListener);
        Intent intent = new Intent(OrderDone.this , RatingService.class);
        String orderId = getIntent().getStringExtra("orderId");
        intent.putExtra("orderId" , orderId);
        startService(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Home.navView.setSelectedItemId(R.id.navigation_products);
                Home.orders.clear();
                Home.badgeDrawable.setVisible(false);
            }
        } , 100);
    }
}