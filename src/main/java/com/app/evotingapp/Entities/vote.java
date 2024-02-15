package com.app.evotingapp.Entities;

public class vote {
    String candidate, election, user;
    public vote() {
        super();
    }
    public vote(String candidate, String election, String user) {
        super();
        this.candidate = candidate;
        this.election = election;
        this.user = user;
    }
    public String getCandidate() {
        return candidate;
    }
    public String getElection() {
        return election;
    }
    public String getUser() {
        return user;
    }
    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
    public void setElection(String election) {
        this.election = election;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
