package com.example.not_decided;
import java.util.Date;

public class Message {
    String sender_id, receiver_id, message_text;
    String date;
    public Message() {

    }
    public Message(String sender_id, String receiver_id, String message_text, String date)
    {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_text = message_text;
        this.date = date;
    }
    public String getSender(){
        return this.sender_id;
    }
    public String getReceiver(){
        return this.receiver_id;
    }
    public String getMessage(){
        return this.message_text;
    }
    public String get_date(){
        return this.date;
    }
}
