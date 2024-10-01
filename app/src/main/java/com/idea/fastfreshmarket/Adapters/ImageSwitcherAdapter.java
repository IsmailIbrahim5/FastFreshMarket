package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.UI.activities.OpenedProduct;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.home.HomeFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageSwitcherAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    public ArrayList<Product> products;
    Activity activity;
    public ImageSwitcherAdapter(Activity activity , ArrayList<Product> products) {
        layoutInflater = LayoutInflater.from(activity);
        this.products = products;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = layoutInflater.inflate(R.layout.image_swithcer_layout, null);

        final ImageView imageView = view.findViewById(R.id.product_image);
        loadImage(products.get(position) , imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OpenedProduct.class);
                ActivityOptionsCompat options;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView , "product_image");
                Home.product = products.get(position);
                HomeFragment.scrolling = true;
                activity.startActivity(intent, options.toBundle());
            }
        });
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    File temp;
    public void loadImage(final Product product , final ImageView imageView){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReference();
        try { temp = new File("/data/data/com.idea.fastfreshmarket/cache/" + product.getProductImage()); } catch (Exception ignored) {}
        if (!temp.exists()) {
            storageReference.child("productImages/" + product.getProductImage()).getFile(temp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    temp = new File("/data/data/com.idea.yassenmarket/cache/" + product.getProductImage());
                    product.setProductImageUri(Uri.fromFile(temp));
                         //   Home.loadingBitmap(temp , imageView, imageView.getWidth(), imageView.getHeight(),activity.getContentResolver());
                    imageView.setImageURI(Uri.fromFile(temp));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } else {
            product.setProductImageUri(Uri.fromFile(temp));
            imageView.setImageURI(Uri.fromFile(temp));
        }
    }

}
