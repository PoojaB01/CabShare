package com.example.not_decided;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Date;

public class Message_reply extends AppCompatDialogFragment {

    private EditText message;
    private TextView email;
    String receiver;
    Message message1;
    String name;
    private Message_replyListener listener;


    public Message_reply(String receiver, Message message) {
        this.message1 = message;
        this.receiver = receiver;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.message_reply,null);
        builder.setView(view)
                .setTitle("Message")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Message = message.getText().toString().trim();
                        Message NewMessage;
                        final Date date;
                        date = new Date();
                        if(receiver.equals(message1.getReceiver()))
                        {
                            NewMessage = new Message(message1.getSender(),message1.getReceiver(), Message, date.toString(), message1.getSenderName(), message1.getReceiverName());
                        }
                        else
                        {
                            NewMessage = new Message(message1.getReceiver(),message1.getSender(), Message, date.toString(), message1.getReceiverName(), message1.getSenderName());
                        }
                        listener.applyTextsReply(NewMessage);
                    }
                });

        email = view.findViewById(R.id.email);
        message = view.findViewById(R.id.message);
        if(receiver.equals(message1.getReceiver())) {
            email.setText("to : " + message1.getReceiverName());
        }
        else email.setText("to : " + message1.getSenderName());
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener =  (Message_replyListener) context;
    }

    public interface Message_replyListener {
        void applyTextsReply(Message message);
    }
}
