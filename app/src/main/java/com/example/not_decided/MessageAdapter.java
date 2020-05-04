package com.example.not_decided;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<Message> messagelist;
    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView message, path;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text);
            path = itemView.findViewById(R.id.sender_reciever);

        }
    }

    public MessageAdapter(ArrayList<Message> messageList) {
        messagelist = messageList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        MessageAdapter.MessageViewHolder mvh = new MessageAdapter.MessageViewHolder(v);
        return mvh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message message = messagelist.get(position);
        holder.message.setText(message.getMessage());
        holder.path.setText(message.getSender()+" -> "+message.getReceiver());

    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }
}
