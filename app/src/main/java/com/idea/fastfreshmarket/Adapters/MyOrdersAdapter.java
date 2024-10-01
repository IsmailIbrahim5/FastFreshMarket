package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.fastfreshmarket.Models.Order;
import com.idea.fastfreshmarket.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ProductViewHolder> {

    public ArrayList<Order> orders;
    LayoutInflater inflater;
    Context context;
    public MyOrdersAdapter(Context context , ArrayList<Order> orders) {
        this.orders = orders;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_item , parent , false);
         return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderId.setText(order.getOrderId());
        if(order.isDelivered())
            holder.orderStatus.setText(context.getString(R.string.delivered));
        else
            holder.orderStatus.setText(context.getString(R.string.delivering));
        holder.orderTime.setText(order.getOrderTime().replace("-", "/"));
        if(order.getCoupon()==0)
            holder.orderPrice.setText(String.valueOf(order.getTotalPrice()));
        else
            holder.orderPrice.setText(order.getTotalPrice() + " (" + order.getCoupon() + " "+context.getString(R.string.points_used)+")");
        holder.orderMeetUp.setText(order.getMeetUpLocation().getAddress());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView orderId , orderStatus, orderTime, orderPrice, orderMeetUp;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTime = itemView.findViewById(R.id.order_time);
            orderPrice = itemView.findViewById(R.id.order_total_price);
            orderMeetUp = itemView.findViewById(R.id.order_meet_up);
        }
    }
}
