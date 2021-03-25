package com.example.a4p02app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a4p02app.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private boolean searcherIsDown;
    private View slidingSearch;
    private View searcher;
    private View closerArrow;
    private View header;
    private View sb;
    private View searchbar;
    private View view;
    List<String> postList = new ArrayList<String>();
    List<String> nameList = new ArrayList<String>();
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList = new ArrayList<String>();

    RecyclerView pList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();


        FirebaseAuth.getInstance().signOut();

        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        if (activeUser == null) {
            goLogin();
        }
        else {
            goHome(view);
        }

        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;//starts the searcher off as not visible

        fsdb.collection("posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            //adds all current field in firestore to the lists
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

        pList = findViewById(R.id.plist);
        PostAdapter myAdapter = new PostAdapter(this, nameList, postList, dateList, postPic);

        pList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        pList.setLayoutManager(new LinearLayoutManager(this));
        pList.setAdapter(myAdapter);
    }
    private void slideUp() {
        //method to bring up the searchbar

        header = findViewById(R.id.header); //sets the header to be visible
        header.setVisibility(View.VISIBLE);
        searcher = findViewById(R.id.searcher);//sets the entire search area to be visible
        searcher.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, slidingSearch.getHeight()*-1);
        animate.setDuration(500);
        animate.setFillAfter(true);
        sb = findViewById(R.id.search_button); //sets the search button to be visible
        sb.setVisibility(View.VISIBLE);
        slidingSearch.startAnimation(animate);
        slidingSearch.postDelayed(() -> searchbar.setVisibility(View.INVISIBLE), 300);
        slidingSearch.setVisibility(View.GONE); //sets the search area to be gone
        closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be gone
        closerArrow.setVisibility(View.INVISIBLE);

        searcherIsDown = false;
    }

    public void reveal(View view) {
        //method to slide down the searchbar
        if (searcherIsDown) {
            slideUp();

        }
        else {
            header = findViewById(R.id.header); //sets the header to be visible
            header.setVisibility(View.VISIBLE);
            searchbar = findViewById(R.id.searchbar); //to be replaced with search bar
            searchbar.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0,0, slidingSearch.getHeight()*-1,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            slidingSearch.startAnimation(animate);
            slidingSearch.postDelayed(() -> searchbar.setVisibility(View.VISIBLE), 300);//delays start of Visibility of standin/searchbar
            sb = findViewById(R.id.search_button); //sets the search button to be gone
            sb.setVisibility(View.INVISIBLE);
            closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be visible
            closerArrow.setVisibility(View.VISIBLE);


            searcherIsDown = true;
        }
    }

    public void goHome(View view) {//reloads Home page since user is at home page already
        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    public void goInfo(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, nonProfit.class);
        startActivity(intent);
    }

    public void goFavs(View view) {//will go to Users favourited non-profits
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }

    public void makePost(View view) {//will bring user to post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }

    public void goLogin() {//brings user to the login page
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }


    public void updateUser(FirebaseUser activeUser){
        this.activeUser = activeUser;
    }
}