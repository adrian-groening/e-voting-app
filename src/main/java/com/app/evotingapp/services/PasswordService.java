package com.app.evotingapp.services;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {
    public PasswordService() {
        super();
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
