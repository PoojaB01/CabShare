package com.example.not_decided;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements Filterable {

    private ArrayList<Message> messagelist;
    private ArrayList<Message> messagesListCopy;
    private BtnClickListener mListener;


    public interface BtnClickListener {
        void onBtnClick(int position);
    }




    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView message, path, date;
        public Button reply;
        public BtnClickListener mClickListener;
        public MessageViewHolder(@NonNull View itemView, BtnClickListener listener) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text);
            path = itemView.findViewById(R.id.sender_reciever);
            reply = (Button) itemView.findViewById(R.id.button_reply);
            date = itemView.findViewById(R.id.message_date);
            mClickListener = listener;

        }
    }

    public MessageAdapter(ArrayList<Message> messageList, BtnClickListener listener) {
        messagelist = messageList;
        mListener = listener;
        messagesListCopy = new ArrayList<>(messageList);
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        MessageAdapter.MessageViewHolder mvh = new MessageAdapter.MessageViewHolder(v,mListener);
        return mvh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.MessageViewHolder holder, final int position) {
        Message message = messagelist.get(position);
        holder.message.setText(message.getMessage());
        holder.path.setText(message.getSenderName()+" -> "+message.getReceiverName());
        holder.date.setText(message.get_date());
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mClickListener != null)
                    holder.mClickListener.onBtnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    @Override
    public Filter getFilter() {
        return MessageFilter;
    }

    private Filter MessageFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Message> FilteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                FilteredList.addAll(messagesListCopy);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for( Message message : messagesListCopy)
                {
                    if(message.getReceiverName().toLowerCase().contains(filterPattern))
                        FilteredList.add(message);
                    else if(message.getSenderName().toLowerCase().contains(filterPattern))
                        FilteredList.add(message);
                    else if(message.getMessage().toLowerCase().contains(filterPattern))
                        FilteredList.add(message);
                }
            }
            FilterResults results = new FilterResults();
            results.values = FilteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            messagelist.clear();
            messagelist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
