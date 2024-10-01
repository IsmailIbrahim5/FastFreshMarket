package com.idea.fastfreshmarket.Models;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String userId;
    private String orderTime;
    private float totalPrice;
    private Location meetUpLocation;
    private boolean delivered;
    private int coupon;
    private Rating rating;

    public Order () { }

    public Order(String userId, String orderTime, Location meetUpLocation , float totalPrice , String orderId , int coupon,  Rating rating) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.meetUpLocation = meetUpLocation;
        this.coupon = coupon;
        this.rating = rating;
    }

    public Order(String orderId, String userId, String orderTime, float totalPrice, Location meetUpLocation, boolean delivered) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.meetUpLocation = meetUpLocation;
        this.delivered = delivered;
    }

    public Order(String userId, String orderTime, Location meetUpLocation , float totalPrice , String orderId , int coupon) {
        this.userId = userId;
        this.orderTime = orderTime;
        this.meetUpLocation = meetUpLocation;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.coupon= coupon;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Location getMeetUpLocation() {
        return meetUpLocation;
    }

    public void setMeetUpLocation(Location meetUpLocation) {
        this.meetUpLocation = meetUpLocation;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
