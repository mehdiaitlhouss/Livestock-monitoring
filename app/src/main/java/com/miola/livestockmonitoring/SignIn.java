package com.miola.livestockmonitoring;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miola.livestockmonitoring.helpers.CurrentUser;
import com.miola.livestockmonitoring.model.User;

public class SignIn extends AppCompatActivity
{
    private ImageView imageView;
    private TextView logoText;
    private TextView sloganText;
    private TextInputLayout emailLogin;
    private TextInputLayout passwordLogin;
    private Button loginBtn;
    private Button callSignUp;

    // for fireBase Autntification with email and password
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        intialiseHooks();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    private void intialiseHooks() {
        imageView = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        emailLogin = findViewById(R.id.login_email);
        passwordLogin = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);
        callSignUp = findViewById(R.id.btn_signin_to_signup);
    }

    private Boolean validateUsername() {
        String username = emailLogin.getEditText().getText().toString();

        if (username.isEmpty()) {
            emailLogin.setError("Field cannot be empty");
            return false;
        }
        else {
            emailLogin.setError(null);
            emailLogin.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = passwordLogin.getEditText().getText().toString();

        if (password.isEmpty()) {
            passwordLogin.setError("Field cannot be empty");
            return false;
        }
        else
        {
            passwordLogin.setError(null);
            passwordLogin.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        if (! validateUsername() | ! validatePassword())
        {
            return;
        }
        else
        {
            isUser();
        }
    }

    private void isUser() {
        String enteredEmail = emailLogin.getEditText().getText().toString().trim();
        String enteredPassword = passwordLogin.getEditText().getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    emailLogin.setError(null);
                    emailLogin.setErrorEnabled(false);
                    passwordLogin.setError(null);
                    passwordLogin.setErrorEnabled(false);

                    firebaseUser = firebaseAuth.getCurrentUser();

                    databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                User user = dataSnapshot.getValue(User.class);

                                System.out.println("get data from firebase" + user);

                                if (user != null){
                                    //System.out.println("SignIn set user before" + CurrentUser.getCurrentUser());
                                    CurrentUser.setCurrentUser(user);
                                    //System.out.println("SignIn set user after" + CurrentUser.getCurrentUser());
                                    System.out.println("SignIn" + CurrentUser.getCurrentUser());
                                    Toast.makeText(SignIn.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn.this, Dashboard.class));
                                }
                                else {
                                    Toast.makeText(SignIn.this, "user is null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignIn.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    passwordLogin.setError("Wrong Password");
                    passwordLogin.requestFocus();
                    emailLogin.setError("No such user exist");
                    emailLogin.requestFocus();
                }
            }
        });
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        Pair[] pairs = new Pair[7];

        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_text");
        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
        pairs[3] = new Pair<View, String>(emailLogin, "email_tran");
        pairs[4] = new Pair<View, String>(passwordLogin, "password_tran");
        pairs[5] = new Pair<View, String>(loginBtn, "button_tran");
        pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SignIn.this, pairs);
        startActivity(intent, activityOptions.toBundle());
    }

}