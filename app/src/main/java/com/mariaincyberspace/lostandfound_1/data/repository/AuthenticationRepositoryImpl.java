package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mariaincyberspace.lostandfound_1.MyApp;
import com.mariaincyberspace.lostandfound_1.domain.repository.AuthenticationRepository;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private FirebaseAuth auth;



    public AuthenticationRepositoryImpl() {
        this.application = MyApp.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }


    @Override
    public Task<AuthResult> signUp(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(application,
                        Literals.Toasts.USER_SIGNED_UP, Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(application,
                Literals.Toasts.USER_NOT_SIGNED_UP, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public Task<AuthResult> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(application,
                                Literals.Toasts.USER_LOGGED_IN, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(application,
                                Literals.Toasts.USER_NOT_LOGGED_IN, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void signOut() {
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }


    @Override
    public FirebaseUser getCurrentUser() {
        return getCurrentFirebaseUser();
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return auth.getCurrentUser();
    }

    public String getCurrentUserId() {
        return getCurrentUser().getUid();
    }
}
