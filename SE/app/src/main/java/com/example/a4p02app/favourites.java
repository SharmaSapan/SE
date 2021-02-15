package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class favourites extends AppCompatActivity {

    boolean searcherIsDown;
    View slidingSearch;
    View searcher;
    View closerArrow;
    View header;
    View headerline;
    View sb;
    View standin;
    View searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize favourites page

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;
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

    public void goDonos(View view) {
    }

    public void goFavs(View view) {
    }

    public void goProfile(View view) {
    }

    public void goHome(View view) {
    }
}