package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password1")
    private String password1;

    @SerializedName("password2")
    private String password2;

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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public SignupRequest(String username, String email, String password1, String password2) {
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;

    }
}

