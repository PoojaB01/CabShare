package com.example.not_decided;
import java.util.Date;

public class Message {
    String sender, receiver, message;
    String date;
    public Message() {

    }
    public Message(String sender_id, String receiver_id, String message_text, String date)
    {
        this.sender = sender_id;
        this.receiver= receiver_id;
        this.message = message_text;
        this.date = date;
    }
    public String getSender(){
        return this.sender;
    }
    public String getReceiver(){
        return this.receiver;
    }
    public String getMessage(){
        return this.message;
    }
    public String get_date(){
        return this.date;
    }
}
