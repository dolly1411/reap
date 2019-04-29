package com.ttn.reap.dto;

import com.ttn.reap.entity.User;

public class BadgeTransactionDto {
    User receiver;
    long countno;

    public BadgeTransactionDto(User receiver, long countno) {
        this.receiver = receiver;
        this.countno = countno;
    }


    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public long getCountno() {
        return countno;
    }

    public void setCountno(long countno) {
        this.countno = countno;
    }

    @Override
    public String toString() {
        return "BadgeTransactionDto{" +
                "receiver=" + receiver +
                ", countno=" + countno +
                '}';
    }
}
