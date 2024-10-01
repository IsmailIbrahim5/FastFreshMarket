package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.UI.activities.OpenedProduct;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.products.Products;
import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    public ArrayList<Product> products;
    LayoutInflater inflater;
    File temp;
    String category;
    int spanCount;
    ArrayList<Product> orders;
    Activity activity;
    public ProductsAdapter(ArrayList<Product> products , Activity activity  , String category , int spanCount) {
        this.products = products;
        this.category = category;
        this.spanCount = spanCount;
        this.orders = Home.orders;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item , parent , false);
        if(spanCount > 1){
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final Product product = products.get(position);
        switch (category) {
            case "new":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_new));
                break;
            case "virus":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_mask));
                break;
            case "sweetAndSour":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_sweet_and_sour));
                break;
            case "diet":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_diet));
                break;
            case "sweets":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_sweets));
                break;
            case "detergent":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_detergent));
                break;
            case "bakery":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_bread));
                break;
            case "beauty":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_beauty));
                break;
            case "groceries":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_groceries));
                break;
            case "fruitAndVegetables":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_fruits_and_vegetables));
                break;
            case "dairy":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_dairy));
                break;
            case "frozen":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_frozen_food));
                break;
            case "meatAndPoultry":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_meat_and_poultry));
                break;
            case "spices":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_spices));
                break;
            case "party":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_party));
                break;
            case "school":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_school_supplies));
                break;
            case "pets":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_pets_food));
                break;
            case "battery":
                holder.productImage.setImageDrawable(activity.getDrawable(R.drawable.ic_battery));
                break;
        }
        if (product.getProductName() != null) {
            holder.infoLayout.setVisibility(View.VISIBLE);
            holder.productName.setText(product.getProductName());
            if (product.getProductImage() != null)
                loadImage(product, holder);
            if (product.getProductOffer() != 0) {
                holder.productPriceBeforeOffer.setText(product.getProductPrice() + activity.getString(R.string.currency));
                holder.offerText.setText(product.getProductOffer() + "%");
                holder.productPriceBeforeOfferLayout.setVisibility(View.VISIBLE);
                holder.offerLayout.setVisibility(View.VISIBLE);
            }
            if (product.isProductNew())
                holder.newLayout.setVisibility(View.VISIBLE);
            float productPriceAfterOffer = product.getProductPrice() - ((product.getProductOffer() / 100f) * product.getProductPrice());
            holder.productPrice.setText(productPriceAfterOffer + activity.getString(R.string.currency));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, OpenedProduct.class);
                    Pair<View, String> p1 = Pair.create(holder.imageCard, "image_card");
                    Pair<View, String> p2 = Pair.create(holder.offerLayout, "offer_layout");
                    Pair<View, String> p3 = Pair.create(holder.newLayout, "new_layout");
                    ActivityOptionsCompat options;
                    if(product.getProductOffer()!=0 & product.isProductNew()) {
                        options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity, p1, p2, p3);
                    }
                    else if(product.getProductOffer()!=0) {
                        options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity, p1, p2);
                    }
                    else if(product.isProductNew()){
                        options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity, p1, p3);
                    }else {
                        options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1);
                    }
                    Home.product = product;
                    if (activity instanceof Products) {
                        intent.putExtra("products", true);
                        activity.startActivityForResult(intent, 5,options.toBundle());
                    }
                    else
                        activity.startActivity(intent, options.toBundle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        View offerLayout , newLayout ,productPriceBeforeOfferLayout , infoLayout , imageCard;
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
            newLayout = itemView.findViewById(R.id.new_layout);
            imageCard = itemView.findViewById(R.id.image_card);
        }
    }

    public void loadImage(final Product product , final ProductViewHolder holder){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReference();
            try { temp = new File("/data/data/com.idea.fastfreshmarket/cache/" + product.getProductImage()); } catch (Exception ignored) {}
            if (!temp.exists()) {
                storageReference.child("productImages/" + product.getProductImage()).getFile(temp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        temp = new File("/data/data/com.idea.fastfreshmarket/cache/" + product.getProductImage());
                        product.setProductImageUri(Uri.fromFile(temp));
                        holder.productImage.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                holder.productImage.setImageDrawable(null);
                                holder.productImage.setScaleType(ImageView.ScaleType.FIT_XY);
                                holder.productImage.setScaleX(1);
                                holder.productImage.setScaleY(1);
                                ObjectAnimator animator = ObjectAnimator.ofFloat(holder.productImage , "alpha",1);
                                animator.start();
                                holder.productImage.setImageURI(product.getProductImageUri());
                            }
                        });    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            } else {
                product.setProductImageUri(Uri.fromFile(temp));
                holder.productImage.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.productImage.setImageDrawable(null);
                        holder.productImage.setImageURI(product.getProductImageUri());
                        holder.productImage.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.productImage.setScaleX(1);
                        holder.productImage.setScaleY(1);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.productImage , "alpha",1);
                        animator.start();
                    }
                });
            }
    }


}
