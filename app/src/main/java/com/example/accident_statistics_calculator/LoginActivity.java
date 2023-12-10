package com.example.accident_statistics_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accident_statistics_calculator.databinding.ActivityLoginBinding;
import com.example.accident_statistics_calculator.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView tv1_quotes;
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    CheckBox remember_me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        remember_me = findViewById(R.id.remember_me);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if (checkbox.equals("true")){

            Intent intent = new Intent(LoginActivity.this, AdviceActivity.class);
            startActivity(intent);

        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please Login.", Toast.LENGTH_SHORT).show();
        }


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.emailAddress.getText().toString().trim();
                String password=binding.password.getText().toString().trim();
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,AdviceActivity.class));


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailAddress.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                progressDialog.show();

                if (!validateEmail() || !validatePassword()) {
                    progressDialog.cancel();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, AdviceActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });






        remember_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                } else if (!compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();

                }

            }
        });








        binding.goToSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });






        binding.resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailAddress.getText().toString();
                progressDialog.setTitle("Sending mail");
                progressDialog.show();

                if (!validateEmail()) {
                    progressDialog.cancel();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private boolean validatePassword() {
        String password = binding.password.getText().toString().trim();

        if (password.isEmpty()) {
            binding.password.setError("Password cannot be empty");
            return false;
        } else if (password.length() < 6) {
            binding.password.setError("Password must be at least 6 characters");
            return false;
        } else {
            binding.password.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = binding.emailAddress.getText().toString().trim();

        if (email.isEmpty()) {
            binding.emailAddress.setError("Email cannot be empty");
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailAddress.setError("Invalid email address");
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            binding.emailAddress.setError(null);
            return true;
        }
    }
    @Override
    public void onResume(){
        super.onResume();

        EditText emailAddress = (EditText) findViewById(R.id.emailAddress);
        emailAddress.setText("");
        EditText password = (EditText) findViewById(R.id.password);
        password.setText("");

    }

}