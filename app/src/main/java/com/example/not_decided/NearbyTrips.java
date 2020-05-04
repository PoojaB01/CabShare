package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.not_decided.R.layout.list_layout;

public class NearbyTrips extends AppCompatActivity {
private EditText destination;
private Button search;
private RecyclerView result;
private DatabaseReference mTripDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_trips);
        destination=(EditText)findViewById(R.id.destination);
        search=(Button) findViewById(R.id.search);
        result=(RecyclerView)findViewById(R.id.recycleriew);
        result.setHasFixedSize(true);
        result.setLayoutManager(new LinearLayoutManager(this));
        mTripDatabase= FirebaseDatabase.getInstance().getReference("Trips");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText=destination.getText().toString();
                firebaseTripSearch(searchText);
            }
        });
    }
    private void firebaseTripSearch(String searchText){
        Toast.makeText(NearbyTrips.this,"Started",Toast.LENGTH_LONG).show();
        Query firebaseSearchQuery=mTripDatabase.orderByChild("destination").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<Trip_information, TripsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Trip_information, TripsViewHolder>(

                Trip_information.class,
                R.layout.list_layout,
                TripsViewHolder.class,
                mTripDatabase

        ) {
            @Override
            protected void populateViewHolder(TripsViewHolder tripsViewHolder, Trip_information trip_information, int i) {
                    tripsViewHolder.setDetails(getApplicationContext(),trip_information.getName(),trip_information.getPhone(),trip_information.getEmail());
            }
        };
        result.setAdapter(firebaseRecyclerAdapter);
    }

    public  static class TripsViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
        }

        public void setDetails(Context ctx, String name1, String phone1, String email1) {
            TextView name2=(TextView)mView.findViewById(R.id.name);
            TextView phone2=(TextView)mView.findViewById(R.id.phone);
            TextView email2=(TextView)mView.findViewById(R.id.email);
            name2.setText(name1);
            phone2.setText(phone1);
            email2.setText(email1);

        }
    }

}
