package com.ttn.reap.entity;

import com.ttn.reap.enums.Badge;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BadgeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private Date date;
    private String reason;
    @Enumerated(EnumType.STRING)
    private Badge badge;

    public BadgeTransaction(User sender, User receiver, Date date, String reason, Badge badge) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.reason = reason;
        this.badge = badge;
    }

    public BadgeTransaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {
        return "BadgeTransaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                ", badge=" + badge +
                '}';
    }
}
