package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class favourites extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db =  FirebaseFirestore.getInstance();

    private String userID;


    List<String> postList = new ArrayList<String>();
    List<String> nameList = new ArrayList<String>();
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList = new ArrayList<String>();

    RecyclerView fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize favourites page
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        userID = String.valueOf(activeUser);

        setContentView(R.layout.activity_favourites);
        getPostsFromDatabase();
    }

    public void getPostsFromDatabase () {
        //Retrieve a list of all favourited items connected to the currently logged in user
        db.collection("accounts/"+userID+"/favourites")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                postList.clear();
                for(DocumentSnapshot val: value){
                    postList.add(val.getString("pContent"));
                }
                nameList.clear();
                for(DocumentSnapshot snapshot: value) {
                    nameList.add(snapshot.getString("pWriter"));
                }
                dateList.clear();
                for(DocumentSnapshot snapshot: value){
                    dateList.add(Objects.requireNonNull(snapshot.getTimestamp("pDate")).toDate().toString());
                }
            }
        });
        fList = findViewById(R.id.flist);
        PostAdapter myAdapter = new PostAdapter(this, nameList, postList, dateList, postPic);

        fList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        fList.setLayoutManager(new LinearLayoutManager(this));
        fList.setAdapter(myAdapter);
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