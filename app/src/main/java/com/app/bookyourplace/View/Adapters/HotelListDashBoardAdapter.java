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

public class HotelListDashBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Hotel> hotelArrayList;
    Context context;

    public HotelListDashBoardAdapter(ArrayList<Hotel> hotelArrayList, Context context) {
        this.hotelArrayList = hotelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HotelListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_small_hotel, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Hotel hotel = hotelArrayList.get(position);

        HotelListHolder hotelListHolder = (HotelListHolder) holder;

        try {

            Picasso.with(context)
                    .load(hotel.getHotelImagesH().getImg1()).fit().centerCrop()
                    .error(R.drawable.hotel)
                    .into(hotelListHolder.hotelImage);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(context)
                    .load(R.drawable.hotel).fit().centerCrop()
                    .into(hotelListHolder.hotelImage);
        }

        int price = Integer.parseInt(hotel.getHotelPrice());

        float discount = Integer.parseInt(hotel.getHotelDiscount())/100;

        int MRP =  price + Math.round(discount * price);

        Log.e("HotelAdapterPrice", "Price: "+ Math.round(discount * price) + "discount: " + discount);

        hotelListHolder.tvHotelMRP.setText(String.valueOf(MRP));
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
        return 5;
    }

    class HotelListHolder extends RecyclerView.ViewHolder{

        View layout;
        ImageView hotelImage;
        TextView tvHotelMRP;
        TextView tvHotelPrice;
        TextView tvHotelDiscountRate;
        TextView tvHotelName;
        TextView tvHotelLocation;
        TextView tvHotelRating;


        public HotelListHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            hotelImage = itemView.findViewById(R.id.imgHotelS);
            tvHotelMRP = itemView.findViewById(R.id.hotelMRPTv);
            tvHotelPrice = itemView.findViewById(R.id.hotelPriceTv);
            tvHotelDiscountRate = itemView.findViewById(R.id.hotelDiscountTv);
            tvHotelName = itemView.findViewById(R.id.hotelNameTv);
            tvHotelLocation = itemView.findViewById(R.id.hotelLocationTv);
            tvHotelRating = itemView.findViewById(R.id.hotelRatingTv);

        }
    }

}
