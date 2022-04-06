package com.mariaincyberspace.lostandfound_1.domain.use_case;

import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;

public class SignOut {
    private AuthenticationRepositoryImpl repository;
    public SignOut() {
        this.repository = new AuthenticationRepositoryImpl();
    }

    public void signOut() {
        repository.signOut();
    }
}
