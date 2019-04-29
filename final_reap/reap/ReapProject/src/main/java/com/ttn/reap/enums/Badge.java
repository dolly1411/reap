package com.ttn.reap.enums;

public enum Badge {
    GOLD(30), SILVER(20), BRONZE(10);
    int value;

    Badge() {
    }

    Badge(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name();
    }

}

