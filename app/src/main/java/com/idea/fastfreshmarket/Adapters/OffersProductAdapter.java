package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OffersProductAdapter extends RecyclerView.Adapter<OffersProductAdapter.ProductViewHolder> {

    public ArrayList<Product> products;
    LayoutInflater inflater;
    Context context;
    public OffersProductAdapter(ArrayList<Product> products , Context context) {
        this.products = products;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final ProductViewHolder productViewHolder = new ProductViewHolder(inflater.inflate(R.layout.product_item_1 , parent , false));
        productViewHolder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                productViewHolder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams l = productViewHolder.itemView.getLayoutParams();
                l.height = productViewHolder.itemView.getWidth();
                productViewHolder.itemView.setLayoutParams(l);
            }
        });
        return productViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        if(product.getProductName()!=null){
            holder.infoLayout.setVisibility(View.VISIBLE);
            holder.productName.setText(product.getProductName());
            if(product.getProductImage()!=null) {
                loadImage(product , holder);
            }
            if (product.getProductOffer() != 0) {
                holder.productPriceBeforeOffer.setText(product.getProductPrice() + " EGP");
                holder.offerText.setText(product.getProductOffer() + "%");
                holder.productPriceBeforeOfferLayout.setVisibility(View.VISIBLE);
                holder.offerLayout.setVisibility(View.VISIBLE);
            }
            float productPriceAfterOffer = product.getProductPrice() - ((product.getProductOffer() / 100f) * product.getProductPrice());
            holder.productPrice.setText(  productPriceAfterOffer+ " EGP");
        }
        else{
            if(product.getProductCategory()!=null) {
                if(product.getProductCategory().equals("Fruits"))
                    holder.productImage.setImageDrawable(context.getDrawable(R.drawable.ic_fruits_loading));
            }
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        View offerLayout , productPriceBeforeOfferLayout , infoLayout;
        ImageView productImage;
        TextView productName , productPrice, productPriceBeforeOffer, offerText;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price_after_offer);
            productPriceBeforeOffer = itemView.findViewById(R.id.product_price_before_offer);
            productPriceBeforeOfferLayout = itemView.findViewById(R.id.product_price_before_offer_layout);
            offerLayout = itemView.findViewById(R.id.offer_layout);
            offerText = itemView.findViewById(R.id.offer_text);
            infoLayout = itemView.findViewById(R.id.info_layout);
        }
    }


    File temp;
    public void loadImage(final Product product , final OffersProductAdapter.ProductViewHolder holder){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReference();
        try { temp = new File("/data/data/com.idea.yassenmarket/cache/" + product.getProductImage()); } catch (Exception ignored) {}
        if (!temp.exists()) {
            storageReference.child("productImages/" + product.getProductImage()).getFile(temp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    temp = new File("/data/data/com.idea.yassenmarket/cache/" + product.getProductImage());
                    product.setProductImageUri(Uri.fromFile(temp));
                    holder.productImage.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.productImage.setImageDrawable(null);
                            holder.productImage.setImageURI(product.getProductImageUri());
                            holder.productImage.setScaleType(ImageView.ScaleType.FIT_XY);
                            holder.productImage.animate().alpha(1);
                        }
                    });    }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) { }
            });
        } else {
            product.setProductImageUri(Uri.fromFile(temp));
            holder.productImage.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    holder.productImage.setImageDrawable(null);
                    holder.productImage.setImageURI(product.getProductImageUri());
                    holder.productImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    holder.productImage.animate().alpha(1);
                }
            });
        }
    }
}
