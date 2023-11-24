package com.ananas.socialmediaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ananas.socialmediaapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signInClicked(View v) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button signinButton = binding.signButton;
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();
        signinButton.setEnabled(false);
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email ve şifre boş olamaz", Toast.LENGTH_LONG).show();
            signinButton.setEnabled(true);
        } else {

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            signinButton.setEnabled(true);
                        }
                    });
        }
    }

    public void registerClicked(View v) {
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();
    Button registerButton = binding.registerButton;
        registerButton.setEnabled(false);
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email ve şifre boş olamaz", Toast.LENGTH_LONG).show();
            registerButton.setEnabled(true);
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            registerButton.setEnabled(true);
                        }
                    });
        }
    }

}