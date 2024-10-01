package com.idea.fastfreshmarket.UI.activities;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.home.HomeFragment;

public class OpenedProduct extends AppCompatActivity {
    ImageView add , remove;
    SeekBar seekBar;
    TextView qtyText;
    float qty = 1;
    int progress = 10;
    float price;
    TextView totalPrice;
    LinearLayout totalView;
    Product product;
    boolean products;
    int max;
    float step;
    CardView imageCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_product);

        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
        product = Home.product;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.scrolling = false;
                supportFinishAfterTransition();
            }
        });

        max= product.getMax() * 10;
        step = product.getStep() * 10;
        final CollapsingToolbarLayout collapsing = findViewById(R.id.collapsing);

        collapsing.post(new Runnable() {
            @Override
            public void run() {
                if(ViewCompat.getLayoutDirection(collapsing) == ViewCompat.LAYOUT_DIRECTION_RTL)
                    collapsing.setCollapsedTitleGravity(Gravity.RIGHT);
            }
        });
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        seekBar = findViewById(R.id.seek_bar);

        seekBar.setMax(max);

        ImageView productImage = findViewById(R.id.product_image);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        seekBar.setProgress((int) (progress+step), true);
                    else
                        seekBar.setProgress((int) (progress+step));
                }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    seekBar.setProgress((int) (progress-step),true);
                else
                    seekBar.setProgress((int) (progress-step));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i % step == 0) {
                    progress = i;
                    qty = progress / 10f;
                    qtyText.setText(qty + " " + product.getSoldBy());
                    setPrice();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        qtyText = findViewById(R.id.qty);
        final FrameLayout confirmButton = findViewById(R.id.confirm_button);
        totalPrice = findViewById(R.id.total_price);
        TextView availabilityText = findViewById(R.id.availability_text),
                producerText = findViewById(R.id.producer_text),
                soldByText = findViewById(R.id.sold_by_text),
                priceBeforeOffer = findViewById(R.id.price_before_offer),
                priceAfterOffer = findViewById(R.id.price_after_offer),
                detailsText = findViewById(R.id.description_text);

        priceBeforeOffer.setPaintFlags(priceBeforeOffer.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        totalView =findViewById(R.id.total_view);

        try {
            products= getIntent().getBooleanExtra("products",false);
        }catch (Exception ignored){}
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty(qty);
                boolean add = true;
                for (int i = 0 ; i < Home.orders.size() ; i++) {
                    if (product.getProductName().equals(Home.orders.get(i).getProductName())) {
                        Home.orders.get(i).setQty((int) (Home.orders.get(i).getQty() + qty));
                        add = false;
                        break;
                    }
                }
                if(add)
                    add(product);
                Snackbar snackbar = Snackbar.make(coordinatorLayout , getString(R.string.added) + qty + " " +product.getSoldBy(), Snackbar.LENGTH_SHORT);
                snackbar.setAction(getString(R.string.move_to_cart), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(products)
                            setResult(RESULT_OK, null);
                        supportFinishAfterTransition();
                        HomeFragment.scrolling = false;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!products)
                                    Home.navView.setSelectedItemId(R.id.navigation_cart);
                            }
                        } , 500);
                    }
                });
                snackbar.setBackgroundTint(getResources().getColor(R.color.green));
                snackbar.setTextColor(Color.WHITE);
                View snackBarLayout = snackbar.getView();
                TextView textView = snackBarLayout.findViewById(com.google.android.material.R.id.snackbar_action);
                Drawable cart = getDrawable(R.drawable.ic_cart);
                cart.setTint(Color.parseColor("#80FFFFFF"));
                             textView.setCompoundDrawablesWithIntrinsicBounds(cart, null, null, null);

                textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));

                snackbar.show();
            }
        });

        setPrice();


        final View availability = findViewById(R.id.availability_layout) ,
                producer = findViewById(R.id.producer_layout) ,
                soldBy = findViewById(R.id.sold_by_layout) ,
                price = findViewById(R.id.price_layout),
                details = findViewById(R.id.description_layout),
                order = findViewById(R.id.order_text),
                seek = findViewById(R.id.seek_layout),
                newLayout = findViewById(R.id.new_layout),
                offer_layout = findViewById(R.id.offer_layout);
        imageCard = findViewById(R.id.image_card);
        final ViewSwitcher switcher = findViewById(R.id.switcher);
        final EditText editText = findViewById(R.id.qty_edit);

        TextView offerText = findViewById(R.id.offer_text);
        qtyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.showNext();
                    editText.setText(String.valueOf(qty));
                    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            try {
                                if(Float.valueOf(textView.getText().toString()) <= 10 & Float.valueOf(textView.getText().toString()) >= 0.5) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    qty = Float.valueOf(textView.getText().toString());
                                    qtyText.setText(qty + " " + product.getSoldBy());
                                    seekBar.setProgress((int) (qty*10));
                                    setPrice();
                                    switcher.showPrevious();
                                }
                                else
                                    Toast.makeText(OpenedProduct.this, getString(R.string.enter_quantity)+"0.5"+getString(R.string.and)+seekBar.getMax()/10, Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                Toast.makeText(OpenedProduct.this, getString(R.string.enter_valid), Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                }
        });


getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
    @Override
    public void onTransitionStart(Transition transition) {
        availability.animate().translationY(0).setDuration(500).start();
        producer.animate().translationY(0).setDuration(500).start();
        soldBy.animate().translationY(0).setDuration(500).start();
        price.animate().translationY(0).setDuration(500).start();
        details.animate().translationY(0).setDuration(500).start();
        order.animate().translationY(0).setDuration(500).start();
        switcher.animate().translationY(0).setDuration(500).start();
        seek.animate().translationY(0).setDuration(500).start();
        totalView.animate().translationY(0).setDuration(500).start();
        ObjectAnimator curve= ObjectAnimator.ofFloat(imageCard , "radius" , 24 , 1);
        curve.start();
    }

    @Override
    public void onTransitionEnd(Transition transition) {

    }

    @Override
    public void onTransitionCancel(Transition transition) {

    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }
});

        collapsing.setTitle(product.getProductName());
        if(product.isProductNew())
            newLayout.setVisibility(View.VISIBLE);
        if(product.getProductOffer() != 0){
            offer_layout.setVisibility(View.VISIBLE);
            offerText.setText(product.getProductOffer()+"%");
            priceBeforeOffer.setText(String.valueOf(product.getProductPrice()));
        }
        productImage.setImageURI(product.getProductImageUri());
        producerText.setText(product.getProductProducer());
        soldByText.setText(product.getSoldBy());
        float productPriceAfterOffer = product.getProductPrice() - ((product.getProductOffer() / 100f) * product.getProductPrice());
        priceAfterOffer.setText(String.valueOf(productPriceAfterOffer));
        detailsText.setText(product.getDetails());
        qtyText.setText("1 " + product.getSoldBy());
        if(!product.isAvailable()){
            availabilityText.setText(getString(R.string.not_available));
            qtyText.setText(getString(R.string.not_available_for_order));
            qtyText.setClickable(false);
            seekBar.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        }
    }

    public void setPrice(){
        float productPriceAfterOffer = product.getProductPrice() - ((product.getProductOffer() / 100f) * product.getProductPrice());
        price = qty * productPriceAfterOffer;
        totalPrice.setText(price +" "+getString(R.string.total));
    }

    public void add (Product product){
        Home.orders.add(product);
        Home.badgeDrawable.setNumber(Home.orders.size());
        if(!Home.badgeDrawable.isVisible())
            Home.badgeDrawable.setVisible(true);
    }

    @Override
    public void onBackPressed() {
        HomeFragment.scrolling = false;
        super.onBackPressed();
    }


}