package com.app.evotingapp.Entities;

public class user {
    String name, email, hash, id;
    public user() {
        super();
    }
    public user(String name, String email, String id, String hash) {
        super();
        this.name = name;
        this.email = email;
        this.hash = hash;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getHash() {
        return hash;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public void setId(String id) {
        this.id = id;
    }
}
