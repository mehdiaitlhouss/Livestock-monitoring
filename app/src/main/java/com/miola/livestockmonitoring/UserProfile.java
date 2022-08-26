package com.miola.livestockmonitoring;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miola.livestockmonitoring.helpers.CurrentUser;
import com.miola.livestockmonitoring.model.User;

public class UserProfile extends AppCompatActivity {

    private TextInputLayout fullNameProfilTextInputLayout;
    private TextInputLayout emailProfilTextInputLayout;
    private TextInputLayout passwordProfilTextInputLayout;
    private TextInputLayout phoneNumberProfilTextInputLayout;

    private TextView nameProfilTextView;
    private TextView usernameProfilTextView;

    private ImageView profilImageView;

    private String _USERNAME;
    private String _NAME;
    private String _EMAIL;
    private String _PHONENUMBER;
    private String _PASSWORD;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        fullNameProfilTextInputLayout = findViewById(R.id.full_name_profile);
        emailProfilTextInputLayout = findViewById(R.id.email_profile);
        phoneNumberProfilTextInputLayout = findViewById(R.id.phone_number_profile);
        passwordProfilTextInputLayout = findViewById(R.id.password_profile);
        nameProfilTextView = findViewById(R.id.fullname_field);
        usernameProfilTextView = findViewById(R.id.username_field);
        profilImageView = findViewById(R.id.profile_image);

        reference = FirebaseDatabase.getInstance().getReference("users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // show all data
        showAllData();
    }

    private void showAllData() {
        User user = CurrentUser.getCurrentUser();

        if (user != null) {
            System.out.println(user);
            _USERNAME = user.getUsername();
            _NAME = user.getName();
            _EMAIL = user.getEmail();
            _PHONENUMBER = user.getPhoneNumber();
            _PASSWORD = user.getPassword();

            nameProfilTextView.setText(user.getName());
            usernameProfilTextView.setText(user.getUsername());
            fullNameProfilTextInputLayout.getEditText().setText(user.getName());
            emailProfilTextInputLayout.getEditText().setText(user.getEmail());
            phoneNumberProfilTextInputLayout.getEditText().setText(user.getPhoneNumber());
            passwordProfilTextInputLayout.getEditText().setText(user.getPassword());
        }
        else
        {
            System.out.println(user);
        }
    }

    public void update(View view)
    {
        if (isNameChanged() || isPasswordChanged() || isPhoneNumberChanged() || isEmailChanged() || isUsernameChanged())
        {
            Toast.makeText(this, "Data has ben updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Data is the same can not be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isUsernameChanged() {
        if(!_USERNAME.equals(usernameProfilTextView.getText().toString()))
        {
            reference.child(firebaseUser.getUid()).child("username").setValue(usernameProfilTextView.getText().toString());
            _USERNAME = emailProfilTextInputLayout.getEditText().getText().toString();
            CurrentUser.getCurrentUser().setUsername(_USERNAME);
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!_EMAIL.equals(emailProfilTextInputLayout.getEditText().getText().toString()))
        {
            reference.child(firebaseUser.getUid()).child("email").setValue(emailProfilTextInputLayout.getEditText().getText().toString());
            _EMAIL = emailProfilTextInputLayout.getEditText().getText().toString();
            CurrentUser.getCurrentUser().setEmail(_EMAIL);
            return true;
        }else{
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!_PHONENUMBER.equals(phoneNumberProfilTextInputLayout.getEditText().getText().toString()))
        {
            reference.child(firebaseUser.getUid()).child("phoneNumber").setValue(phoneNumberProfilTextInputLayout.getEditText().getText().toString());
            _PHONENUMBER = phoneNumberProfilTextInputLayout.getEditText().getText().toString();
            CurrentUser.getCurrentUser().setPhoneNumber(_PHONENUMBER);
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordChanged(){
        if(!_PASSWORD.equals(passwordProfilTextInputLayout.getEditText().getText().toString()))
        {
            reference.child(firebaseUser.getUid()).child("password").setValue(passwordProfilTextInputLayout.getEditText().getText().toString());
            _PASSWORD = passwordProfilTextInputLayout.getEditText().getText().toString();
            CurrentUser.getCurrentUser().setPassword(_PASSWORD);
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged(){
        if(!_NAME.equals(fullNameProfilTextInputLayout.getEditText().getText().toString())){
            reference.child(firebaseUser.getUid()).child("name").setValue(fullNameProfilTextInputLayout.getEditText().getText().toString());
            _NAME = fullNameProfilTextInputLayout.getEditText().getText().toString();
            CurrentUser.getCurrentUser().setName(_NAME);
            return true;
        }else{
            return false;
        }
    }
}