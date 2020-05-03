package com.example.not_decided;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class UpcomingTrips extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_trips);
        ArrayList<Trip_information> triplist = new ArrayList<>();
        triplist.add(new Trip_information("destination","source","6:00","May 6","Pooja","pooja@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("destination","source","6:00","May 6","Yoooo","pooja@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("destination","source","6:00","May 6","Pooja","Hey@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("Airport","source","6:00","May 6","Pooja","pooja@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("destination","source","6:00","May 6","Pooja","pooja@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("destination","source","6:00","May 6","Yoooo","pooja@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("destination","source","6:00","May 6","Pooja","Hey@gmail","0000000000","wbdj"));
        triplist.add(new Trip_information("Airport","source","6:00","May 6","Pooja","pooja@gmail","0000000000","wbdj"));

        mRecyclerView = findViewById(R.id.new_trips);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TripAdapter(triplist);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
