package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class EditProfile extends AppCompatActivity {
    DatabaseReference ref;
    FirebaseUser user;
    String name1,hostel1,phone1,department1,email;
    String uid;
    String name2,hostel2,department2,phone2;

    EditText t1,t2,t3,t4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button b1=(Button)findViewById(R.id.button9);
        user= FirebaseAuth.getInstance().getCurrentUser();
        t1=(EditText)findViewById(R.id.editText4);
        t2=(EditText)findViewById(R.id.editText5);
        t3=(EditText)findViewById(R.id.editText6);
        t4=(EditText)findViewById(R.id.editText7);
        uid=user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 name1 = dataSnapshot.child("name").getValue().toString();
                 hostel1 = dataSnapshot.child("hostel").getValue().toString();
                 phone1 = dataSnapshot.child("phone").getValue().toString();
                 department1 = dataSnapshot.child("department").getValue().toString();
                 email = dataSnapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, HomeActivity.class));
            }
        });
        Button b2=(Button)findViewById(R.id.button10);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name2=t1.getText().toString();
                phone2=t2.getText().toString();
                department2=t3.getText().toString();
                hostel2=t4.getText().toString();
                if(name2.matches(""))
                {
                    name2=name1;
                }
                if(phone2.matches(""))
                {
                   phone2=phone1;
                }
                if(department2.matches(""))
                {
                    department2=department1;
                }
                if(hostel2.matches(""))
                {
                    hostel2=hostel1;
                }
                User_information user1=new User_information(name2,phone2,hostel2,department2,email);

                ref.setValue(user1);

                Toast.makeText(EditProfile.this, "Your changes are saved",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


}
