package com.app.bookyourplace.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.R;
import com.app.bookyourplace.View.Activities.BookingActivity;
import com.app.bookyourplace.View.Activities.HotelActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BookingDetails> bookingDetailsArrayList;
    Context context;

    public BookingListAdapter(ArrayList<BookingDetails> bookingDetailsArrayList, Context context) {
        this.bookingDetailsArrayList = bookingDetailsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BookingDetails bookingDetails = bookingDetailsArrayList.get(position);
        BookingListHolder bookingListHolder = (BookingListHolder) holder;

        try {

            Picasso.with(context)
                    .load(bookingDetails.getBookingHotelPicture()).fit().centerCrop()
                    .error(R.drawable.dummybg)
                    .into(bookingListHolder.smallbookingHotelImage);

        } catch (Exception e){

            e.printStackTrace();
            Picasso.with(context)
                    .load(R.drawable.dummybg).fit().centerCrop()
                    .into(bookingListHolder.smallbookingHotelImage);
        }

        bookingListHolder.smallbookingId.setText(bookingDetails.getBookingId());
        bookingListHolder.smallbookingUserName.setText(bookingDetails.getBookingUsername());
        bookingListHolder.smallbookingCheckIndate.setText(bookingDetails.getBookingStartdate());
        bookingListHolder.smallbookingNights.setText(bookingDetails.getBookingNights());
        bookingListHolder.smallbookingCheckOutDate.setText(bookingDetails.getBookingEnddate());
        bookingListHolder.smallbookingHotelName.setText(bookingDetails.getBookingHotelName());
        bookingListHolder.smallbookingHotelLocation.setText(bookingDetails.getBookingHotelLoc());

        bookingListHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("bookingDetailsMain",bookingDetails);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingDetailsArrayList.size();
    }

    class BookingListHolder extends RecyclerView.ViewHolder{

        View layout;
        ImageView smallbookingHotelImage;
        TextView smallbookingId;
        TextView smallbookingUserName;
        TextView smallbookingCheckIndate;
        TextView smallbookingNights;
        TextView smallbookingCheckOutDate;
        TextView smallbookingHotelName;
        TextView smallbookingHotelLocation;

        public BookingListHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            smallbookingHotelImage = itemView.findViewById(R.id.bookingHotelPicture);
            smallbookingId = itemView.findViewById(R.id.smallbookingId);
            smallbookingUserName = itemView.findViewById(R.id.smallbookingUserName);
            smallbookingCheckIndate = itemView.findViewById(R.id.smallbookingCheckIndate);
            smallbookingNights = itemView.findViewById(R.id.smallbookingNights);
            smallbookingCheckOutDate = itemView.findViewById(R.id.smallbookingCheckOutDate);
            smallbookingHotelName = itemView.findViewById(R.id.smallbookingHotelName);
            smallbookingHotelLocation = itemView.findViewById(R.id.smallbookingHotelLocation);

        }
    }

}
