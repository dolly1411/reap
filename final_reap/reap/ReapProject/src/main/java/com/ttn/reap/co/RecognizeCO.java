package com.ttn.reap.co;

public class RecognizeCO {
    private String sender_email;
    private String badge_val;
    private String message_val;
    private String receiver_email;
    
    public String getSender_email() {
        return sender_email;
    }
    
    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }
    
    public String getBadge_val() {
        return badge_val;
    }
    
    public void setBadge_val(String badge_val) {
        this.badge_val = badge_val;
    }
    
    public String getMessage_val() {
        return message_val;
    }
    
    public void setMessage_val(String message_val) {
        this.message_val = message_val;
    }
    
    public String getReceiver_email() {
        return receiver_email;
    }
    
    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }
    
    @Override
    public String toString() {
        return "RecognizeCO{" +
                "sender_email='" + sender_email + '\'' +
                ", badge_val='" + badge_val + '\'' +
                ", message_val='" + message_val + '\'' +
                ", receiver_email='" + receiver_email + '\'' +
                '}';
    }
}
