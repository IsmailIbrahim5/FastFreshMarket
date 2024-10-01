package com.idea.fastfreshmarket.UI.fragments.products;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.Models.Order;
import com.idea.fastfreshmarket.Tools.DataBaseGenerator;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

class OrdersViewModel {
    private MutableLiveData<ArrayList<Order>> mOrder = new MutableLiveData<>();
    public ArrayList<String> ordersId;
    public ArrayList<Order> orders;

    public OrdersViewModel(Context context) {
        orders=new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput("orders");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ordersId = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();
            loadOrders(ordersId);
        }catch (Exception ignored){ }
    }



    public MutableLiveData<ArrayList<Order>> getOrder() {
        return mOrder;
    }


    public void loadOrders(final ArrayList<String> query) {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateOrders(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orders.add(snapshot.getValue(Order.class));
                    if(orders.size() == ordersId.size())
                        mOrder.postValue(orders);
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        }, query);
    }
}
