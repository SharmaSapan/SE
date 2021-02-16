package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

//the default profile picture is from https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/

public class nonProfit extends AppCompatActivity {

    private String npName;
    private String npDesc;
    private int profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_profit);

        //this page should be sent the name of the non-profit in the intent so that
        //the correct non-profit is displayed
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //if there is no nonprofit name given in the intent display a default one
            npName = "Test Non-Profit";
            profilePic = R.drawable.blank_profile_picture;
            npDesc = "Test non-profit description";
        } else {
            //get all info from the database here
            npName = extras.getString("NON_PROFIT_TO_DISPLAY");
        }

        //update the page (will need to add test nonprofits to the database, for now am just testing with hard-coded strings)
        getInfo();
    }

    //this method updates the page to match the non-profit's info
    //needs to be updated to use info from the database
    public void getInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npNameDisplay);
        nonProfitName.setText(npName);

        //update profile picture
        ImageView nonProfitPic = (ImageView) findViewById(R.id.profilePic);
        nonProfitPic.setImageResource(profilePic);

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
