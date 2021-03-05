package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//the default profile picture is from https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/

public class nonProfit extends AppCompatActivity {

    String npName;
    String npDesc;
    String profilePicName;
    String documentID;

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_profit);

        //this page should be sent the id of the non-profit in the intent so that
        //the correct non-profit is displayed
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //if there is no nonprofit name given in the intent display a default one
            npName = "No Profile Found";
            profilePicName = "blank_profile_picture";
            npDesc = "An error occurred, please try again.";
            getInfo();
        } else {
            //get all info from the database here
            documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
            getFromDatabase();
        }
    }

    //this method gets the info from the database
    //not done yet
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        //should change testNP later
        DocumentReference docRef = db.collection("nonprofits").document(documentID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        npName = document.getString("npName");
                        npDesc = document.getString("npDescription");
                        profilePicName = document.getString("profilePic");

                        //update the page
                        getInfo();
                    } else {
                        Log.d("TAG: ", "No such document");
                    }
                } else {
                    Log.d("TAG: ", "get failed with ", task.getException());
                }
            }
        });
    }

    //this method updates the page to match the non-profit's info
    public void getInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npNameDisplay);
        nonProfitName.setText(npName);

        //update profile picture
        ImageView nonProfitPic = (ImageView) findViewById(R.id.profilePic);
        int id = getResources().getIdentifier(profilePicName, "drawable", getPackageName());
        nonProfitPic.setImageResource(id);

        //update description
        TextView nonProfitDescription = (TextView) findViewById(R.id.profilePart);
        nonProfitDescription.setText(npDesc);
    }

    public void goBack(View view) {
        //go back to the previous activity
        finish();
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goProfile(View view) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    public void goFavs(View view) {
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }

    public void goDonos(View view) {
        Intent intent = new Intent(this, donations.class);
        startActivity(intent);
    }

    public void goInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
