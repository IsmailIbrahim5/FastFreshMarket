package com.idea.fastfreshmarket.Adapters;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idea.fastfreshmarket.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChoiceViewHolder> {
    String[] categories;
    LayoutInflater inflater;
    Context context;
    int spanCount;
    int selected;
    View.OnClickListener onClickListener;
    Resources r;
    public ChooseAdapter(Context context, int spanCount , View.OnClickListener onClickListener , int selected, String category) {
        this.context = context;
        this.spanCount = spanCount;
        this.onClickListener = onClickListener;
        this.selected = selected;
        inflater = LayoutInflater.from(context);
        r = context.getResources();
        switch (category){
            case "offers" : categories = new String[]{r.getString(R.string.all_offers)
                    , r.getString(R.string.news)
                    , r.getString(R.string.virus)
                    , r.getString(R.string.sweetAndSour)
                    , r.getString(R.string.sweets)
                    , r.getString(R.string.diet)
                    , r.getString(R.string.detergent)
                    , r.getString(R.string.bakery)
                    , r.getString(R.string.fruitsAndVegetables)
                    , r.getString(R.string.beauty)
                    , r.getString(R.string.groceries)
                    , r.getString(R.string.dairy)
                    , r.getString(R.string.frozen)
                    , r.getString(R.string.meatAndPoultry)
                    , r.getString(R.string.spices)
                    , r.getString(R.string.party)
                    , r.getString(R.string.school)
                    , r.getString(R.string.pets)
                    , r.getString(R.string.batteries)}; break;
            case "sweets" : categories = new String[]{r.getString(R.string.all)
                    ,r.getString(R.string.chips)
                    , r.getString(R.string.chocolates)
                    , r.getString(R.string.biscuits)
                    , r.getString(R.string.ice_cream)
                    , r.getString(R.string.juices)
                    , r.getString(R.string.coke)
                    , r.getString(R.string.fresh_water)}; break;

            case "detergent" : categories = new String[]{r.getString(R.string.all)
                    , r.getString(R.string.food_wrappers)
                    , r.getString(R.string.insecticides)
                    , r.getString(R.string.washing_powders)
                    , r.getString(R.string.air_fresheners)
                    , r.getString(R.string.toilet_paper)
                    , r.getString(R.string.cleaning_tools)
                    , r.getString(R.string.kitchen_utensils)}; break;

            case "fruitsAndVegetables" : categories = new String[]{r.getString(R.string.all)
                    , r.getString(R.string.fresh_fruits)
                    , r.getString(R.string.fresh_vegetables)
                    , r.getString(R.string.cooked_vegetables)}; break;

            case "beauty" : categories = new String[]{r.getString(R.string.all)
                    , r.getString(R.string.skin_and_body_care)
                    , r.getString(R.string.hair_care)
                    , r.getString(R.string.women_stuff)
                    , r.getString(R.string.men_care)
                    , r.getString(R.string.oral_and_dental_care)
                    , r.getString(R.string.shoes_care)}; break;
            case "groceries" : categories = new String[]{r.getString(R.string.all)
                    , r.getString(R.string.tea_and_coffee)
                    , r.getString(R.string.sauces)
                    , r.getString(R.string.oils)
                    , r.getString(R.string.magarine)
                    , r.getString(R.string.canned_food)
                    , r.getString(R.string.honey_and_dates)
                    , r.getString(R.string.rice_and_pasta)}; break;
            case "meatAndPoultry" : categories = new String[]{r.getString(R.string.all)
                    , r.getString(R.string.fresh_meat)
                    , r.getString(R.string.fresh_poultry)}; break;
        }
        if(category.equals("offers")) {
            }
    }

    @NonNull
    @Override
    public ChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choose_product_item, parent, false);
        if (spanCount > 1) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }
        return new ChoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoiceViewHolder holder, int position) {
        String category = categories[position];
        if(category.equals(r.getString(R.string.all_offers)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_offers));
        else if(category.equals(r.getString(R.string.news)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_new));
        else if(category.equals(r.getString(R.string.virus)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_mask));
        else if(category.equals(r.getString(R.string.sweetAndSour)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_sweet_and_sour));
        else if(category.equals(r.getString(R.string.sweets)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_sweets));
        else if(category.equals(r.getString(R.string.diet)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_diet));
        else if(category.equals(r.getString(R.string.detergent)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_detergent));
        else if(category.equals(r.getString(R.string.bakery)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_bread));
        else if(category.equals(r.getString(R.string.fruitsAndVegetables)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_fruits_and_vegetables));
        else if(category.equals(r.getString(R.string.beauty)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_beauty));
        else if(category.equals(r.getString(R.string.groceries)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_groceries));
        else if(category.equals(r.getString(R.string.dairy)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_dairy));
        else if(category.equals(r.getString(R.string.frozen)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_frozen_food));
        else if(category.equals(r.getString(R.string.meatAndPoultry)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_meat_and_poultry));
        else if(category.equals(r.getString(R.string.spices)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_spices));
        else if(category.equals(r.getString(R.string.party)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_party));
        else if(category.equals(r.getString(R.string.school)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_school_supplies));
        else if(category.equals(r.getString(R.string.pets)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_pets_food));
        else if(category.equals(r.getString(R.string.batteries)))
            holder.categoryImage.setImageDrawable(context.getDrawable(R.drawable.ic_battery));

        if(selected == position) {
            holder.categoryImage.setImageTintList(ColorStateList.valueOf(r.getColor(R.color.orange)));
            holder.categoryName.setTextColor(r.getColor(R.color.orange));
        }
        else{
            holder.categoryImage.setImageTintList(ColorStateList.valueOf(r.getColor(R.color.green)));
            holder.categoryName.setTextColor(r.getColor(R.color.green));
        }
        holder.categoryName.setText(category);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }


    class ChoiceViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public ChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(onClickListener);
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
