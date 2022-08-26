package com.miola.livestockmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miola.livestockmonitoring.helpers.CurrentUser;
import com.miola.livestockmonitoring.model.User;

public class SignUp extends AppCompatActivity {

    //Variables
    private TextInputLayout regName;
    private TextInputLayout regUsername;
    private TextInputLayout regEmail;
    private TextInputLayout regPhoneNo;
    private TextInputLayout regPassword;
    private Button regBtn;
    private Button regToLoginBtn;

    // fire base variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        intialiseHooks();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void intialiseHooks() {
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
    }

    private Boolean validateName() {
        String name = regName.getEditText().getText().toString();

        if (name.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String username = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (username.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else if (username.length() >= 15)
        {
            regUsername.setError("Username too long");
            return false;
        }
        else if (! username.matches(noWhiteSpace))
        {
            regUsername.setError("White space not allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String email = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty())
        {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if (! email.matches(emailPattern))
        {
            regEmail.setError("Invalid email address");
            return false;
        }
        else
        {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String phoneNumber = regPhoneNo.getEditText().getText().toString();

        if (phoneNumber.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (password.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if (! password.matches(passwordVal))
        {
            regPassword.setError("Password is too weak");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {
        if (! validateName() | ! validateUsername() | ! validateEmail() | ! validatePhoneNumber() | ! validatePassword())
        {
            return;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNumber = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        User user = new User(name, username, email, phoneNumber, password);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    CurrentUser.setCurrentUser(user);
                    firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    databaseReference.child(userId).setValue(user);
                    Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this, Dashboard.class));
                }else{
                    Toast.makeText(SignUp.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callSignInScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
    }
}