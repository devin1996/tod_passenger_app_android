package com.example.ptms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ptms.Model.BusTimeDisplay;
import com.example.ptms.Prevelent.Prevelent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddToTravelPlansActivity extends AppCompatActivity implements
        View.OnClickListener {


    private EditText txtDate;
    private int mYear, mMonth, mDay;

    private Button addToTravelPlanBtn,btnDatePicker;
    private ElegantNumberButton numberSeatsButton;
    private TextView fromCity, toCity, arrivalTime, departureTime, trackNo;
    private String timeSlotKey = "", state="Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_travel_plans);

        timeSlotKey = getIntent().getStringExtra("timeSlotKey");

        addToTravelPlanBtn=(Button)findViewById(R.id.pd_add_to_travel_plans);
        btnDatePicker=(Button)findViewById(R.id.btn_date);

        txtDate=(EditText)findViewById(R.id.in_date);
        fromCity=(TextView)findViewById(R.id.from_add_to_travel_plans);
        toCity=(TextView)findViewById(R.id.to_add_to_travel_plans);
        arrivalTime=(TextView)findViewById(R.id.arr_add_to_travel_plans);
        departureTime=(TextView)findViewById(R.id.dep_add_to_travel_plans);
        trackNo=(TextView)findViewById(R.id.track_no_add_to_travel_plans);
        numberSeatsButton=(ElegantNumberButton)findViewById(R.id.no_seats_btn);

        getTimeSlotDetails(timeSlotKey);

        btnDatePicker.setOnClickListener(this);
        addToTravelPlanBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {
// Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v== addToTravelPlanBtn){
//
            addingToTravelPlans();
//
//            if (state.equals("Reserved") || state.equals("Not Reserved"))
//            {
//                Toast.makeText(AddToTravelPlansActivity.this, "You Add more Travel Plans, once you have completed previous tasks", Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                addingToTravelPlans();
//            }
//
        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        CheckOrderState();
//    }


    private void addingToTravelPlans() {

        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate= currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime= currentTime.format(calForDate.getTime());

        final DatabaseReference bookingListRef = FirebaseDatabase.getInstance().getReference().child("bookingList");

        final HashMap<String, Object> travelPlanMap = new HashMap<>();
        travelPlanMap.put("timeSlotKey", timeSlotKey);
        travelPlanMap.put("from", fromCity.getText().toString());
        travelPlanMap.put("to", toCity.getText().toString());
        travelPlanMap.put("arrTime", arrivalTime.getText().toString());
        travelPlanMap.put("depTime", departureTime.getText().toString());
        travelPlanMap.put("trackNo", trackNo.getText().toString());
        travelPlanMap.put("date", saveCurrentDate);
        travelPlanMap.put("time", saveCurrentTime);
        travelPlanMap.put("numberOfSeats", numberSeatsButton.getNumber());
        travelPlanMap.put("BookedDate", txtDate.getText().toString());

        bookingListRef.child("passengerBookingView").child(Prevelent.currentOnlineUser.getPhone())
                .child("busBooking").child(timeSlotKey)
                .updateChildren(travelPlanMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            bookingListRef.child("admin_booking_view")
                                    .child(Prevelent.currentOnlineUser.getPhone())
                                    .child("bus_booking").child(timeSlotKey)
                                    .updateChildren(travelPlanMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(AddToTravelPlansActivity.this, "Added to the Travel Plan List", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(AddToTravelPlansActivity.this, PasMenuActivity.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });
                        }

                    }
                });

    }
    private void getTimeSlotDetails(String timeSlotKey) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("timeSlots").child("busTimes").child("busTimeDislpay");

        productsRef.child(timeSlotKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    BusTimeDisplay products = dataSnapshot.getValue(BusTimeDisplay.class);

                    fromCity.setText(products.getFrom().toUpperCase());
                    toCity.setText(products.getTo().toUpperCase());
                    arrivalTime.setText(products.getArrTime());
                    departureTime.setText(products.getDepTime());
                    trackNo.setText(products.getTrackNo());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

//    private void CheckOrderState()
//    {
//        DatabaseReference orderRef;
//        orderRef = FirebaseDatabase.getInstance().getReference()
//                .child("bookings")
//                .child(Prevelent.currentOnlineUser.getPhone());
//
//        orderRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists())
//                {
//                    String shippingState = dataSnapshot.child("state").getValue().toString();
//                    //String userName = dataSnapshot.child("name").getValue().toString();
//                    if (shippingState.equals("Reserved"))
//                    {
//
//                        state = "Reserved";
//
//                    }
//                    else if (shippingState.equals("not shipped"))
//                    {
//                        state = "Not Reserved";
//
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


}