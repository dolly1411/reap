package com.ttn.reap.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userId;

    @ManyToOne
    private Item itemId;
    private Date purchaseTimestamp;
//    private int purchaseQuantity;

    public PurchaseHistory(User userId, Item itemId, Date purchaseTimestamp) {
        this.userId = userId;
        this.itemId = itemId;
        this.purchaseTimestamp = purchaseTimestamp;
//        this.purchaseQuantity = purchaseQuantity;
    }

    public PurchaseHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Date getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    public void setPurchaseTimestamp(Date purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }


//    public int getPurchaseQuantity() {
//        return purchaseQuantity;
//    }
//
//    public void setPurchaseQuantity(int purchaseQuantity) {
//        this.purchaseQuantity = purchaseQuantity;
//    }

    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", purchaseTimestamp=" + purchaseTimestamp +
//                ", purchaseQuantity=" + purchaseQuantity +
                '}';
    }
}
