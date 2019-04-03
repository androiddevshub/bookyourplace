package com.app.bookyourplace.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.R;
import com.app.bookyourplace.View.Activities.HotelActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HotelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Hotel> hotelArrayList;
    Context context;

    public HotelListAdapter(ArrayList<Hotel> hotelArrayList, Context context) {
        this.hotelArrayList = hotelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HotelListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hotel, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Hotel hotel = hotelArrayList.get(position);

        HotelListHolder hotelListHolder = (HotelListHolder) holder;

        try {

            Picasso.with(context)
                    .load(hotel.getHotelImagesH().getImg3()).fit().centerCrop()
                    .error(R.drawable.hotel)
                    .into(hotelListHolder.hotelImage);

           /* Picasso.with(context).load(videoList.get(position).getProfile_pic())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(holder.user_profile_pic);*/

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(context)
                    .load(R.drawable.hotel).fit().centerCrop()
                    .into(hotelListHolder.hotelImage);
        }

        Log.e("HotelAdapter", "Url: "+ hotel.getHotelImagesH().getImg1() );

        hotelListHolder.tvHotelPrice.setText(hotel.getHotelPrice());
        hotelListHolder.tvHotelDiscountRate.setText(hotel.getHotelDiscount()+ "% OFF");
        hotelListHolder.tvHotelName.setText(hotel.getHotelName());
        hotelListHolder.tvHotelLocation.setText(hotel.getHotelLocation());
        hotelListHolder.tvHotelRating.setText(hotel.getHotelRating());

        hotelListHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HotelActivity.class);
                intent.putExtra("hotelData",hotel);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return hotelArrayList.size();
    }

    class HotelListHolder extends RecyclerView.ViewHolder{

        View layout;
        ImageView hotelImage;
        TextView tvHotelPrice;
        TextView tvHotelDiscountRate;
        TextView tvHotelName;
        TextView tvHotelLocation;
        TextView tvHotelRating;


        public HotelListHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            hotelImage = itemView.findViewById(R.id.imgHotelL);
            tvHotelPrice = itemView.findViewById(R.id.tvHotelAPrice);
            tvHotelDiscountRate = itemView.findViewById(R.id.tvHotelDisRate);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvHotelLocation = itemView.findViewById(R.id.tvHotelLocation);
            tvHotelRating = itemView.findViewById(R.id.tvHotelRating);

        }
    }

}
