package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    TextView t1, t2, t3, t4;
    FirebaseUser user;
    DatabaseReference ref;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        t1 = (TextView) findViewById(R.id.textView6);
        t2 = (TextView) findViewById(R.id.textView7);
        t3 = (TextView) findViewById(R.id.textView8);
        t4 = (TextView) findViewById(R.id.textView9);
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1 = dataSnapshot.child("name").getValue().toString();
                String hostel1 = dataSnapshot.child("hostel").getValue().toString();
                String phone1 = dataSnapshot.child("phone").getValue().toString();
                String department1 = dataSnapshot.child("department").getValue().toString();
                t1.setText(name1);
                t2.setText(phone1);
                t3.setText(department1);
                t4.setText(hostel1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

