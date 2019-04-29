package com.ttn.reap.entity;

import javax.persistence.*;

@Entity
public class BadgeBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.REFRESH, orphanRemoval = true)
    private User userId;
    private int goldCount;
    private int silverCount;
    private int bronzeCount;

    public BadgeBalance(User userId, int goldCount, int silverCount, int bronzeCount) {
        this.userId = userId;
        this.goldCount = goldCount;
        this.silverCount = silverCount;
        this.bronzeCount = bronzeCount;
    }


    public BadgeBalance() {
    }

    @Override
    public String toString() {
        return "BadgeSender{" +
                "id=" + id +
                ", userId=" + userId +
                ", goldCount=" + goldCount +
                ", silverCount=" + silverCount +
                ", bronzeCount=" + bronzeCount +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }

    public int getSilverCount() {
        return silverCount;
    }

    public void setSilverCount(int silverCount) {
        this.silverCount = silverCount;
    }

    public int getBronzeCount() {
        return bronzeCount;
    }

    public void setBronzeCount(int bronzeCount) {
        this.bronzeCount = bronzeCount;
    }
}
