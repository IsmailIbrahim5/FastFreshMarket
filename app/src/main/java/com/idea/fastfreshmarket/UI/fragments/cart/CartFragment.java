package com.idea.fastfreshmarket.UI.fragments.cart;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idea.fastfreshmarket.UI.alertDialogs.Consume;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.Adapters.OrdersAdapter;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.SelectingLocation;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    float totalPrice;
    TextView totalPriceText;
    OrdersAdapter ordersAdapter;
    public FrameLayout confirmButton;
    public View emptyCart , totalView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        RecyclerView ordersView = root.findViewById(R.id.orders);

        totalView  = root.findViewById(R.id.total_view);
        ArrayList<Product> orders;
        orders = Home.orders;
        if(orders.size()!=0) {
            ordersAdapter = new OrdersAdapter(orders, this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            ordersView.setLayoutManager(linearLayoutManager);
            ordersView.setAdapter(ordersAdapter);

            confirmButton = root.findViewById(R.id.confirm_button);
            totalPriceText = root.findViewById(R.id.total_price);
            emptyCart = root.findViewById(R.id.empty_cart);

            totalView.setVisibility(View.VISIBLE);

            emptyCart.setVisibility(View.GONE);

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isNetworkAvailable()) {
                        if (Home.user.getPoints() > 100) {
                            Consume consume = new Consume(getActivity(), totalPrice);
                            consume.start();
                        } else
                            Continue();
                    }else
                        Toast.makeText(getContext(), "Sorry, You Don't Have an Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
            setTotalPrice();
        }
        return root;
    }


    public void setTotalPrice() {
        totalPrice = 0;
        if(ordersAdapter.orders.size()!=0) {
            for (int i = 0; i < ordersAdapter.orders.size(); i++) {
                Product p = ordersAdapter.orders.get(i);
                final float productPriceAfterOffer = p.getProductPrice() - ((p.getProductOffer() / 100f) * p.getProductPrice());
                totalPrice += p.getQty() * productPriceAfterOffer;

            }
            totalPriceText.setText(String.format("%.02f", totalPrice) + getString(R.string.total));
        }else{
            totalView.setVisibility(View.GONE);
        }
    }

    public void Continue (){
        Intent intent = new Intent(getContext()  , SelectingLocation.class);
        intent.putExtra("totalPrice" , totalPrice);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_to_right , R.anim.right_to_left);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}