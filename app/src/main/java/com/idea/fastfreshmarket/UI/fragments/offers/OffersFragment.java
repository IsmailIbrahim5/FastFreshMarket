package com.idea.fastfreshmarket.UI.fragments.offers;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idea.fastfreshmarket.Adapters.ChooseAdapter;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Adapters.ProductsAdapter;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.products.Products;

import java.util.ArrayList;

public class OffersFragment extends Fragment {

    private OffersViewModel offersViewModel;
    int selected = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        offersViewModel = new OffersViewModel();
        View root = inflater.inflate(R.layout.fragment_offers, container, false);

        final RecyclerView offers = root.findViewById(R.id.offers);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        offers.setLayoutManager(gridLayoutManager);

        final View emptyCart = root.findViewById(R.id.empty_cart);
        offersViewModel.getOffers().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Product> products) {
                if(products.size() == 0){
                    offers.setAlpha(0);
                    offers.setVisibility(View.GONE);
                    emptyCart.animate().alpha(1);
                }
                else {
                    offers.setVisibility(View.VISIBLE);
                    offers.animate().alpha(1);
                    offers.setAdapter(new ProductsAdapter(products, (Home) getActivity(), "offers", 2));
                }
            }
        });

        ImageView category = root.findViewById(R.id.category);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyCart.animate().alpha(0);
                offers.setVisibility(View.VISIBLE);
                final ObjectAnimator alpha = ObjectAnimator.ofFloat(offers , "alpha" , 0f);
                alpha.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        offers.setAdapter(new ChooseAdapter(getContext() , 2 , new View.OnClickListener() {
                            @Override
                            public void onClick(final View view) {
                                ObjectAnimator alpha = ObjectAnimator.ofFloat(offers , "alpha" , 0);
                                alpha.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {

                                selected = (int) view.getTag();
                                switch (selected){
                                    case 0 :offersViewModel.generateOffers(""); break;
                                    case 1 :offersViewModel.generateOffers("new"); break;
                                    case 2 :offersViewModel.generateOffers("virus"); break;
                                    case 3 :offersViewModel.generateOffers("sweetAndSour"); break;
                                    case 4 :offersViewModel.generateOffers("sweets"); break;
                                    case 5 :offersViewModel.generateOffers("diet"); break;
                                    case 6 :offersViewModel.generateOffers("detergent"); break;
                                    case 7 :offersViewModel.generateOffers("bakery"); break;
                                    case 8 :offersViewModel.generateOffers("fruitsAndVegetables"); break;
                                    case 9 :offersViewModel.generateOffers("beauty"); break;
                                    case 10 :offersViewModel.generateOffers("groceries"); break;
                                    case 11 :offersViewModel.generateOffers("dairy"); break;
                                    case 12 :offersViewModel.generateOffers("frozen"); break;
                                    case 13 :offersViewModel.generateOffers("meatAndPoultry"); break;
                                    case 14 :offersViewModel.generateOffers("spices"); break;
                                    case 15 :offersViewModel.generateOffers("party"); break;
                                    case 16 :offersViewModel.generateOffers("school"); break;
                                    case 17 :offersViewModel.generateOffers("pets"); break;
                                    case 18 :offersViewModel.generateOffers("battery"); break;
                                }

                                    }
                                });
                                alpha.start();
                            }
                        } , selected , "offers"));
                        offers.scrollToPosition(0);
                        offers.animate().alpha(1);
                    }
                });
                alpha.start();
            }
        });
        FloatingActionButton searchFab = root.findViewById(R.id.search_fab);

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , Products.class);
                intent.putExtra("category" , "search");
                startActivity(intent);
            }
        });
        return root;
    }


}