package com.example.ptms;

import android.animation.ArgbEvaluator;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.ptms.Model.Promotion;
import com.example.ptms.Prevelent.Prevelent;
import com.example.ptms.ViewHolder.PromotionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class PasMenuActivity extends AppCompatActivity {


    private DatabaseReference PasDatabaseRef;
    String PasId;
    ClipData.Item Bot;

    private AppBarConfiguration mAppBarConfiguration;
    private Boolean currentLogOutPasStatus = false;

    private ImageView myTravelPlansBtn, timeSchedulesBtn, addNewTravelPlanBtn, onBoardBtn, nearByBtn, routesbtn;

    ViewPager viewPager;
    PromotionAdapter adapter;
    List<Promotion> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pas_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String PassengerId = intent.getStringExtra("PasId");


        FloatingActionButton fab = findViewById(R.id.fab123);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view , "Replace with your own action" , Snackbar.LENGTH_LONG)
                        .setAction("Action" , null).show();
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , ChatBotActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        myTravelPlansBtn = (ImageView) findViewById(R.id.travel_plans_btn);
        routesbtn = (ImageView) findViewById(R.id.routes_btn);
        onBoardBtn = (ImageView) findViewById(R.id.on_board_btn);
        timeSchedulesBtn = (ImageView) findViewById(R.id.time_slot_btn);
        nearByBtn = (ImageView) findViewById(R.id.near_by_btn);
        addNewTravelPlanBtn = (ImageView) findViewById(R.id.add_new_travel_plan_btn);

        myTravelPlansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , DuoTravelPlans.class);
                startActivity(myTravelPlansIntents);
            }
        });

        routesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , DuoRoutesActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        timeSchedulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , DuoTimeSlotActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        onBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , OnBoardActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        nearByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , PassengerMapsActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        addNewTravelPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTravelPlansIntents = new Intent(PasMenuActivity.this , DuoTimeSlotActivity.class);
                startActivity(myTravelPlansIntents);
            }
        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home , R.id.nav_gallery , R.id.nav_slideshow ,
                R.id.nav_tools , R.id.nav_share , R.id.nav_send , R.id.near_by , R.id.promo, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this , R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this , navController , mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView , navController);


        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Write a message to the database
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference myRef = database.getReference("message");
                //myRef.setValue("Just Checking"+PassengerId);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        //Toast.makeText(getApplicationContext() , "nav_slideshow is Selected" , Toast.LENGTH_LONG).show();
                        Intent travelPlanIntent = new Intent(PasMenuActivity.this , DuoTravelPlans.class);
                        startActivity(travelPlanIntent);
                        break;
                    case R.id.nav_gallery:
                        Intent timeScheduleIntent = new Intent(PasMenuActivity.this , DuoRoutesActivity.class);
                        startActivity(timeScheduleIntent);
                        break;
                    case R.id.nav_slideshow:
                        Intent timeSlotIntent = new Intent(PasMenuActivity.this , DuoTimeSlotActivity.class);
                        startActivity(timeSlotIntent);
                        break;
                    case R.id.nav_tools:
                        Intent settingsIntent = new Intent(PasMenuActivity.this , OnBoardActivity.class);
                        startActivity(settingsIntent);
                        break;

                    case R.id.near_by:
                        Intent nearByIntent = new Intent(PasMenuActivity.this , PassengerMapsActivity.class);
                        startActivity(nearByIntent);
                        break;
                    case R.id.promo:
                        Intent promotionIntent = new Intent(PasMenuActivity.this , PromotionsActivity.class);
                        startActivity(promotionIntent);
                        break;

                    case R.id.nav_share:
                        Intent scanQrIntent = new Intent(PasMenuActivity.this , PassSettingsActivity.class);
                        startActivity(scanQrIntent);
                        break;

                    case R.id.nav_about:
                        Intent aboutIntent = new Intent(PasMenuActivity.this , AboutActivity.class);
                        startActivity(aboutIntent);
                        break;

                    case R.id.nav_send:
                        currentLogOutPasStatus = true;
                        Paper.book().destroy();

                        Intent logoutIntent = new Intent(PasMenuActivity.this , PasMainActivity.class);
                        //logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });

        View headerview = navigationView.getHeaderView(0);
        TextView userNameTextView = headerview.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerview.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevelent.currentOnlineUser.getName());
        Picasso.get().load(Prevelent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);


        models = new ArrayList<>();
        models.add(new Promotion(R.drawable.socialdistance , "" , ""));
        models.add(new Promotion(R.drawable.ptmsmsg , "Visit DownSouth" , ""));
        models.add(new Promotion(R.drawable.d86077acf94c2f4d0d8be5dec12e47f4 , "Capital" , ""));
        models.add(new Promotion(R.drawable.map , "Ancient Kingdom" , ""));

        adapter = new PromotionAdapter(models , this);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130 , 0 , 130 , 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.yelllow) ,
                getResources().getColor(R.color.greeen) ,
                getResources().getColor(R.color.blueee) ,
                getResources().getColor(R.color.whiteyellow)
        };
        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position , float positionOffset , int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset ,
                                    colors[position] ,
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pas_menu , menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this , R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController , mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
