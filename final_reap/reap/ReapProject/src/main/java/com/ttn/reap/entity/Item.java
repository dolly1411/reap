package com.ttn.reap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private int itemValue;
    private String imageSource;

    public Item(String itemName, int itemValue, String imageSource) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.imageSource = imageSource;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemValue=" + itemValue +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getItemValue() == item.getItemValue() &&
                Objects.equals(getId(), item.getId()) &&
                Objects.equals(getItemName(), item.getItemName()) &&
                Objects.equals(getImageSource(), item.getImageSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemName(), getItemValue(), getImageSource());
    }
}
