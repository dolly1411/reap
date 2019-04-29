package com.ttn.reap.enums;

public enum Role {
    USER("USER"), SUPERVISOR("SUPERVISOR"), PRACTICE_HEAD("PRACTICE_HEAD");
    String value;

    public String getValue() {
        return value;
    }

    Role() {
    }

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Role{" +
                "value='" + value + '\'' +
                '}';
    }
}
