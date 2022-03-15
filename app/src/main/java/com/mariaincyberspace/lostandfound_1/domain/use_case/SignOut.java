package com.mariaincyberspace.lostandfound_1.domain.use_case;

import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.presentation.auth.LoginViewModel;

public class SignOut {
    private AuthenticationRepositoryImpl repository;
    public SignOut(AuthenticationRepositoryImpl repository) {
        this.repository = repository;
    }

    public void signOut() {
        repository.signOut();
    }
}
