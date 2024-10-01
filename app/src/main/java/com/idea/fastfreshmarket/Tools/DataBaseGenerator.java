package com.idea.fastfreshmarket.Tools;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataBaseGenerator {

    public void generateProducts(ValueEventListener valueEventListener){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference offersRef = firebaseDatabase.getReference().child("products");
        offersRef.keepSynced(true);
        offersRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public void generateProducts(ValueEventListener valueEventListener , String child){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference offersRef = firebaseDatabase.getReference().child("products").child(child);
        offersRef.keepSynced(true);
        offersRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void generateProducts(ValueEventListener valueEventListener , String child , int limit){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference offersRef = firebaseDatabase.getReference().child("products").child(child);
        offersRef.keepSynced(true);
        if(limit != 0){
            Query query = offersRef.orderByChild("productName").limitToFirst(limit);
            query.addListenerForSingleValueEvent(valueEventListener);
        }
        else
            offersRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public void generateOrders(ValueEventListener valueEventListener , ArrayList<String> orderIds){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        for(int i = 0 ; i < orderIds.size() ; i++) {
            DatabaseReference reference = firebaseDatabase.getReference().child("orders").child(orderIds.get(i)).child("Info");
            reference.keepSynced(true);
            reference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

}
