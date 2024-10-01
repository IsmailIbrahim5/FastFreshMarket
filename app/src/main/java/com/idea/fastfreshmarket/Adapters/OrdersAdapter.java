package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.UI.fragments.cart.CartFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ProductViewHolder> {
    public ArrayList<Product> orders;
    LayoutInflater inflater;
    float totalPrice;
    CartFragment cartFragment;
    public OrdersAdapter(ArrayList<Product> orders , CartFragment cartFragment) {
        this.orders = orders;
        inflater = LayoutInflater.from(cartFragment.getContext());
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public OrdersAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersAdapter.ProductViewHolder(inflater.inflate(R.layout.product_item_1 , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrdersAdapter.ProductViewHolder holder, final int position) {
        final Product product = orders.get(position);
        final float productPriceAfterOffer = product.getProductPrice() - ((product.getProductOffer() / 100f) * product.getProductPrice());
        holder.productName.setText(product.getProductName());
        holder.productImage.setImageURI(product.getProductImageUri());
        holder.unitPrice.setText(String.format("%.02f" ,productPriceAfterOffer));
        holder.qty.setText(String.valueOf(product.getQty()));

        totalPrice = product.getQty() * productPriceAfterOffer;
        holder.totalPrice.setText(String.format("%.02f",totalPrice));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getQty() < product.getMax()) {
                    product.setQty(product.getQty() + product.getStep());
                    holder.qty.setText(String.valueOf(product.getQty()));
                    totalPrice = product.getQty() * productPriceAfterOffer;
                    holder.totalPrice.setText(String.format("%.02f", totalPrice));
                    cartFragment.setTotalPrice();
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getQty() != 1) {
                    product.setQty(product.getQty()-product.getStep());
                    holder.qty.setText(String.valueOf(product.getQty()));
                    totalPrice = product.getQty() * productPriceAfterOffer;
                    holder.totalPrice.setText(String.format("%.02f",totalPrice));
                    cartFragment.setTotalPrice();
                }
                else
                    removeOrder(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeOrder(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage , add , remove;
        TextView productName , unitPrice, totalPrice, qty;
        View delete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            unitPrice = itemView.findViewById(R.id.unit_price);
            totalPrice = itemView.findViewById(R.id.total_price);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            qty = itemView.findViewById(R.id.qty);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    public void removeOrder(int position){
     orders.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orders.size());
        if(orders.size()!=0) {
            Home.badgeDrawable.setNumber(orders.size());
            cartFragment.setTotalPrice();
        }else{
            cartFragment.totalView.setVisibility(View.GONE);
            cartFragment.emptyCart.setVisibility(View.VISIBLE);
            Home.badgeDrawable.setVisible(false);
        }
    }
}
