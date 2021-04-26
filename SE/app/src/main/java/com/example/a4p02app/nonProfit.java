package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

//the default profile picture is from https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/

public class nonProfit extends AppCompatActivity {

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

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_profit);

        //this page should be sent the id of the non-profit in the intent so that
        //the correct non-profit is displayed
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //if there is no nonprofit name given in the intent display a default one
            npName = "No Non-Profit Name added.";
            profilePicName = "blank_profile_picture";
            npDesc = "No description added.";
            webURL ="No website added.";
            phoneNum ="No phone number added.";
            unitNum = "No unit number added.";
            sName = "No street name added.";
            city = "No city added.";
            postalCode = "No postal code added.";
            emailAddress = "No email address added.";
            getInfo();
        } else {
            //get all info from the database here
            documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
            getFromDatabase();
        }
    }

    //this method gets the info from the database
    //not done yet
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        //should change testNP later
        DocumentReference docRef = db.collection("test").document(documentID);;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                       // Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        npName = document.getString("if_npo_name");
                        npDesc = document.getString("if_npo_desc");
                        profilePicName = document.getString("profilePic");
                        webURL = document.getString("if_npo_url");

                        //get the map for the address values
                        Map<String, String> addMap = (Map<String, String>) document.get("address");
                        unitNum = addMap.get("unit_number");
                        sName = addMap.get("street_name");
                        city = addMap.get("city");
                        postalCode = addMap.get("postal_code");
                        province = addMap.get("province");

                        phoneNum = document.getString("if_npo_phone");
                        emailAddress = document.getString("user_email");
                        //update the page
                        getInfo();
                    } else {
                        Log.d("TAG: ", "No such document");
                    }
                } else {
                    Log.d("TAG: ", "get failed with ", task.getException());
                }
            }
        });
   }

    public void fromList(){
        if(getIntent().hasExtra("npoName")){
            npName = getIntent().getStringExtra("npoName");
        }
    }

    //this method updates the page to match the non-profit's info
    public void getInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npNameDisplay);
        nonProfitName.setText(npName);

        //update profile picture -- change this to work with image handler
        /*ImageView nonProfitPic = (ImageView) findViewById(R.id.profilePic);
        if (profilePicName.equals("") || profilePicName == null) {
            profilePicName = "blank_profile_picture";
        }
        int id = getResources().getIdentifier(profilePicName, "drawable", getPackageName());
        nonProfitPic.setImageResource(id);*/

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

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
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
