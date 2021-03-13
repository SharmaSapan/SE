package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class makePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
    }

    public void writePost(View view) {
    }

    public void goBack(View view) {
        //go back to the previous activity
        //if post is being written, ask user if they want to finish or leave page
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
    public void goInfo(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, nonProfit.class);
        startActivity(intent);
    }
    public void goFavs(View view) {//will go to Users favourited non-profits
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }
    public void mPost(View view) {//will reload post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }
}