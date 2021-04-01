package com.example.a4p02app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class NPOlist extends AppCompatActivity {

    boolean searcherIsDown;
    View slidingSearch;
    View searcher;
    View closerArrow;
    View header;
    View back;
    View sb;
    View searchbar;
    ListView mList;
    String[] messageList = {"NPO 1", "NPO 2", "NPO 3", "NPO 4",
            "NPO 5", "NPO 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize messages page

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npo_list);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;//starts the searcher off as not visible
        mList = (ListView)findViewById(R.id.homeList); //sets up the array of announcements
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_home_list, R.id.NPO_name, messageList);
        mList.setAdapter(arrayAdapter);
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
        //standin = findViewById(R.id.searchbar); //to be replaced by search bar
        //standin.setVisibility(View.GONE);
        closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be gone
        closerArrow.setVisibility(View.INVISIBLE);

        searcherIsDown = false;
    }

    public void reveal(View view) {
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
            slidingSearch.postDelayed(() -> searchbar.setVisibility(View.VISIBLE), 300);
            //delays start of Visibility of standin/searchbar
            sb = findViewById(R.id.search_button); //sets the search button to be gone
            sb.setVisibility(View.INVISIBLE);
            closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be visible
            closerArrow.setVisibility(View.VISIBLE);
            back = findViewById(R.id.back);
            back.setVisibility(View.VISIBLE);

            searcherIsDown = true;
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

    public void goNPOList(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, NPOlist.class);
        startActivity(intent);
    }

    public void goFavs(View view) {//will go to Users favourited non-profits
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }

    public void makePost(View view) {//will pop up post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }
}