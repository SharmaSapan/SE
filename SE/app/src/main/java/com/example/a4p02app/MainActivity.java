package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
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
    private View headerline;
    private View sb;
    private View standin;
    private View searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseAuth.getInstance().signOut();

        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        if (activeUser == null) {
            goLogin();
        }
        else {
            goHome();
        }
    }

    private void slideUp() {
        //method to bring up the searchbar

        header = findViewById(R.id.header); //sets the header to be visible
        header.setVisibility(View.VISIBLE);
        searcher = findViewById(R.id.searcher);//sets the entire search area to be visible
        searcher.setVisibility(View.VISIBLE);
        headerline.setElevation(1);   //this is to under impose searcher onto what is in background
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, slidingSearch.getHeight()*-1);
        animate.setDuration(0);
        animate.setFillAfter(true);
        sb = findViewById(R.id.search_button); //sets the search button to be visible
        sb.setVisibility(View.VISIBLE);
        slidingSearch.startAnimation(animate);
        slidingSearch.setVisibility(View.GONE); //sets the search area to be gone

        standin = findViewById(R.id.standin); //to be replaced by search bar
        standin.setVisibility(View.GONE);
        closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be gone
        closerArrow.setVisibility(View.GONE);

        searcherIsDown = false;
    }

    public void reveal(View view) {
        if (searcherIsDown) {
            slideUp();

        }
        else {
            header = findViewById(R.id.header); //sets the header to be visible
            header.setVisibility(View.VISIBLE);
            headerline = findViewById(R.id.headerline); //sets the header border to be gone
            headerline.setVisibility(View.GONE);
            standin = findViewById(R.id.standin); //to be replaced with search bar
            standin.setVisibility(View.GONE);
            TranslateAnimation animate = new TranslateAnimation(0,0, slidingSearch.getHeight()*-1,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            slidingSearch.startAnimation(animate);
            slidingSearch.postDelayed(() -> standin.setVisibility(View.VISIBLE), 300);
            //delays start of Visibility of standin/searchbar
            sb = findViewById(R.id.search_button); //sets the search button to be gone
            sb.setVisibility(View.GONE);
            closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be visible
            closerArrow.setVisibility(View.VISIBLE);


            searcherIsDown = true;
        }
    }

    public void goProfile(View view) {//will go to User profile
        Toast.makeText(MainActivity.this, "Profile not yet completed", Toast.LENGTH_SHORT).show();
    }


    public void goFavs(View view) {//will go to User favourites
        Toast.makeText(MainActivity.this, "Favourites not yet completed", Toast.LENGTH_SHORT).show();
    }

    public void goDonos(View view) {//will go to users completed donations
        Toast.makeText(MainActivity.this, "Donations not yet completed", Toast.LENGTH_SHORT).show();
    }

    public void makePost(View view) {
        Toast.makeText(MainActivity.this, "Posts not yet implemented", Toast.LENGTH_SHORT).show();
    }

    public void goInfo(View view) {
        Toast.makeText(MainActivity.this, "Info page not yet implemented", Toast.LENGTH_SHORT).show();
    }

    public void goLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void goHome() {
        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;
        Toast.makeText(MainActivity.this, "You are Home", Toast.LENGTH_SHORT).show();
    }


    public void updateUser(FirebaseUser activeUser){
        this.activeUser = activeUser;
    }
}