package com.app.bookyourplace.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookyourplace.Model.Place;
import com.app.bookyourplace.R;
import com.app.bookyourplace.View.Activities.PlaceActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Place> placeArrayList;
    Context context;

    public PlaceListAdapter(ArrayList<Place> placeArrayList, Context context) {
        this.placeArrayList = placeArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HotelListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Place place = placeArrayList.get(position);

        HotelListHolder hotelListHolder = (HotelListHolder) holder;

        try {

            Picasso.with(context)
                    .load(place.getPlaceImagesP().getImg1()).fit().centerCrop()
                    .error(R.drawable.place)
                    .into(hotelListHolder.placeImage);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(context)
                    .load(R.drawable.place).fit().centerCrop()
                    .into(hotelListHolder.placeImage);
        }

        hotelListHolder.tvPlaceName.setText(place.getPlaceName());
        hotelListHolder.tvPlaceLocation.setText(place.getPlaceLocation());

        hotelListHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PlaceActivity.class);
                intent.putExtra("placeData",place);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    class HotelListHolder extends RecyclerView.ViewHolder{

        View layout;
        ImageView placeImage;
        TextView tvPlaceName;
        TextView tvPlaceLocation;



        public HotelListHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            placeImage = itemView.findViewById(R.id.imgPlace);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvPlaceLocation = itemView.findViewById(R.id.tvPlaceCity);

        }
    }

}
