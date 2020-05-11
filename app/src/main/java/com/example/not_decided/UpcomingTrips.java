package com.example.not_decided;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingTrips extends AppCompatActivity implements Message_reply.Message_replyListener{
    private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        DatabaseReference databaseReference;
        ArrayList<Trip_information> triplist = new ArrayList<>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upcoming_trips);

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Trips");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot tripSnapshot: dataSnapshot.getChildren()) {
                        Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                        triplist.add(trip);
                    }
                    makeView(triplist);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        public void makeView(final ArrayList<Trip_information> triplist) {
            mRecyclerView = findViewById(R.id.new_trips);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            TripAdapter.BtnClickListener listener = new TripAdapter.BtnClickListener() {
                @Override
                public void onBtnClick(int position) {
                    final Trip_information trip = triplist.get(position);

                    DatabaseReference ref;
                    final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            Message_reply dialog;
                            dialog = new Message_reply(trip.getName(), new Message(UID, trip.getId(), "", "", name, trip.getName()));
                            dialog.show(getSupportFragmentManager(), "message");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            };
            mAdapter = new TripAdapter(triplist, listener);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void applyTextsReply(Message message) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
            String key = ref.push().getKey();
            ref.child(key).setValue(message);
            Toast.makeText(UpcomingTrips.this, "Message Sent",
                    Toast.LENGTH_SHORT).show();
    }
}
