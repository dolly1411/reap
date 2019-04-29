package com.ttn.reap.dto;

import com.ttn.reap.entity.User;

public class NewerBoardDto {
    private User user;
    private int countGold;
    private int countSilver;
    private int countBronze;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCountGold() {
        return countGold;
    }

    public void setCountGold(int countGold) {
        this.countGold = countGold;
    }

    public int getCountSilver() {
        return countSilver;
    }

    public void setCountSilver(int countSilver) {
        this.countSilver = countSilver;
    }

    public int getCountBronze() {
        return countBronze;
    }

    public void setCountBronze(int countBronze) {
        this.countBronze = countBronze;
    }
}
