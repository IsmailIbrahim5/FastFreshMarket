package com.idea.fastfreshmarket.Models;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import java.io.Serializable;

public class DatabaseProduct implements Serializable {
    private String productName;
    private String productCategory;
    private String productProducer;
    private String soldBy;
    private float productPrice;
    private int productOffer;
    private float qty = 1;

    public DatabaseProduct(){
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


    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }




    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductOffer(int productOffer) {
        this.productOffer = productOffer;
    }
}


