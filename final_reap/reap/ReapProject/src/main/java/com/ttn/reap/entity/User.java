package com.ttn.reap.entity;

import com.ttn.reap.encryption.PasswordHelper;
import com.ttn.reap.enums.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;

    @Transient
    private String name;
    private long availPoints;
    private long redeemedPoints;
    private String password;
    private String token;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isAdmin = false;
    private boolean isActive = true;
    private String fileName;

    public User() {
    }

    public User(String email, String firstName, String lastName, long availPoints, long redeemedPoints, String password, String token, Role role, boolean isAdmin, boolean isActive, String fileName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.availPoints = availPoints;
        this.redeemedPoints = redeemedPoints;
        this.password = PasswordHelper.encrypt(password);
        this.token = token;
        this.role = role;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
        this.fileName = fileName;
    }


    public String getName() {
        this.setName(this.getFirstName().substring(0, 1).toUpperCase() + this.getFirstName().substring(1) + " " +
                this.getLastName().substring(0, 1).toUpperCase() + this.getLastName().substring(1));
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAvailPoints() {
        return availPoints;
    }

    public void setAvailPoints(long availPoints) {
        this.availPoints = availPoints;
    }

    public long getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(long redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordHelper.encrypt(password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", availPoints=" + availPoints +
                ", redeemedPoints=" + redeemedPoints +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                ", isAdmin=" + isAdmin +
                ", isActive=" + isActive +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
