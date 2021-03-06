package com.example.a4p02app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class favourites extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db =  FirebaseFirestore.getInstance();

    String userID;
    LinkedList<String> favID = new LinkedList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize favourites page

        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        userID = String.valueOf(activeUser);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

    public void getPostsFromDatabase () {
        //Retrieve a list of all favourited items connected to the currently logged in user
        db.collection("accounts/"+userID+"/favourites").whereEqualTo("favourited", true)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        favID.add(document.getId()); //Add favourited document to list
                        Log.d("TAG: ", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("TAG: ", "Error getting documents: ", task.getException());
                }
            }
        });
        //Connect favourited documents with documents in database and print to screen
        //not finished
        while(favID.getFirst() != null){
            DocumentReference docRef = db.collection("posts").document(favID.removeFirst());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //print to screen
                            Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("TAG: ", "No such document");
                        }
                    } else {
                        Log.d("TAG: ", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public void goBack(View view) {
        //go back to the previous activity
        finish();
    }

    public void goHome(View view) {//will go to Home page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }
    public void goInfo(View view) {//will go to info page of non-profit
        Intent intent = new Intent(this, nonProfit.class);
        startActivity(intent);
    }

    public void goFavs(View view) {//will reload the page
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }

    public void makePost(View view) {//will pop up post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }
}