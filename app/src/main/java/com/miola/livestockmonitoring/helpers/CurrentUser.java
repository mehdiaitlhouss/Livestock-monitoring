package com.miola.livestockmonitoring.helpers;


import com.miola.livestockmonitoring.model.User;

public class CurrentUser {

    private static User user;

    private CurrentUser() {
    }

    public static User getCurrentUser() {
        return user;
    }

    public static void setCurrentUser(User currentUser) {
        user = currentUser;
    }
}