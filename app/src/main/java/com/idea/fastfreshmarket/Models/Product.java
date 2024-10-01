package com.idea.fastfreshmarket.Models;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.net.Uri;
import java.io.Serializable;

public class Product implements Serializable {
    private String productImage;
    private String productName;
    private String productCategory;
    private String productProducer = "Unknown";
    private String soldBy;
    private String details;
    private boolean available = true;
    private transient Uri productImageUri;
    private float productPrice;
    private int productOffer;
    private boolean productSpecial , productNew;
    private float qty = 1;
    private int position;
    private int max = 10;
    private float step = 1;

    public Product(){
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public int getProductOffer() {
        return productOffer;
    }

    public Uri getProductImageUri() {
        return productImageUri;
    }

    public void setProductImageUri(Uri productImageUri) {
        this.productImageUri = productImageUri;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isProductSpecial() {
        return productSpecial;
    }

    public boolean isProductNew() {
        return productNew;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getProductProducer() {
        return productProducer;
    }

    public void setProductProducer(String productProducer) {
        this.productProducer = productProducer;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }
}


