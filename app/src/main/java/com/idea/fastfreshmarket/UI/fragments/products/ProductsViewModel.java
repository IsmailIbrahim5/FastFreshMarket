package com.idea.fastfreshmarket.UI.fragments.products;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Tools.DataBaseGenerator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

class ProductsViewModel {
    private MutableLiveData<ArrayList<Product>> mProducts = new MutableLiveData<>();
    ArrayList<Product> products = new ArrayList<>();

    public ProductsViewModel(String category) {
        if (!category.equals("search")) {
            for (int i = 0; i < 10; i++) {
                products.add(new Product());
            }

            mProducts.setValue(products);

            if (category.equals("new"))
                loadNew();
            else
                loadProducts(category);
        }
    }

    public ProductsViewModel(String category , String child) {

            for (int i = 0; i < 10; i++) {
                products.add(new Product());
            }

            mProducts.setValue(products);
             loadChildOfProducts(category , child);
    }

    public MutableLiveData<ArrayList<Product>> getProducts() {
        return mProducts;
    }


    public void loadProducts(final String query) {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Product product = snapshot1.getValue(Product.class);
                        products.add(product);
                    }
                    mProducts.postValue(products);
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        }, query);
    }

    public void loadChildOfProducts(final String query , final String child) {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Product product = snapshot1.getValue(Product.class);
                        if(product.getProductCategory().equalsIgnoreCase(child))
                            products.add(product);
                    }

            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        }, query);
    }

    public void searchProducts(final String query) {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if(!snapshot2.getValue().equals("none")) {

                            Product product = snapshot2.getValue(Product.class);
                            if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                                products.add(product);
                            }
                        }
                    }
                }
                mProducts.postValue(products);
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loadNew() {
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if(!snapshot2.getValue().equals("none")) {

                            Product product = snapshot2.getValue(Product.class);
                            if (product.isProductNew())
                                products.add(product);
                        }
                    }
                }
                mProducts.postValue(products);
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
