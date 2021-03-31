package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void goBack(View view) {
        //go back to the previous activity
        finish();
    }
    public void goDonos(View view) {//will go to users completed donations
        Intent intent = new Intent(this, donations.class);
        startActivity(intent);
    }
    public void goMessages(View view) {//will go to users sent messages
        Intent intent = new Intent(this, messages.class);
        startActivity(intent);
    }

    public void goHome(View view) {//will go to Home page
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

    public void goNPOList(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, NPOlist.class);
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
}