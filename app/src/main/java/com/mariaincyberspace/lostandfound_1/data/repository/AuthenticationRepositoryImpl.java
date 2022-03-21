package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.mariaincyberspace.lostandfound_1.domain.repository.AuthenticationRepository;

import com.mariaincyberspace.lostandfound_1.utils.Literals;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private FirebaseAuth auth;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public AuthenticationRepositoryImpl(Application application) {
        this.application = application;
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
                        //todo link with storage if needed
//                        addToDatabase();
                //todo where to start another activity?
//                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(i);
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

    public void checkIfRegistered(String email) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(application, "There's no user with this e-mail", Toast.LENGTH_LONG).show();
                Log.d("AuthLog: ", "no such user");
            }
        });
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
