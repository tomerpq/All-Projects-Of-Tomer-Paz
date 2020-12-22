package com.example.spotparking.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.R;
import com.example.spotparking.Utils.VerifyInputs;
import com.example.spotparking.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth mAuth;
    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initHomeWatcher();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initHomeWatcher() {
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                startService(new Intent(ForgotPasswordActivity.this, FloatingWidgetShowService.class));
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initButtons();
    }

    private void initButtons() {
        binding.resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailActv.getText().toString().trim();
                if (!VerifyInputs.validateEmail(email)) {
                    binding.emailActv.setError("Please enter a valid email address");
                    return;
                }
                binding.emailActv.setError(null);
                resetPassword(email);
            }
        });
        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //sent email with reset password to the user
                            Toast.makeText(ForgotPasswordActivity.this, "Reset password email sent!\n " +
                                            "Going back to login",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getLocalizedMessage();
                Toast.makeText(ForgotPasswordActivity.this, "Reset password error: " + error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goBackToLogin() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHomeWatcher.stopWatch();
    }
}
