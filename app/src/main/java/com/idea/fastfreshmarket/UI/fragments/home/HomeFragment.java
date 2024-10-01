package com.idea.fastfreshmarket.UI.fragments.home;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.Adapters.ImageSwitcherAdapter;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Adapters.ProductsAdapter;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.fragments.products.Products;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ViewPager dealsSwitcher;
    int dotsCount = 0, page = 0;
    ImageView[] dots;
    public static boolean scrolling;
    View specialsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dealsSwitcher = root.findViewById(R.id.deals_image_switcher);
        specialsView = root.findViewById(R.id.specials);

        final LinearLayout dotIndicator = root.findViewById(R.id.dots_indicator);
        homeViewModel.getSpecials().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(ArrayList<Product> products) {
                        if(products !=null) {
                            specialsView.setVisibility(View.VISIBLE);
                            dotsCount = products.size();
                            dots = new ImageView[dotsCount];
                            for (int i = 0 ; i < dotsCount ; i++) {
                                 dots[i] = new ImageView(getContext());
                                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(8, 0, 8, 0);
                                dots[i].setLayoutParams(layoutParams);
                                dotIndicator.addView(dots[i]);
                            }
                                dots[0].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));

                            final Timer timer = new Timer();

                            timer.scheduleAtFixedRate(new RemindTask(), 0, 2000);

                            dealsSwitcher.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    try {

                                    for (ImageView imageView : dots) {
                                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));
                                    }

                                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));

                                    page = position;
                                    }catch (Exception ignored){}
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                    if (state == ViewPager.SCROLL_STATE_DRAGGING)
                                        scrolling = true;
                                    else
                                        scrolling = false;
                                }
                            });
                            dealsSwitcher.setAdapter( new ImageSwitcherAdapter(getActivity() , products));
                        }
                    }
                }
        );
            final RecyclerView offers = root.findViewById(R.id.offers);
        final RecyclerView newProducts = root.findViewById(R.id.new_products);
        final RecyclerView virus = root.findViewById(R.id.virus);
        final RecyclerView sweetAndSour = root.findViewById(R.id.sour_and_pour);
        final RecyclerView diet = root.findViewById(R.id.diet);
        final RecyclerView sweets = root.findViewById(R.id.sweets);
        final RecyclerView detergents = root.findViewById(R.id.detergents);
        final RecyclerView bakery = root.findViewById(R.id.bakery);
        final RecyclerView fruitsAndVegetables = root.findViewById(R.id.fruits_and_vegetables);
        final RecyclerView beauty = root.findViewById(R.id.beauty);
        final RecyclerView groceries = root.findViewById(R.id.groceries);
        final RecyclerView dairy = root.findViewById(R.id.dairy);
        final RecyclerView frozen = root.findViewById(R.id.frozen);
        final RecyclerView meatAndPoultry = root.findViewById(R.id.meat_and_poultry);
        final RecyclerView spices = root.findViewById(R.id.spices);
        final RecyclerView party = root.findViewById(R.id.party);
        final RecyclerView school = root.findViewById(R.id.school);
        final RecyclerView pets = root.findViewById(R.id.pets);
        final RecyclerView batteries = root.findViewById(R.id.batteries);

        offers.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        newProducts.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        virus.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        sweetAndSour.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        diet.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        sweets.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        detergents.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        bakery.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        fruitsAndVegetables.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        beauty.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        groceries.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        dairy.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        frozen.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        meatAndPoultry.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        spices.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        party.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        school.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        pets.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        batteries.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));


        homeViewModel.getOffers().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                offers.setAdapter(new ProductsAdapter(products , (Home) getActivity(), "offer" , 1));
            }
        });
        homeViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(final ArrayList<Product> products) {
                newProducts.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "new" , 1));
            }
        });

        homeViewModel.getVirus().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                virus.setAdapter(new ProductsAdapter(products , (Home) getActivity(), "virus" ,1 ));
            }
        });
        homeViewModel.getSourAndPour().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                sweetAndSour.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "sweetAndSour" , 1));
            }
        });
        homeViewModel.getDiet().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                diet.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "diet" ,1));
            }
        });
        homeViewModel.getSweet().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                sweets.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "sweets" , 1));
            }
        });
        homeViewModel.getDetergent().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                detergents.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "detergent" ,1 ));
            }
        });
        homeViewModel.getBakery().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                bakery.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "bakery" , 1));
            }
        });
        homeViewModel.getFruitsAndVegetables().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                fruitsAndVegetables.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "fruitAndVegetables" , 1));
            }
        });
        homeViewModel.getBeauty().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                beauty.setAdapter(new ProductsAdapter(products , (Home) getActivity(), "beauty" ,1));
            }
        });
        homeViewModel.getGroceries().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                groceries.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "groceries" , 1));
            }
        });
        homeViewModel.getDairy().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                dairy.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "dairy" , 1));
            }
        });
        homeViewModel.getFrozen().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                frozen.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "frozen" , 1));
            }
        });
        homeViewModel.getMeatAndPoultry().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                meatAndPoultry.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "meatAndPoultry" , 1));
            }
        });

        homeViewModel.getSpices().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                spices.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "spices" , 1));
            }
        });
        homeViewModel.getParty().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                party.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "party" , 1));
            }
        });
        homeViewModel.getSchool().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                school.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "school" , 1));
            }
        });
        homeViewModel.getPets().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                pets.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "pets" , 1));
            }
        });
        homeViewModel.getBatteries().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                batteries.setAdapter(new ProductsAdapter(products , (Home) getActivity() , "battery" , 1));
            }
        });

        FloatingActionButton searchFab = root.findViewById(R.id.search_fab);

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , Products.class);
                intent.putExtra("category" , "search");
                startActivity(intent);
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });
        return root;
    }

    class RemindTask extends TimerTask {
        @Override
        public void run(){
            try {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (!scrolling) {
                            try {
                                if (page > dotsCount) {
                                    dealsSwitcher.setCurrentItem(0, true);
                                } else {
                                    dealsSwitcher.setCurrentItem(page++, true);
                                }
                            }catch (Exception ignored){}
                        }

                    }
                });
            }catch (Exception ignored){}
        }
    }

}
