package com.example.not_decided;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Parcelable {
    String message, sender, receiver, senderName, receiverName;
    String _date;

    public Message() {

    }

    public Message(String sender_id, String receiver_id, String message_text, String date, String senderName, String receiverName) {
        this.sender = sender_id;
        this.receiver = receiver_id;
        this.message = message_text;
        this._date = date;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    protected Message(Parcel in) {
        message = in.readString();
        sender = in.readString();
        receiver = in.readString();
        senderName = in.readString();
        receiverName = in.readString();
        _date = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getMessage() {
        return this.message;
    }

    public String get_date() {
        String date_time = "";
        Date date;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(this._date);
            date_time = date.getHours() + ":" + date.getMinutes() + " " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
        } catch (Exception e) {
        }
        return date_time;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(sender);
        dest.writeString(receiver);
        dest.writeString(senderName);
        dest.writeString(receiverName);
        dest.writeString(_date);
    }
}
