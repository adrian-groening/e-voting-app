package com.app.evotingapp.Entities;

public class candidate {
    String name, bio, party;
    public candidate() {
        super();
    }
    public candidate(String name, String bio, String party) {
        super();
        this.name = name;
        this.bio = bio;
        this.party = party;
    }
    public String getName() {
        return name;
    }
    public String getBio() {
        return bio;
    }
    public String getParty() {
        return party;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setParty(String party) {
        this.party = party;
    }
}
