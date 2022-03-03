package com.mariaincyberspace.lostandfound_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reference = FirebaseDatabase.getInstance().getReference().child(Literals.USER_KEY);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        auth = FirebaseAuth.getInstance();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser != null) {
//            initUser();
//        } else {
//            Toast.makeText(this,"User null", Toast.LENGTH_SHORT).show();
//        }
//    }



   public void onClickSignUp(View view) {

       if (!TextUtils.isEmpty(email.getText().toString())
               && !TextUtils.isEmpty(password.getText().toString())) {
           auth.createUserWithEmailAndPassword(email.getText().toString(),
                   password.getText().toString())
                   .addOnCompleteListener(this, task -> {
                       if (task.isSuccessful()) {
                           Toast.makeText(LoginActivity.this,
                                   Literals.TOAST_USER_SIGNED_UP, Toast.LENGTH_LONG).show();
                           addToDatabase();
                           Intent i = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(i);
                       }
                       else {
                           Toast.makeText(LoginActivity.this,
                                   Literals.TOAST_USER_NOT_SIGNED_UP, Toast.LENGTH_LONG).show();
                       }
                   });
       }
       else {
           verifyInput();
       }
   }

   public void onClickSignIn(View view) {

        if (!TextUtils.isEmpty(email.getText().toString())
                && !TextUtils.isEmpty(password.getText().toString())) {
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    Literals.TOAST_USER_LOGGED_IN, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    Literals.TOAST_USER_NOT_LOGGED_IN, Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else {
            verifyInput();
        }
   }

   private void addToDatabase() {
        firebaseUser = auth.getCurrentUser();
        User user = new User(email.getText().toString(), password.getText().toString());
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

    private void verifyInput() {
        String values;
        if (TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString())) {
            values = Literals.TOAST_INPUT_EMAIL_AND_PASSWORD;
        }
        else {
            values = TextUtils.isEmpty(email.getText().toString())
                    ? Literals.TOAST_INPUT_EMAIL : Literals.TOAST_INPUT_PASSWORD;
        }

        Toast.makeText(LoginActivity.this,
                Literals.TOAST_INPUT_PROMPT + values, Toast.LENGTH_LONG).show();
    }

}