package com.idea.fastfreshmarket.UI.fragments.products;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.idea.fastfreshmarket.Adapters.ChooseAdapter;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.UI.alertDialogs.Loading;
import com.idea.fastfreshmarket.Adapters.MyOrdersAdapter;
import com.idea.fastfreshmarket.Models.Order;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Adapters.ProductsAdapter;
import com.idea.fastfreshmarket.R;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    ProductsViewModel productsViewModel;
    int selected = -1;
    String child = null;
    RecyclerView productsView;
    ChooseAdapter chooseAdapter;
    String category;
    View emptyCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        productsView = findViewById(R.id.products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        productsView.setLayoutManager(gridLayoutManager);

        TextView title = findViewById(R.id.title);

        FrameLayout searchLayout = findViewById(R.id.search_layout);
        EditText searchBar = findViewById(R.id.search_bar);
        ImageView filter = findViewById(R.id.filter);
        emptyCart = findViewById(R.id.empty_cart);
        try {
            Resources r = getResources();
            category = getIntent().getStringExtra("category");

            chooseAdapter = new ChooseAdapter(Products.this, 2, new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    productsView.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                            selected = (int) view.getTag();
                            if(selected == 0)
                                child = "all";
                            else {
                                if (category.equals("sweets")) {
                                    switch (selected) {
                                        case 1:
                                            child = "chips";
                                            break;
                                        case 2:
                                            child = "chocolates";
                                            break;
                                        case 3:
                                            child = "biscuits";
                                            break;
                                        case 4:
                                            child = "ice_cream";
                                            break;
                                        case 5:
                                            child = "juices";
                                            break;
                                        case 6:
                                            child = "coke";
                                            break;
                                        case 7:
                                            child = "water";
                                            break;
                                    }
                                } else if (category.equals("beauty")) {
                                    switch (selected) {
                                        case 1:
                                            child = "skin_and_body_care";
                                            break;
                                        case 2:
                                            child = "hair_care";
                                            break;
                                        case 3:
                                            child = "women_stuff";
                                            break;
                                        case 4:
                                            child = "men_care";
                                            break;
                                        case 5:
                                            child = "baby_care";
                                            break;
                                        case 6:
                                            child = "oral_and_dental_care";
                                            break;
                                        case 7:
                                            child = "shoes_care";
                                            break;
                                    }
                                } else if (category.equals("detergents")) {
                                    switch (selected) {
                                        case 1:
                                            child = "food_wrappers";
                                            break;
                                        case 2:
                                            child = "insecticides";
                                            break;
                                        case 3:
                                            child = "washing_powders";
                                            break;
                                        case 4:
                                            child = "air_fresheners";
                                            break;
                                        case 5:
                                            child = "toilet_paper";
                                            break;
                                        case 6:
                                            child = "cleaning_tools";
                                            break;
                                        case 7:
                                            child = "kitchen_utensils";
                                            break;
                                    }
                                } else if (category.equals("groceries")) {
                                    switch (selected) {
                                        case 1:
                                            child = "tea_and_coffee";
                                            break;
                                        case 2:
                                            child = "sauces";
                                            break;
                                        case 3:
                                            child = "oils";
                                            break;
                                        case 4:
                                            child = "magarine";
                                            break;
                                        case 5:
                                            child = "canned_food";
                                            break;
                                        case 6:
                                            child = "honey_and_dates";
                                            break;
                                        case 7:
                                            child = "rice_and_pasta";
                                            break;
                                    }
                                } else if (category.equals("fruitsAndVegetables")) {
                                    switch (selected) {
                                        case 1:
                                            child = "fresh_fruits";
                                            break;
                                        case 2:
                                            child = "fresh_vegetables";
                                            break;
                                        case 3:
                                            child = "cooked_vegetables";
                                            break;
                                    }
                                } else {
                                    switch (selected) {
                                        case 1:
                                            child = "fresh_meat";
                                            break;
                                        case 2:
                                            child = "fresh_poultry";
                                            break;
                                    }
                                }
                            }
                            if(!child.equals("all"))
                                productsViewModel = new ProductsViewModel(category, child);
                            else
                                productsViewModel = new ProductsViewModel(category);
                            productsViewModel.getProducts().observe(Products.this, new Observer<ArrayList<Product>>() {
                                @Override
                                public void onChanged(ArrayList<Product> products) {
                                    ObjectAnimator alpha = ObjectAnimator.ofFloat(productsView, "alpha", 1);
                                    if(products.size() == 0) {
                                        emptyCart.animate().alpha(1);
                                        productsView.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        productsView.setAdapter(new ProductsAdapter(products, Products.this, category, 2));
                                        alpha.start();
                                    }
                                }
                            });

                        }
                    });

                }
            }, selected, category);
            if (category.equals("sweets") |
                    category.equals("detergent") |
                    category.equals("fruitsAndVegetables") |
                    category.equals("beauty") |
                    category.equals("groceries") |
                    category.equals("meatAndPoultry")) {

                productsView.setAdapter(chooseAdapter);
                filter.setVisibility(View.VISIBLE);
                filter.setClickable(true);
                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(emptyCart.getAlpha() == 1) {
                            emptyCart.animate().alpha(0);
                        }
                        productsView.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                chooseAdapter.setSelected(selected);
                                productsView.setAdapter(chooseAdapter);
                                ObjectAnimator alpha = ObjectAnimator.ofFloat(productsView , "alpha" , 1);
                                productsView.setVisibility(View.VISIBLE);
                                alpha.start();
                                productsView.scrollToPosition(0);
                            }
                        });
                    }
                });
            }else{
                if(!category.equalsIgnoreCase("orders")) {
                    productsViewModel = new ProductsViewModel(category);
                    productsViewModel.getProducts().observe(Products.this, new Observer<ArrayList<Product>>() {
                        @Override
                        public void onChanged(ArrayList<Product> products) {
                            if (products.size() == 0) {
                                emptyCart.setAlpha(1);
                                productsView.setAlpha(0);
                            } else
                                productsView.setAdapter(new ProductsAdapter(products, Products.this, category, 2));
                        }
                    });
                }else{
                    final Loading loading= new Loading(this);
                    loading.start();

                    gridLayoutManager.setSpanCount(1);
                    gridLayoutManager.setReverseLayout(true);
                    final OrdersViewModel ordersViewModel= new OrdersViewModel(this);
                    if(ordersViewModel.ordersId.size()==0)
                        loading.dismiss(); 
                    else {
                        ordersViewModel.getOrder().observe(this, new Observer<ArrayList<Order>>() {
                            @Override
                            public void onChanged(ArrayList<Order> orders) {
                                productsView.setAdapter(new MyOrdersAdapter(Products.this,orders));
                                    loading.dismiss();
                            }
                        });
                    }
                }
            }

            switch (category) {
                case "new":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.news));
                    break;
                case "virus":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.virus));
                    break;
                case "sweetAndSour":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.sweetAndSour));
                    break;
                case "diet":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.diet));
                    break;
                case "sweets":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.sweets));
                    break;
                case "detergent":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.detergent));
                    break;
                case "bakery":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.bakery));
                    break;
                case "beauty":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.beauty));
                    break;
                case "groceries":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.groceries));
                    break;
                case "fruitsAndVegetables":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.fruitsAndVegetables));
                    break;
                case "dairy":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.dairy));
                    break;
                case "frozen":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.frozen));
                    break;
                case "meatAndPoultry":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.meatAndPoultry));
                    break;
                case "spices":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.spices));
                    break;
                case "party":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.party));
                    break;
                case "school":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.school));
                    ;
                    break;
                case "pets":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.pets));
                    break;
                case "battery":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.batteries));
                    break;
                case "search":
                    searchLayout.setVisibility(View.VISIBLE);
                    break;
                case "orders":
                    title.setVisibility(View.VISIBLE);
                    title.setText(r.getString(R.string.my_orders));
                    break;
            }
        } catch (Exception ignored) {}
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ignored) {
                }
                productsViewModel.searchProducts(textView.getText().toString());
                return true;
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(child!=null) {
            if (!child.equals("all")) {
                child = "all";
                chooseAdapter.setSelected(0);
                productsViewModel = new ProductsViewModel(category);
                productsViewModel.getProducts().observe(Products.this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(ArrayList<Product> products) {
                        final ObjectAnimator alpha = ObjectAnimator.ofFloat(productsView, "alpha", 1);
                        if (products.size() == 0) {
                            emptyCart.animate().alpha(1);
                            productsView.setVisibility(View.INVISIBLE);
                        } else {
                            emptyCart.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    productsView.setVisibility(View.VISIBLE);
                                    alpha.start();
                                }
                            });
                            productsView.setAdapter(new ProductsAdapter(products, Products.this, category, 2));
                        }
                    }
                });
            }
            else{
                finish();
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
            }
        }
        else{
            finish();
            overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_down);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5)
            if (resultCode == RESULT_OK) {
                onBackPressed();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Home.navView.setSelectedItemId(R.id.navigation_cart);
                    }
                } , 500);
            }
    }
}