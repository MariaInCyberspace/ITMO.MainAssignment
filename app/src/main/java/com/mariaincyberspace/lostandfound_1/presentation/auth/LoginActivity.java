package com.mariaincyberspace.lostandfound_1.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.domain.model.User;
import com.mariaincyberspace.lostandfound_1.presentation.MainActivity;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new LoginViewModel(getApplication());
        reference = FirebaseDatabase.getInstance().getReference().child(Literals.USER_KEY);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        auth = FirebaseAuth.getInstance();
    }


   public void onClickSignUp(View view) {
       if (!TextUtils.isEmpty(emailEditText.getText().toString())
               && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
           viewModel.signUp(emailEditText.getText().toString(), passwordEditText.getText().toString())
               .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       addToDatabase();
                       Intent i = new Intent(LoginActivity.this, MainActivity.class);
                       startActivity(i);
                   }
               });

       }
       else {
           viewModel.verifyInput(emailEditText, passwordEditText);
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
            viewModel.verifyInput(emailEditText, passwordEditText);
        }
   }

   // todo : add users/items repository
   private void addToDatabase() {
        firebaseUser = auth.getCurrentUser();
        User user = new User(emailEditText.getText().toString(), passwordEditText.getText().toString());
        reference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, Literals.TOAST_VALUES_STORED,
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(LoginActivity.this, Literals.TOAST_VALUES_NOT_STORED,
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}