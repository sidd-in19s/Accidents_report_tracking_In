package com.example.accident_statistics_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.accident_statistics_calculator.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.fullName.getText().toString();
                String number=binding.phoneNumber.getText().toString();
                String email=binding.emailAddress.getText().toString().trim();
                String password=binding.password.getText().toString();

                progressDialog.show();;
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Send verification email to user
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();

                                                    // Save user data to Firestore
                                                    firebaseFirestore.collection("User")
                                                            .document(FirebaseAuth.getInstance().getUid())
                                                            .set(new UserModel(name,number,email));

                                                    // Redirect to login activity
                                                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                                    progressDialog.cancel();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                                                    progressDialog.cancel();
                                                }
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
            }
        });

        binding.goToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user has verified their email address
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user.isEmailVerified()) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Please verify your email address first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}