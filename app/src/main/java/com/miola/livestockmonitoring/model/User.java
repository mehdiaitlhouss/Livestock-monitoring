package com.miola.livestockmonitoring.model;

import androidx.annotation.Keep;

import java.util.HashMap;
import java.util.Map;

@Keep
public class User
{
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Map<String, Cow> cows = new HashMap<>();
    private Map<String, Sheep> sheeps = new HashMap<>();
    private Map<String, Chicken> chickens = new HashMap<>();

    public User(String name, String username, String email, String phoneNumber, String password, Map<String, Cow> cows, Map<String, Sheep> sheeps, Map<String, Chicken> chickens) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.cows = cows;
        this.sheeps = sheeps;
        this.chickens = chickens;
    }

    public User(String name, String username, String email, String phoneNumber, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Cow> getCows() {
        return cows;
    }

    public void setCows(Map<String, Cow> cows) {
        this.cows = cows;
    }

    public Map<String, Sheep> getSheeps() {
        return sheeps;
    }

    public void setSheeps(Map<String, Sheep> sheeps) {
        this.sheeps = sheeps;
    }

    public Map<String, Chicken> getChickens() {
        return chickens;
    }

    public void setChickens(Map<String, Chicken> chickens) {
        this.chickens = chickens;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", cows=" + cows +
                ", sheeps=" + sheeps +
                ", chickens=" + chickens +
                '}';
    }
}
