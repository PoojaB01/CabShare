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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements Message_Dialog.Message_DialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference databaseReference;
    ArrayList<Message> messageList = new ArrayList<>();

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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    messageList.add(message);
                }
                makeView(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openDialog() {
        Message_Dialog dialog = new Message_Dialog();
        dialog.show(getSupportFragmentManager(), "message");
    }

    @Override
    public void applyTexts(final String email, final String message) {
        FirebaseUser user;
        final Date date;
        user = FirebaseAuth.getInstance().getCurrentUser();
        sender = user.getUid();
        date = new Date();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(sender);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sender = dataSnapshot.child("email").getValue().toString();
                writeMessage(sender, email, message, date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    public void writeMessage(String sender, String email, String message, Date date) {
        Message M = new Message(sender, email, message, date.toString());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
        String key = ref.push().getKey();
        ref.child(key).setValue(M);
        Toast.makeText(ChatActivity.this, "Message Sent",
                Toast.LENGTH_SHORT).show();
    }

    public void makeView(ArrayList<Message> messageList) {
        mRecyclerView = findViewById(R.id.chats);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessageAdapter(messageList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
