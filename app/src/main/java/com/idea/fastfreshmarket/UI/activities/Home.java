package com.idea.fastfreshmarket.UI.activities;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.UI.alertDialogs.MyPoints;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.alertDialogs.Rate;
import com.idea.fastfreshmarket.Models.User;
import com.idea.fastfreshmarket.UI.fragments.products.Products;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class Home extends AppCompatActivity {

    public static ArrayList<Product> orders = new ArrayList<>();

    DrawerLayout mDrawerLayout;
    public static BottomNavigationView navView;
    public static BadgeDrawable badgeDrawable;
    public static Product product;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try { FirebaseDatabase.getInstance().setPersistenceEnabled(true); }catch (Exception ignored){}

        try{
            boolean rating = getIntent().getBooleanExtra("rating" , false);
            String orderId = getIntent().getStringExtra("order");
            if(rating) {
                Rate rate = new Rate(this , orderId);
                rate.start();
            }
        }catch (Exception ignored){ }


        navView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        badgeDrawable = navView.getOrCreateBadge(R.id.navigation_cart);
        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
        badgeDrawable.setVisible(false);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId()==R.id.nav_points){
                   MyPoints myPoints = new MyPoints(Home.this);
                   myPoints.start();
               }else if(item.getItemId() == R.id.nav_call_us_button){
                   String phone = "+201099238168";
                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                   startActivity(intent);
               }
               else {
                   Intent intent = new Intent(Home.this, Products.class);
                   switch (item.getItemId()) {
                       case R.id.nav_new_button:
                           intent.putExtra("category", "new");
                           break;
                       case R.id.nav_virus_prevention:
                           intent.putExtra("category", "virus");
                           break;
                       case R.id.nav_sweet_and_sour_button:
                           intent.putExtra("category", "sweetAndSour");
                           break;
                       case R.id.nav_diet_button:
                           intent.putExtra("category", "diet");
                           break;
                       case R.id.nav_sweets_button:
                           intent.putExtra("category", "sweets");
                           break;
                       case R.id.nav_detergent_button:
                           intent.putExtra("category", "detergent");
                           break;
                       case R.id.nav_bakery_button:
                           intent.putExtra("category", "bakery");
                           break;
                       case R.id.nav_fruits_and_vegetables_button:
                           intent.putExtra("category", "fruitsAndVegetables");
                           break;
                       case R.id.nav_beauty_button:
                           intent.putExtra("category", "beauty");
                           break;
                       case R.id.nav_groceries_button:
                           intent.putExtra("category", "groceries");
                           break;
                       case R.id.nav_dairy_button:
                           intent.putExtra("category", "dairy");
                           break;
                       case R.id.nav_frozen_button:
                           intent.putExtra("category", "frozen");
                           break;
                       case R.id.nav_meat_and_poultry_button:
                           intent.putExtra("category", "meatAndPoultry");
                           break;
                       case R.id.nav_spices_button:
                           intent.putExtra("category", "spices");
                           break;
                       case R.id.nav_party_button:
                           intent.putExtra("category", "party");
                           break;
                       case R.id.nav_school_button:
                           intent.putExtra("category", "school");
                           break;
                       case R.id.nav_pets_button:
                           intent.putExtra("category", "pets");
                           break;
                       case R.id.nav_batteries_button:
                           intent.putExtra("category", "batteries");
                           break;
                       case R.id.nav_my_orders_button:
                           intent.putExtra("category", "orders");
                           break;
                   }
                   startActivityForResult(intent, 100);
                   overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                   mDrawerLayout.closeDrawer(GravityCompat.START);
               }
                return true;
            }
        });
        navView.setOnNavigationItemReselectedListener(null);

        String userId = null;
        try {
            FileInputStream fis = openFileInput("userId");
            Reader in = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(in);
            userId = bufferedReader.readLine();
            bufferedReader.close();
            in.close();
        } catch (Exception ignored) {}

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                ((TextView)navigationView.getHeaderView(0).findViewById(R.id.user_name)).setText(user.getName());
                if(user.getGender().equals("Female"))
                    ((ImageView)navigationView.getHeaderView(0).findViewById(R.id.user_photo)).setImageDrawable(getResources().getDrawable(R.drawable.ic_female));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try{
            boolean news = getIntent().getBooleanExtra("news" , false);
            if(news) {
                Intent intent = new Intent(Home.this, Products.class);
                intent.putExtra("category", "new");
                startActivityForResult(intent, 100);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        }catch (Exception ignored){ }
    }

    public void seeMore(View view) {
        String category = (String) view.getTag();
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        if(category.equals("offers"))
            navView.setSelectedItemId(R.id.navigation_offers);
        else{
            Intent intent = new Intent(Home.this , Products.class);
            intent.putExtra("category" , category);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
        Toast.makeText(this, (String)view.getTag(), Toast.LENGTH_SHORT).show();
    }



    public void goShopping(View view) {
        navView.setSelectedItemId(R.id.navigation_products);
    }

    public void navButton(View view) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

}