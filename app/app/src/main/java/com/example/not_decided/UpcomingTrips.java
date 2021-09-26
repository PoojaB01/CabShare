package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingTrips extends AppCompatActivity implements Message_reply.Message_replyListener {
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
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                    triplist.add(trip);
                }
//                    Collections.sort(triplist, new Comparator<Trip_information>() {
//                        @Override
//                        public int compare(Trip_information o1, Trip_information o2) {
//                            String s1=o1.getDate();
//                            String s2=o2.getDate();
//                            String a1,a2,b1,b2,c1,c2;
//                            a1=s1.substring(6,10);
//                            b1=s1.substring(3,5);
//                            c1=s1.substring(0,2);
//                            a2=s2.substring(6,10);
//                            b2=s2.substring(3,5);
//                            c2=s2.substring(0,2);
//                            String s3=a1+b1+c1;
//                            String s4=a2+b2+c2;
//                            int x1=Integer.parseInt(s3);
//                            int y1=Integer.parseInt(s4);
//
//                            if(x1>y1)
//                            {
//                                return 0;
//                            }
//                            else
//                            {
//                                return 1;
//                            }
//
//                        }
//                    });
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
                        dialog = new Message_reply(trip.getName(), new Message(UID, trip.getUID(), "", "", name, trip.getName()));
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
