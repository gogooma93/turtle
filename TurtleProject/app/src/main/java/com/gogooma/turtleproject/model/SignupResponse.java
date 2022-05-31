package com.gogooma.turtleproject.model;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {

    @SerializedName("user")
    private NewUser newUser;

    public NewUser getNewUser() {

        return newUser;
    }

    public void setNewUser(NewUser newUser) {

        this.newUser = newUser;
    }

    public class NewUser {

        @SerializedName("id")
        private int userid;

        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }


        public int getUserId() {
            return userid;
        }

        public void setUserId(int userid) {
            this.userid = userid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

//    @SerializedName("access")
//    private  String access;
//
//    public String getAccess() {
//        return access;
//    }
//
//    public void setAccess(String access) {
//        this.access = access;
//    }
}
