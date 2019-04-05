package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookyourplace.Model.Place;
import com.app.bookyourplace.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Place place;
    private ImageView placeImgMain;
    private TextView placeName;
    private TextView placeLocation;
    private TextView placeDescription;
    private ImageView place_location_icon;
    private GoogleMap mMapPlace;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        getIntent().setExtrasClassLoader(Place.class.getClassLoader());
        place = getIntent().getParcelableExtra("placeData");

        placeImgMain = findViewById(R.id.placeImageMain);
        placeName = findViewById(R.id.aPlaceName);
        placeLocation = findViewById(R.id.aPlaceLocation);
        placeDescription = findViewById(R.id.aPlaceDescription);
        place_location_icon = findViewById(R.id.place_location_icon);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_place);
        mapFragment.getMapAsync(this);

        try {

            Picasso.with(this)
                    .load(place.getPlaceImagesP().getImg1()).fit().centerCrop()
                    .error(R.drawable.place)
                    .into(placeImgMain);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(this)
                    .load(R.drawable.hotel).fit().centerCrop()
                    .into(placeImgMain);
        }

        placeName.setText(place.getPlaceName());
        placeLocation.setText(place.getPlaceLocation());
        placeDescription.setText(place.getPlaceDescription());

        place_location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:" + place.getPlaceLat() + "," + place.getPlaceLong());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapPlace = googleMap;
        Place mPlace = place;

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(mPlace.getPlaceName(), 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                LatLng place = new LatLng(latitude, longitude);
                mMapPlace.addMarker(new MarkerOptions().position(place).title(mPlace.getPlaceName()));
                mMapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17f));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera
//        LatLng place = new LatLng(Double.parseDouble(mPlace.getPlaceLat()), Double.parseDouble(mPlace.getPlaceLong()));
//        mMapPlace.addMarker(new MarkerOptions().position(place));
//        mMapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17f));
    }
}
