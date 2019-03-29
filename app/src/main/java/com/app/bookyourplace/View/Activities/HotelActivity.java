package com.app.bookyourplace.View.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.bookyourplace.R;
import com.takisoft.datetimepicker.DatePickerDialog;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Calendar cal = Calendar.getInstance();

        findViewById(R.id.imgHotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(HotelActivity.this, (view1, year, month, dayOfMonth) -> {
                    Toast.makeText(HotelActivity.this, String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("EEEE", dayOfMonth), Toast.LENGTH_SHORT).show();
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
                dpd.show();
            }
        });
    }
}
