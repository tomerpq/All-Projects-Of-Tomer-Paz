package com.example.spotparking.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.DB.WriteAndReadData;
import com.example.spotparking.R;
import com.example.spotparking.Utils.VerifyInputs;
import com.example.spotparking.Utils.VerifyPermissions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarRegistrationActivity extends AppCompatActivity {

//    private String manufactureString, modelString, colorString, numberString;
    private Button register_btn;
    private EditText mManufacturer, mModel_et, mColor_et, mNumber_et;

    private boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        buildButtons();
        initButtons();
    }

    private void buildButtons(){
        register_btn = findViewById(R.id.continue_btn);
        mManufacturer = findViewById(R.id.car_manufacture_et);
        mModel_et = findViewById(R.id.car_model_et);
        mColor_et = findViewById(R.id.car_color_et);
        mNumber_et = findViewById(R.id.car_number_et);
    }

    private void initButtons(){
        register_btn.setOnClickListener(view -> {
            String carNumber = mNumber_et.getText().toString().trim();
            String carModel = mModel_et.getText().toString().trim();
            String manufacturer = mManufacturer.getText().toString().trim();
            String color = mColor_et.getText().toString().trim();
            if (color.isEmpty() || VerifyInputs.validateColor(color)){
                mColor_et.setError("Please enter a valid car color");
                return;
            }
            mColor_et.setError(null);
            if (manufacturer.isEmpty() || manufacturer.contains("DELETE") || manufacturer.contains("REMOVE")){
                mManufacturer.setError("Please enter car manufacturer");
                return;
            }
            mManufacturer.setError(null);
            WriteAndReadData ward = new WriteAndReadData();
            ward.saveDetails(manufacturer,carModel,color,carNumber,0);
            isDone = true;
            Toast.makeText(CarRegistrationActivity.this, "Successfully registered, going back to login.",
                            Toast.LENGTH_SHORT).show();

            goToLogin();
        });
    }

    private void goToLogin(){
        Intent myIntent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(myIntent);
    }

    /**
     * start alert dialog before going back to login.
     */
    private void startAlertDialogLeaving() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CarRegistrationActivity.this);
        builder.setMessage("Are you sure you want to leave current progress? Any changes will be lost.")
                .setCancelable(true)
                // before leaving, delete user details to avoid missing details in the registration
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToLogin();
                    }
                }).setNegativeButton("No", null).setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        startAlertDialogLeaving();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if the user exited before finishing all the details, delete his account.
        if (!isDone){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                final FirebaseUser currentUser = auth.getCurrentUser();
                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(CarRegistrationActivity.this, LoginActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
            }
        }
    }

}