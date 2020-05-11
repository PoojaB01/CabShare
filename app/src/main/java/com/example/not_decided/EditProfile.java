package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.content.ContentResolver;
import android.content.Intent;
<<<<<<< HEAD
=======
import android.graphics.Bitmap;
>>>>>>> 00d45d7a66cd3b4cab006ebf9365da29b4ffc352
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
<<<<<<< HEAD

import java.io.File;
=======
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
>>>>>>> 00d45d7a66cd3b4cab006ebf9365da29b4ffc352

public class EditProfile extends AppCompatActivity {
    DatabaseReference ref;
    FirebaseUser user;
    String name1,hostel1,phone1,department1,email;
    String uid;
    String name2,hostel2,department2,phone2;

    EditText t1,t2,t3,t4;
    ImageView profileImageView;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


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
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
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
                if(imagePath!=null)
                    sendUserData(uid);

                Toast.makeText(EditProfile.this, "Your changes are saved",
                        Toast.LENGTH_SHORT).show();
            }
        });

<<<<<<< HEAD

    }


=======
        profileImageView = findViewById(R.id.picture);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });
    }

    private void sendUserData(String uid) {
        // Get "User UID" from Firebase > Authentification > Users.

        // DatabaseReference databaseReference = firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));
        StorageReference imageReference = storageReference.child(uid).child("Images").child("ProfilePicture.jpg"); //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Error: Uploading profile picture", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfile.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

>>>>>>> 00d45d7a66cd3b4cab006ebf9365da29b4ffc352
}
