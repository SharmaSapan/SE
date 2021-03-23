package com.example.a4p02app.data;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Firestore {

    private static final String TAG = "Firestore";

    /**
     * Adds a user to the accounts document in firestore.
     *
     * @param email
     * @param accountType
     * @return boolean to indicate success
     */
    public static boolean addAccount(FirebaseFirestore db, String email, FirebaseUser user, int accountType) {
        Date currentTime = Calendar.getInstance().getTime();

        Map<String, Object> newAccount = new HashMap<>();
        newAccount.put("accountType", accountType);
        newAccount.put("address", "");
        newAccount.put("createDate", currentTime);
        newAccount.put("first", "");
        newAccount.put("last", "");
        newAccount.put("isActive", true);
        newAccount.put("postalCode", "");
        newAccount.put("email", email);
        newAccount.put("lastUpdateDate", currentTime);

        Task<Void> z = db.collection("accounts")
                .document(user.getUid())
                .set(newAccount)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + user.getUid());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding user to database", e);
                });

        if (z.isSuccessful()){
            return true;
        }
        return false;
    }

    /**
     * Remove a user from firebase authentication. Set document to inactive.
     *
     * @param email
     * @return atomic integer (effectively a boolean) to indicate if remove was successful
     */
    public static boolean removeAccount(FirebaseFirestore db, String email, FirebaseUser user) {
        Map<String, Object> account = new HashMap<>();
        account.put("isActive", 0);

        Task<Void> z = db.collection("accounts").document(user.getUid())
                .set(account)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "removeAccount:success");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "removeAccount:failure", e);
                });
        if (z.isSuccessful()){
            return true;
        }
        return false;
    }
}

