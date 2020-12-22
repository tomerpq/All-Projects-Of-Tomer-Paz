package com.example.spotparking.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.Model.User;
import com.example.spotparking.R;
import com.example.spotparking.Utils.SharedPreferencesUtils;
import com.example.spotparking.Utils.VerifyInputs;
import com.example.spotparking.Utils.VerifyPermissions;
import com.example.spotparking.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* the login activity - with facebook, google,
    and deep link sms read of phone number(using google services).
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    HomeWatcher mHomeWatcher;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        quickLogin();
        initHomeWatcher();
    }

    /**
     * handles the quick login if the user saved his credentials in the shared preferences.
     */
    private void quickLogin() {
        mEmail = SharedPreferencesUtils.getEmail(this);
        if (mEmail != null) {
            if (!mEmail.equals("")) {
                Toast.makeText(this, "Logging in, it can take a few seconds.", Toast.LENGTH_LONG).show();
                mPassword = SharedPreferencesUtils.getPassword(this);
                loginWithEmailPassword(true);
            }
        } else {
            setContentView(binding.getRoot());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_sign_in_key))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mCallbackManager = CallbackManager.Factory.create();
        initButtons();

    }


    private void initHomeWatcher() {
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                startService(new Intent(LoginActivity.this, FloatingWidgetShowService.class));
                finish();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }

    private void initButtons() {
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUPActivity();
                finish();
            }
        });
        binding.forgotPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open new activity:
                startForgotPasswordActivity();
                finish();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmailPassword(false);
            }
        });
//        //TODO: for now dont use google and facebook
//        binding.googleLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginWithGoogle();
//            }
//        });
//        binding.facebookLoginBtn.setPermissions("mEmail_actv", "public_profile");
//        binding.facebookLoginBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                loginWithFacebook(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(LoginActivity.this, "Facebook authentication stopped",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(LoginActivity.this, "Facebook authentication failed",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void loginWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login();
                        } else {
                            showFacebookErrorMessage();
                        }
                    }
                });
    }


    private void loginWithGoogle() {
        //login with google:
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {//google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, show error message.
                showGoogleErrorMessage();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login();
                        } else {
                            // If sign in fails, display a message to the user.
                            showGoogleErrorMessage();
                        }
                    }
                });
    }

    private void loginWithEmailPassword(boolean isQuickSignIn) {
        if (!isQuickSignIn) {
            mPassword = binding.passwordEt.getText().toString().trim();
            mEmail = binding.emailActv.getText().toString().trim();
            // validate email and password
            if (!VerifyInputs.validateEmail(mEmail)) {
                binding.emailActv.setError("Please enter a valid email address");
                return;
            }
            binding.emailActv.setError(null);

            if (!VerifyInputs.validatePassword(mPassword)) {
                binding.passwordEt.setError("Please enter a password");
                return;
            }
            binding.passwordEt.setError(null);
            Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (!SharedPreferencesUtils.getIsQuickLogin(getApplicationContext())) {
                                startKeepLoggedInDialog();
                            } else {
                                login();
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, "Login error: " + error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(getString(R.string.our_users_ref));
        final String currentUid = FirebaseAuth.getInstance().getUid();
        //Read it from the database:
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get current userID:
                boolean foundUser = false;
                DataSnapshot currentUserDataSnapshot = null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data == null) continue;
                    String uid = data.getValue(User.class).getId();
                    if (uid == null) {
                        //Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Login", "onDataChange: Error with this data: " + data);
                        continue;
                    }
                    LocalDB.getInstance().addUserId(uid);
                    if (uid.equals(currentUid)) {
                        currentUserDataSnapshot = data;
                        LocalDB.getInstance().setAllDataFromDB(data);
                        if (data.child("timeToEvacuate").getValue(Integer.class) == null) {
                            usersRef.child(uid).child("timeToEvacuate").setValue(0);
                        }
                        foundUser = true;
                    }
                }
                if (!foundUser) {
                    Toast.makeText(LoginActivity.this, "Error: Something Went Wrong",
                            Toast.LENGTH_SHORT).show();
                    Log.d("FAIL", "onDataChange: user not found");
                    finish();
                    return;
                }

                // if the car color/manufacture/model is null, then the details are missing.
                if (currentUserDataSnapshot.getValue(User.class).getCarColor() == null) {
                    startCarRegistrationDialog();
                    return;
                }
                if (currentUserDataSnapshot.getValue(User.class).getCarManufacturer() == null) {
                    startCarRegistrationDialog();
                    return;
                }
                if (currentUserDataSnapshot.getValue(User.class).getCarModel() == null) {
                    startCarRegistrationDialog();
                    return;
                }
                // successfully  loaded all data to local db, start verify permission
                Intent intent = new Intent(LoginActivity.this, VerifyPermissions.class);
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startCarRegistrationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("It seems like your car details are missing, please fill the details")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this, CarRegistrationActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }).setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startKeepLoggedInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to keep the user details for quick login next time?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveToSharedPreferences();
                        login();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                login();
            }
        })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveToSharedPreferences() {
        SharedPreferencesUtils.saveEmail(binding.emailActv.getText().toString(), this);
        SharedPreferencesUtils.savePassword(binding.passwordEt.getText().toString(), this);
        SharedPreferencesUtils.setIsQuickLogin(true, this);
    }

    private void startForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        finish();
        startActivity(intent);
    }

    private void startSignUPActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Error: Sign In Failed!", Toast.LENGTH_SHORT).show();
        binding.loginBtn.setError("Sign in failed");
    }

    private void showGoogleErrorMessage() {
        Toast.makeText(this, "Google authentication failed!", Toast.LENGTH_SHORT).show();
    }

    private void showFacebookErrorMessage() {
        Toast.makeText(this, "Facebook authentication failed!", Toast.LENGTH_SHORT).show();
        binding.facebookLoginBtn.setError("Failed to sign in with facebook!");
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mHomeWatcher.stopWatch();
    }

}
