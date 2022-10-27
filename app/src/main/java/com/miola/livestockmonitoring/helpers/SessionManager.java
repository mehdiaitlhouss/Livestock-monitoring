package com.miola.livestockmonitoring.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONENUMBER = "phoneNumber";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DATE = "date";
    private static final String KEY_GENDER = "gender";

    private static final String KEY_ANIMAL_TYPE = "animalType";

    public SessionManager(Context _context) {
        context = _context;
        userSession = _context.getSharedPreferences("userSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String fullname, String username, String email, String phoneNumber, String password, String date, String gender) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, fullname);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_GENDER, gender);

        editor.commit();
    }

    public void createAnimalTypeSession(String animalType) {
        editor.putString(KEY_ANIMAL_TYPE, animalType);

        editor.commit();
    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }

    public String getAnimalTypeFromSession() {
        return userSession.getString(KEY_ANIMAL_TYPE, null);
    }

    public boolean checkLogin() {
        return userSession.getBoolean(IS_LOGIN, true) ? true : false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
