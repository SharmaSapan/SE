package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class nonProfit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_profit);
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
