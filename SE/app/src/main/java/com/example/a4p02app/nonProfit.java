package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

//the default profile picture is from https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/

public class nonProfit extends AppCompatActivity {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
    String npName;
    String npDesc;
    String profilePicName;
    String documentID;
    String webURL;
    String phoneNum;
    String unitNum;
    String sName;
    String city;
    String province;
    String postalCode;
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_profit);
//        //this page should be sent the id of the non-profit in the intent so that
//        //the correct non-profit is displayed
//        if (extras == null) {
//            //if there is no nonprofit name given in the intent display a default one
//            npName = "No Non-Profit Name added.";
//            profilePicName = "blank_profile_picture";
//            npDesc = "No description added.";
//            webURL ="No website added.";
//            phoneNum ="No phone number added.";
//            unitNum = "No unit number added.";
//            sName = "No street name added.";
//            city = "No city added.";
//            postalCode = "No postal code added.";
//            emailAddress = "No email address added.";
//            getInfo();
//        } else {
//            //get all info from the database here
//            documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
//        }
        getFromDatabase();
        setInfo();
    }

    //this method gets the info from the database
    //not done yet
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        npName = userData.getInstance().getNpo_name();
        npDesc = userData.getInstance().getNpo_desc();
        //profilePicName = userData.;
        webURL = userData.getInstance().getNpo_url();
        phoneNum = userData.getInstance().getPhone();
        unitNum = userData.getInstance().getAddress_unit();
        sName = userData.getInstance().getAddress_street();
        city = userData.getInstance().getAddress_city();
        province = userData.getInstance().getAddress_province();
        postalCode = userData.getInstance().getAddress_postal();
        emailAddress = userData.getInstance().getEmail();
   }


    //this method updates the page to match the non-profit's info
    public void setInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npNameDisplay);
        nonProfitName.setText(npName);

        //update profile picture
        ImageView nonProfitPic = (ImageView) findViewById(R.id.profilePic);
//        if (profilePicName.equals("") || profilePicName == null) {
//            profilePicName = "blank_profile_picture";
//        }

        Glide.with(nonProfit.this).load(storageReference).into(nonProfitPic);

        //update description
        TextView nonProfitDescription = (TextView) findViewById(R.id.profilePart);
        String s = npDesc + "\n\n Address: " + getAddressAsString() + "\nPhone number: " + phoneNum + "\nEmail Address: " + emailAddress;
        nonProfitDescription.setText(s);
    }

    private String getAddressAsString(){
       return unitNum + " " +  sName + ", " + city + ", " + province + ", " + postalCode;
    }

    public void openWebsite(View view){
        if (webURL.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "This organization does not have a website available.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Uri uri = Uri.parse(webURL);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        }
    }
    
    //this method allows the user to start a phone call with with the organization
    public void callOrganization(View view) {
        if(phoneNum.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "This organization does not have a phone number available.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent callInt = new Intent(Intent.ACTION_VIEW);
            callInt.setData(Uri.parse("tel:" + phoneNum));
            startActivity(callInt);
        }
    }

    //this method allows the user to send an email to the organization
    public void sendEmail(View view){
        if (emailAddress.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "This organization does not have an email address available.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", emailAddress, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
    }

    //this method opens the map
    public void openMap(View view){
        if(sName.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "This organization does not have an address available.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String map = "http://maps.google.co.in/maps?q=" + getAddressAsString();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
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


    public void goInfo(View view) {//reloads the current page?
        Intent intent = new Intent(this, MainActivity.class);
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

    public void openSettings(View view) {
        Intent intent = new Intent(this, npPreferences.class);
        startActivity(intent);
    }

    public void writeMessage(View view) {
        //message writing pop-up?
        //then when sent it saves to past messages accessible by user profile
    }
}
