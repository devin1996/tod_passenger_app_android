package com.example.ptms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DuoTimeSlotActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duo_time_slot);

        fragmentManager = getSupportFragmentManager();
        fragment = new BusTimeSlotFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container , fragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                fragment = new BusTimeSlotFragment();
                                break;

                            case R.id.action_video:
                                fragment = new TrainTimeSlotFragment();

                                break;

                            case R.id.action_music:
                                Intent timeScheduleIntent = new Intent(DuoTimeSlotActivity.this , PasMenuActivity.class);
                                startActivity(timeScheduleIntent);
//                                fragment = new TrainRouteFragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container , fragment).commit();
                        return true;
                    }
                });
    }
}