package com.idea.fastfreshmarket.Models;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

public class Rating {
    private float deliveryRate;
    private float packageRate;
    private float productsRate;
    private String comments;

    public Rating() {
    }

    public Rating(float deliveryRate, float packageRate, float productsRate, String comments) {
        this.deliveryRate = deliveryRate;
        this.packageRate = packageRate;
        this.productsRate = productsRate;
        this.comments = comments;
    }

    public float getDeliveryRate() {
        return deliveryRate;
    }

    public void setDeliveryRate(float deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public float getPackageRate() {
        return packageRate;
    }

    public void setPackageRate(float packageRate) {
        this.packageRate = packageRate;
    }

    public float getProductsRate() {
        return productsRate;
    }

    public void setProductsRate(float productsRate) {
        this.productsRate = productsRate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
