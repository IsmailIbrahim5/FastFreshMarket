package com.idea.fastfreshmarket.UI.fragments.home;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Tools.DataBaseGenerator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Product>> mNew= new MutableLiveData<>()
            , mOffers = new MutableLiveData<>()
            , mVirus= new MutableLiveData<>()
            , mSweetAndSour = new MutableLiveData<>()
            , mDiet= new MutableLiveData<>()
            , mSweet= new MutableLiveData<>()
            , mDetergent = new MutableLiveData<>()
            , mBakery= new MutableLiveData<>()
            , mFruitsAndVegetables= new MutableLiveData<>()
            , mBeauty= new MutableLiveData<>()
            , mGroceries= new MutableLiveData<>()
            , mDairy= new MutableLiveData<>()
            , mFrozen= new MutableLiveData<>()
            , mMeatAndPoultry= new MutableLiveData<>()
            , mSpices= new MutableLiveData<>()
            , mParty= new MutableLiveData<>()
            , mSchool= new MutableLiveData<>()
            , mPets= new MutableLiveData<>()
            , mBatteries= new MutableLiveData<>()
            , mSpecials= new MutableLiveData<>();



    ArrayList<Product> products = new ArrayList<>()
            , specials = new ArrayList<>()
            , offers = new ArrayList<>()
            , news = new ArrayList<>();

    public HomeViewModel() {

        for(int i = 0 ; i < 10 ; i++) {
            products.add(new Product());
        }

        mNew.setValue(products);
        mOffers.setValue(products);
        mVirus.setValue(products);
        mSweetAndSour.setValue(products);
        mDiet.setValue(products);
        mSweet.setValue(products);
        mDetergent.setValue(products);
        mBakery.setValue(products);
        mFruitsAndVegetables.setValue(products);
        mBeauty.setValue(products);
        mGroceries.setValue(products);
        mDairy.setValue(products);
        mFrozen.setValue(products);
        mMeatAndPoultry.setValue(products);
        mSpices.setValue(products);
        mParty.setValue(products);
        mSchool.setValue(products);
        mPets.setValue(products);
        mBatteries.setValue(products);
      /*  FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("Images/IMG20200820075341.jpg");
        try {
            temp = new File("/data/data/com.idea.yassenmarket/cache/IMG20200820075341.jpg");
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(!temp.exists()) {
            storageReference.getFile(temp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    image = Uri.fromFile(temp);
                    products.get(0).setProductImage(image);
                    products.get(1).setProductImage(image);
                    products.get(2).setProductImage(image);
                    products.get(3).setProductImage(image);
                    products.get(4).setProductImage(image);
                    products.get(5).setProductImage(image);
                    products.get(6).setProductImage(image);
                    mOffers.postValue(products);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }else {
            image = Uri.fromFile(temp);
        }
        products.add(new Product(image , "idk" , 123 ,  25));
        products.add(new Product(image , "lmao" , 13 ,  22));
        products.add(new Product(image , "lol" , 213 ,  15));
        products.add(new Product(image , "ok" , 156 ,   55));
        products.add(new Product(image , "whatever" , 11523 ,  54));
        products.add(new Product(image , "hm.." , 563 ,  67));
        products.add(new Product(image , "maybe.." , 456 , 87));
        mOffers.setValue(products);
       */

        loadProducts("virus");
        loadProducts("sweetAndSour");
        loadProducts("diet");
        loadProducts("sweets");
        loadProducts("detergents");
        loadProducts("bakery");
        loadProducts("fruitsAndVegetables");
        loadProducts("beauty");
        loadProducts("groceries");
        loadProducts("dairy");
        loadProducts("frozen");
        loadProducts("meatAndPoultry");
        loadProducts("spices");
        loadProducts("party");
        loadProducts("school");
        loadProducts("pets");
        loadProducts("battery");

    }



    public MutableLiveData<ArrayList<Product>> getOffers() {
        return mOffers;
    }
    public MutableLiveData<ArrayList<Product>> getSpecials() {
        return mSpecials;
    }
    public MutableLiveData<ArrayList<Product>> getNews() {
        return mNew;
    }

    public MutableLiveData<ArrayList<Product>> getVirus() {
        return mVirus;
    }

    public MutableLiveData<ArrayList<Product>> getSourAndPour() {
        return mSweetAndSour;
    }

    public MutableLiveData<ArrayList<Product>> getDiet() {
        return mDiet;
    }

    public MutableLiveData<ArrayList<Product>> getSweet() {
        return mSweet;
    }

    public MutableLiveData<ArrayList<Product>> getDetergent() {
        return mDetergent;
    }

    public MutableLiveData<ArrayList<Product>> getBakery() {
        return mBakery;
    }

    public MutableLiveData<ArrayList<Product>> getFruitsAndVegetables() {
        return mFruitsAndVegetables;
    }

    public MutableLiveData<ArrayList<Product>> getBeauty() {
        return mBeauty;
    }

    public MutableLiveData<ArrayList<Product>> getGroceries() {
        return mGroceries;
    }

    public MutableLiveData<ArrayList<Product>> getDairy() {
        return mDairy;
    }

    public MutableLiveData<ArrayList<Product>> getFrozen() {
        return mFrozen;
    }

    public MutableLiveData<ArrayList<Product>> getMeatAndPoultry() {
        return mMeatAndPoultry;
    }

    public MutableLiveData<ArrayList<Product>> getSpices() {
        return mSpices;
    }

    public MutableLiveData<ArrayList<Product>> getParty() {
        return mParty;
    }

    public MutableLiveData<ArrayList<Product>> getSchool() {
        return mSchool;
    }

    public MutableLiveData<ArrayList<Product>> getPets() {
        return mPets;
    }

    public MutableLiveData<ArrayList<Product>> getBatteries() {
        return mBatteries;
    }

    public void loadProducts(final String query){
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        try {
                            Product product = snapshot1.getValue(Product.class);
                            products.add(product);
                            if (product.isProductSpecial()) {
                                specials.add(product);
                                mSpecials.postValue(specials);
                            }
                            if (product.getProductOffer() != 0) {
                                offers.add(product);
                                mOffers.postValue(offers);
                            }
                            if (product.isProductNew()) {
                                news.add(product);
                                mNew.postValue(news);
                            }
                        }catch (Exception e){}
                    }
                    switch (query) {
                        case "virus":
                            mVirus.postValue(products);
                            break;
                        case "sweetAndSour":
                            mSweetAndSour.postValue(products);
                            break;
                        case "diet":
                            mDiet.postValue(products);
                            break;
                        case "sweets":
                            mSweet.postValue(products);
                            break;
                        case "detergents":
                            mDetergent.postValue(products);
                            break;
                        case "bakery":
                            mBakery.postValue(products);
                            break;
                        case "beauty":
                            mBeauty.postValue(products);
                            break;
                        case "groceries":
                            mGroceries.postValue(products);
                            break;
                        case "fruitsAndVegetables":
                            mFruitsAndVegetables.postValue(products);
                            break;
                        case "dairy":
                            mDairy.postValue(products);
                            break;
                        case "frozen":
                            mFrozen.postValue(products);
                            break;
                        case "meatAndPoultry":
                            mMeatAndPoultry.postValue(products);
                            break;
                        case "spices":
                            mSpices.postValue(products);
                            break;
                        case "party":
                            mParty.postValue(products);
                            break;
                        case "school":
                            mSchool.postValue(products);
                            break;
                        case "pets":
                            mPets.postValue(products);
                            break;
                        case "battery":
                            mBatteries.postValue(products);
                            break;
                    }
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {}
        } , query , 10);
    }

    public void loadMore (final String query , int pageCount){
        final ArrayList<Product> products = new ArrayList<>();
        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator();
        dataBaseGenerator.generateProducts(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Product product = snapshot1.getValue(Product.class);
                    products.add(product);
                }
                switch (query){
                    case "virus": mVirus.postValue(products); break;
                    case "sourAndPour": mSweetAndSour.postValue(products); break;
                    case "diet": mDiet.postValue(products); break;
                    case "sweets": mSweet.postValue(products); break;
                    case "detergent": mDetergent.postValue(products); break;
                    case "bakery": mBakery.postValue(products); break;
                    case "beauty": mBeauty.postValue(products); break;
                    case "groceries": mGroceries.postValue(products); break;
                    case "fruitAndVegetables": mFruitsAndVegetables.postValue(products); break;
                    case "dairy": mDairy.postValue(products); break;
                    case "frozen": mFrozen.postValue(products); break;
                    case "meatAndPoultry":mMeatAndPoultry.postValue(products); break;
                    case "spices":mSpices.postValue(products); break;
                    case "party":mParty.postValue(products); break;
                    case "school":mSchool.postValue(products); break;
                    case "pets":mPets.postValue(products); break;
                    case "battery":mBatteries.postValue(products); break;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } , query , 4);
    }
}