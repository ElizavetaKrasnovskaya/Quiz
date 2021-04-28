package com.bsuir.quiz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.bsuir.quiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        AppCompatButton signUp = findViewById(R.id.signup);

        AppCompatImageButton signIn = findViewById(R.id.signin);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d("Id", user.getUid());
            } else {
                Log.d("Id", "0");
            }
        };


        signUp.setOnClickListener(view -> signUp(email.getText().toString(), password.getText().toString()));

        signIn.setOnClickListener(view -> signIn(email.getText().toString(), password.getText().toString()));
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Authorization completed successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TopicActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authorization failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Registration completed successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TopicActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Registration failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}