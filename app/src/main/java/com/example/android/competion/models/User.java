package com.example.android.competion.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String name ;
    @SerializedName("email")
    private String email ;
    @SerializedName("password")
    private String password ;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String name) {this.name = name;}
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

