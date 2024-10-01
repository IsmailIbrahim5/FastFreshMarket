package com.idea.fastfreshmarket.UI.fragments.offers;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Tools.DataBaseGenerator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OffersViewModel {

    private MutableLiveData<ArrayList<Product>> mOffers;
    ArrayList<Product> offers = new ArrayList<>();
    public OffersViewModel() {
        mOffers = new MutableLiveData<>();
        for(int i = 0 ; i < 10 ; i++)
            offers.add(new Product());

        mOffers.setValue(offers);

        generateOffers("");
    }

    String key;
    public void generateOffers (final String category){
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offers.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if(snapshot1.getKey().equals(category)|category.isEmpty()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            if(!snapshot2.getValue().equals("none")) {
                                Product product = snapshot2.getValue(Product.class);
                                if (product.getProductOffer() != 0) {
                                    offers.add(product);
                                }
                            }
                        }
                    }
                }

                mOffers.postValue(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public LiveData<ArrayList<Product>> getOffers() {
        return mOffers;
    }
}