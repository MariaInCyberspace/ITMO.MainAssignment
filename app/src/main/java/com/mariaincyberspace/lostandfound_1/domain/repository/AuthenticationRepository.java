package com.mariaincyberspace.lostandfound_1.domain.repository;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationRepository {
    Task<AuthResult> signUp(String email, String password);
    Task<AuthResult> signIn(String email, String password);
    void signOut();
    FirebaseUser getCurrentUser();
}
