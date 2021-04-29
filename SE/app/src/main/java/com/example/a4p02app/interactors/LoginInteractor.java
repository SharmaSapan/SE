package com.example.a4p02app.interactors;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import javax.annotation.Nullable;

public class LoginInteractor {

    private static User user;
    private FirebaseFirestore db;
    private DocumentReference user_data;
    @Nullable
    private String test_uid = "67CbuAWgP3V19ziIXAaNS3eCIjC2"; //bj16hh@brocku.ca


    @SuppressLint("RestrictedApi")
    public LoginInteractor(FirebaseFirestore firestore) {
        db = firestore;
        user = new User(test_uid);
    }


}
