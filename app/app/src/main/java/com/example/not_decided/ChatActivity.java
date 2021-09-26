package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

public class ChatActivity extends AppCompatActivity implements Message_Dialog.Message_DialogListener, Message_reply.Message_replyListener {

    DatabaseReference databaseReference;
    ArrayList<Message> messageList = new ArrayList<>();


    String sender, receiver;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button add_messgae = (Button) findViewById(R.id.button_message);
        user = FirebaseAuth.getInstance().getCurrentUser();
        sender = user.getUid();
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
                messageList.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if (message.getSender().equals(sender) || message.getReceiver().equals(sender))
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
        final Date date;

        date = new Date();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserID");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(email.replace('.', '-')).exists()) {
                    Toast.makeText(ChatActivity.this, "Invalid Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String receiver = dataSnapshot.child(email.replace('.', '-')).getValue().toString();
                writeMessage(sender, receiver, message, date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void writeMessage(final String sender, final String receiver, final String message, final Date date) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String senderName = dataSnapshot.child(sender).child("name").getValue().toString();
                String receiverName = dataSnapshot.child(receiver).child("name").getValue().toString();
                Message M = new Message(sender, receiver, message, date.toString(), senderName, receiverName);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
                String key = ref.push().getKey();
                ref.child(key).setValue(M);
                Toast.makeText(ChatActivity.this, "Message Sent",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private MessageAdapter mAdapter;

    public void makeView(final ArrayList<Message> messageList) {
        RecyclerView mRecyclerView;

        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById(R.id.chats);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        MessageAdapter.BtnClickListener listener = new MessageAdapter.BtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                Message message = messageList.get(position);
                Message_reply dialog;
                if (sender.matches(message.getSender())) {
                    dialog = new Message_reply(message.getReceiver(), message);
                } else {
                    dialog = new Message_reply(message.getSender(), message);
                }
                dialog.show(getSupportFragmentManager(), "message");
            }
        };
        mAdapter = new MessageAdapter(messageList, listener);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void applyTextsReply(Message message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
        String key = ref.push().getKey();
        ref.child(key).setValue(message);
        Toast.makeText(ChatActivity.this, "Message Sent",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chatsearchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.chat_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }
}
