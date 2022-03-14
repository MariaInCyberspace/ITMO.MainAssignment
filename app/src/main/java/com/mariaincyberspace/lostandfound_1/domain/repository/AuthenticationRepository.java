package com.mariaincyberspace.lostandfound_1.domain.repository;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AuthenticationRepository {
    public Task<AuthResult> signUp(String email, String password);
    public Task<AuthResult> signIn(String email, String password);
    public void signOut();
}
