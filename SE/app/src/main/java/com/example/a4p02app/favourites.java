package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class favourites extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db =  FirebaseFirestore.getInstance();

    String userID;
    String [] favs;
    int count;
    ArrayList<String> favID = new ArrayList<String>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize favourites page
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        userID = String.valueOf(activeUser);

        setContentView(R.layout.activity_favourites);

        listView = (ListView)findViewById(R.id.listview);
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
        favs = new String[favID.size()];
        count = 0;
        while(favID.get(count) != null){
            DocumentReference docRef = db.collection("posts").document(favID.remove(count));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //print to screen
                            favs[count] = document.getString("pTitle");
                            Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        } else {
                            favs[count] = "Post not longer exists.";
                            Log.d("TAG: ", "No such document");
                        }
                    } else {
                        favs[count] = "Error.";
                        Log.d("TAG: ", "get failed with ", task.getException());
                    }
                }
            });
            count++;
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,favs);
        listView.setAdapter(arrayAdapter);
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

    public void goNPOList(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, NPOlist.class);
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