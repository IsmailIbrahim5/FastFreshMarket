package com.idea.fastfreshmarket.UI.fragments;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idea.fastfreshmarket.Adapters.PlacesAutoCompleteAdapter;
import com.idea.fastfreshmarket.Models.DatabaseProduct;
import com.idea.fastfreshmarket.Models.Order;
import com.idea.fastfreshmarket.Models.Product;
import com.idea.fastfreshmarket.Models.Rating;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.activities.Home;
import com.idea.fastfreshmarket.UI.activities.OrderDone;
import com.idea.fastfreshmarket.UI.alertDialogs.GPS;
import com.idea.fastfreshmarket.UI.alertDialogs.Loading;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelectingLocation extends FragmentActivity  implements
        OnMapReadyCallback , PlacesAutoCompleteAdapter.ClickListener{

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    boolean locationPermissionGranted;
    Location lastKnownLocation;
    TextView locationName;
    BottomSheetBehavior<View> bottomSheetBehavior;
    Geocoder geocoder;
    AutoCompleteTextView searchBar;
    BitmapDescriptor icon;
    Address address;
    float totalPrice;
    int coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        icon = BitmapDescriptorFactory.fromResource(R.drawable.location_pin);
        try {
            totalPrice = getIntent().getFloatExtra("totalPrice" , 0);
            coupon = getIntent().getIntExtra("coupon" , 0);
        }catch (Exception e){}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        FloatingActionButton zoomIn = findViewById(R.id.zoom_in);
        FloatingActionButton zoomOut = findViewById(R.id.zoom_out);
        FloatingActionButton myLocation = findViewById(R.id.my_location);
        locationName = findViewById(R.id.location_name);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                else
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocationUI();
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lastKnownLocation = new Location(LocationManager.GPS_PROVIDER);
                lastKnownLocation.setLatitude(latLng.latitude);
                lastKnownLocation.setLongitude(latLng.longitude);
                moveCamera();
            }
        });
        geocoder = new Geocoder(this, getResources().getConfiguration().locale);



        final PlacesAutoCompleteAdapter placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        placesAutoCompleteAdapter.setClickListener(this);
        searchBar = findViewById(R.id.auto_complete_text_view);
        searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchBar.setAdapter(placesAutoCompleteAdapter);
        placesAutoCompleteAdapter.notifyDataSetChanged();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                placesAutoCompleteAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try { ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); } catch (Exception ignored) { }
                return true;
            }
        });

        updateLocationUI();

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Loading loading = new Loading(SelectingLocation.this);
                loading.start();

                try {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders").push();
                    for (int i = 0; i < Home.orders.size(); i++) {
                        Product p = Home.orders.get(i);
                        DatabaseProduct databaseProduct = new DatabaseProduct();
                        databaseProduct.setProductName(p.getProductName());
                        databaseProduct.setQty(p.getQty());
                        databaseProduct.setProductPrice(p.getProductPrice());
                        databaseProduct.setProductOffer(p.getProductOffer());
                        databaseReference.child("OrderProducts").push().setValue(databaseProduct);
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    final String time = formatter.format(date);
                    final Order order = new Order(Home.user.getId(), time, new com.idea.fastfreshmarket.Models.Location(address.getAddressLine(0), address.getLongitude(), address.getLatitude()), totalPrice - (coupon / 10), databaseReference.getKey(), coupon, new Rating(0,0,0," "));
                    databaseReference.child("Info").setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ArrayList<String> ordersId = new ArrayList<>();
                            try {
                                FileInputStream fis = openFileInput("orders");
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                ordersId = (ArrayList<String>) ois.readObject();
                                ois.close();
                                fis.close();

                            } catch (Exception ignored) {
                            }
                            try {
                                FileOutputStream fos = openFileOutput("orders", Context.MODE_PRIVATE);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                ordersId.add(order.getOrderId());
                                oos.writeObject(ordersId);
                                oos.close();
                                fos.close();
                            } catch (Exception ignored) {}
                            loading.dismiss();
                            Intent intent = new Intent(SelectingLocation.this, OrderDone.class);
                            intent.putExtra("orderId" , order.getOrderId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                            finish();
                        }
                    });
                    final DatabaseReference pointsRef = FirebaseDatabase.getInstance().getReference("users").child(Home.user.getId()).child("points");
                    final int points = (int) (totalPrice / 10) - coupon;
                    Home.user.setPoints(Home.user.getPoints() + points);
                    pointsRef.setValue(Home.user.getPoints());
                }catch (Exception e){
                    Toast.makeText(SelectingLocation.this, "Something went wrong, Please try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            updateLocationUI();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (locationPermissionGranted)
            getDeviceLocation();
        else
            getLocationPermission();
    }

    public boolean statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GPS gps= new GPS(this);
            gps.start();
            return false;
        }
        return true;
    }

    private void getDeviceLocation() {
        if(statusCheck()) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            try {
                if (locationPermissionGranted) {
                    Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                    locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                lastKnownLocation = task.getResult();
                                if (lastKnownLocation != null) {
                                    moveCamera();
                                    mMap.setMyLocationEnabled(true);
                                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                }
                            }
                        }
                    });
                }
            } catch (SecurityException e) {
            }
        }
    }

    public void moveCamera() {
        LatLng location = new LatLng(lastKnownLocation.getLatitude(),
                lastKnownLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 20));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).icon(icon));



        try {

            List<Address> myList = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);

            address = myList.get(0);
            locationName.setText(address.getAddressLine(0).split(",")[0]);

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } catch (Exception ignored) {
        }
    }

    public void moveCamera(Place place) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 20));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(icon));
        locationName.setText(place.getName());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        address = new Address(Locale.getDefault());
        address.setAddressLine(0 , place.getAddress());
        address.setLatitude(place.getLatLng().latitude);
        address.setLongitude(place.getLatLng().longitude);
    }


    @Override
    public void click(Place place) {
        try { ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); } catch (Exception ignored) { }
        searchBar.setText(place.getName());
        searchBar.clearFocus();
        moveCamera(place);
    }
}