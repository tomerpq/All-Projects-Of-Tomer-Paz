package com.example.spotparking.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.Model.User;
import com.example.spotparking.R;
import com.example.spotparking.Utils.VerifyInputs;
import com.example.spotparking.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        initButtons();
        initHomeWatcher();
    }

    private void initHomeWatcher(){
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                startService(new Intent(RegisterActivity.this, FloatingWidgetShowService.class));
                finish();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }


    private void initButtons() {
        binding.RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });
    }

    /**
     * go back to the login activity.
     */
    private void goBackToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    private void validateInputs(String email, String pass1, String pass2, String displayName){
        if (displayName.isEmpty()) {
            binding.displayNameEt.setError("Please enter your display name");
            return;
        }
        binding.displayNameEt.setError(null);
        if (!VerifyInputs.validateEmail(email)) {
            binding.emailActv.setError("Please enter a valid email address");
            return;
        }
        binding.emailActv.setError(null);
        if (!VerifyInputs.validatePassword(pass1)) {
            binding.passwordEt.setError("Please enter at least 6 characters");
            return;
        }
        binding.passwordEt.setError(null);
        if (!pass1.equals(pass2)) {
            binding.verifyPasswordEt.setError("Passwords are not matched!");
            return;
        }
        binding.verifyPasswordEt.setError(null);
    }

    private void register() {
        final String email = binding.emailActv.getText().toString().trim();
        final String pass1 = binding.passwordEt.getText().toString().trim();
        final String pass2 = binding.verifyPasswordEt.getText().toString().trim();
        final String displayName = binding.displayNameEt.getText().toString().trim();

        validateInputs(email, pass1, pass2, displayName);

        /*
            create the user in firebase, set the values in the local db and go to the car registration
         */
        mAuth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Sign up successful!",
                                    Toast.LENGTH_SHORT).show();
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(getString(R.string.our_users_ref));

                            String fbid = usersRef.push().getKey();
                            String uid = FirebaseAuth.getInstance().getUid();
                            //usersRef.child(uid).setValue(new User(uid, fbid, displayName));
                            usersRef.child(uid).setValue(new User(uid, fbid));

                            LocalDB.getInstance().setEmail(email);
                            LocalDB.getInstance().setPassword(pass1);
                            saveDisplayName(displayName);
                            // start car registration
                            Intent intent = new Intent(RegisterActivity.this, CarRegistrationActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, "Sign up error: " + error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDisplayName(String displayName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName).build();
        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Register", "User updated");
            }
        });
    }

    /**
     * start alert dialog before going back to login.
     */
    private void startAlertDialogLeaving() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("Are you sure you want to leave current progress? Any changes will be lost.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goBackToLogin();
                    }
                }).setNegativeButton("No", null).setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * on back pressed, start dialog before going back to login
     */
    @Override
    public void onBackPressed() {
        startAlertDialogLeaving();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHomeWatcher.stopWatch();
    }
}
