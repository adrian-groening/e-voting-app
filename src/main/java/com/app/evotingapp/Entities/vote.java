package com.app.evotingapp.Entities;

public class vote {
    candidate candidate;
    user user;
    public vote() {
        super();
    }
    public vote(candidate candidate, user user) {
        super();
        this.candidate = candidate;
        this.user = user;
    }
    public candidate getCandidate() {
        return candidate;
    }
    public user getUser() {
        return user;
    }
    public void setCandidate(candidate candidate) {
        this.candidate = candidate;
    }
    public void setUser(user user) {
        this.user = user;
    }
}
