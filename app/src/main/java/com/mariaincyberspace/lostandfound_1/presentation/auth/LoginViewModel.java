package com.mariaincyberspace.lostandfound_1.presentation.auth;

import android.app.Application;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import com.google.android.gms.tasks.Task;


public class LoginViewModel extends AndroidViewModel {

    private AuthenticationRepositoryImpl repository;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> loggedStatus;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthenticationRepositoryImpl(application);
        userData = repository.getUserMutableLiveData();
        loggedStatus = repository.getUserLoggedMutableLiveData();
    }

    public Task<AuthResult> signUp(String email, String password) {
       return repository.signUp(email, password);

    }

    public Task<AuthResult> signIn(String email, String password) {
        return repository.signIn(email, password);
    }


    public void verifyInput(EditText name, EditText email, EditText password) {
        String values;
        if (TextUtils.isEmpty(name.getText().toString())
                && TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString())) {
            values = Literals.Toasts.INPUT_NAME_EMAIL_PASSWORD;
        }
        else if (TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString())) {
            values = Literals.Toasts.INPUT_EMAIL_AND_PASSWORD;
        }
        else {
            if (TextUtils.isEmpty(name.getText().toString())) {
                values = Literals.Toasts.INPUT_NAME;
            } else {
                values = TextUtils.isEmpty(email.getText().toString())
                        ? Literals.Toasts.INPUT_EMAIL : Literals.Toasts.INPUT_PASSWORD;
            }

        }

        Toast.makeText(getApplication(),
                Literals.Toasts.INPUT_PROMPT + values, Toast.LENGTH_LONG).show();
    }

}
