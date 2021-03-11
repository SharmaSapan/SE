package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a4p02app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    ListView aList;
    String[] theList = {"Donate Message 1", "Donate Message 2", "Donate Message 3", "Donate Message 4",
            "Donate Message 5", "Donate Message 6","Donate Message 7","Donate Message 8","Donate Message 9",
            "Donate Message 10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        aList = (ListView)findViewById(R.id.homeList); //sets up the array of announcements
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_home_list, R.id.textView_, theList);
        aList.setAdapter(arrayAdapter);
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