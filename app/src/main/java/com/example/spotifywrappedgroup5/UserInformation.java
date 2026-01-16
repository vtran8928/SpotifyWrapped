package com.example.spotifywrappedgroup5;

public class UserInformation {
    String uid, name, email, accessCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public UserInformation(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public UserInformation() {
    }
}
