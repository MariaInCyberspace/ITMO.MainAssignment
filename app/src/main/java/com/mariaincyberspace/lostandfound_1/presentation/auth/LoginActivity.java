package com.mariaincyberspace.lostandfound_1.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.UserRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.User;
import com.mariaincyberspace.lostandfound_1.presentation.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private FirebaseAuth auth;
    private UserRepositoryImpl userRepository;
    private LoginViewModel viewModel;

    private void setFields() {
        viewModel = new LoginViewModel(getApplication());
        nameEditText = findViewById(R.id.editTextInputUserName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        auth = FirebaseAuth.getInstance();
        userRepository = new UserRepositoryImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setFields();
    }



   public void onClickSignUp(View view) {
       if (!TextUtils.isEmpty(emailEditText.getText().toString())
               && !TextUtils.isEmpty(passwordEditText.getText().toString())
               && !TextUtils.isEmpty(nameEditText.getText().toString())) {
           viewModel.signUp(emailEditText.getText().toString(), passwordEditText.getText().toString())
               .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       userRepository.addUser(new User(auth.getUid(), nameEditText.getText().toString() ));
                       Intent i = new Intent(LoginActivity.this, MainActivity.class);
                       startActivity(i);
                   }
               });

       }
       else {
           viewModel.verifyInput(nameEditText, emailEditText, passwordEditText);
       }
   }

   public void onClickSignIn(View view) {
        if (!TextUtils.isEmpty(emailEditText.getText().toString())
                && !TextUtils.isEmpty(passwordEditText.getText().toString())) {

            viewModel.signIn(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
        }
        else {
            viewModel.verifyInput(nameEditText, emailEditText, passwordEditText);
        }
   }

}