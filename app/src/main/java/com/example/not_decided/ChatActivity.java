package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

public class ChatActivity extends AppCompatActivity implements Message_Dialog.Message_DialogListener {

    String sender, receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button add_messgae = (Button) findViewById(R.id.button_message);

        add_messgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

        });

    }

    public void openDialog() {
        Message_Dialog dialog = new Message_Dialog();
        dialog.show(getSupportFragmentManager(), "message");
    }

    @Override
    public void applyTexts(String email, String message) {
        FirebaseUser user;
        Date date;
        user = FirebaseAuth.getInstance().getCurrentUser();
        sender = user.getUid();
        date = new Date();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(sender);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sender = dataSnapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Message M = new Message(sender, email, message, date.toString());

        ref = FirebaseDatabase.getInstance().getReference().child("Chats").child(sender + " & " + email);

        String key = ref.push().getKey();

        ref.child(key).setValue(M);

        Toast.makeText(ChatActivity.this, "Message Sent",
                Toast.LENGTH_SHORT).show();

    }
}
