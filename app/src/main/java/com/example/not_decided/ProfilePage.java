package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfilePage extends AppCompatActivity {
    TextView t1, t2, t3, t4;
    FirebaseUser user;
    DatabaseReference ref;
    String uid;
    ImageView profileImageView;

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
        profileImageView = findViewById(R.id.profilepicture);
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
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(uid).child("Images").child("ProfilePicture.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(profileImageView);
            }
        });

        Button b1=(Button)findViewById(R.id.button8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this, EditProfile.class));
            }
        });
        Button b2=(Button)findViewById(R.id.button7);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this, HomeActivity.class));
            }
        });





    }


}

