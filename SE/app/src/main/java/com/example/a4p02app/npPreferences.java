package com.example.a4p02app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class npPreferences extends Activity {

    String npName;
    String npDesc;
    String profilePicName;
    String documentID;
    String webURL;
    String phoneNum;
    String address;
    String emailAddress;

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonprofit_preferences);

        //this page should be sent the id of the non-profit in the intent so that
        //the correct non-profit is displayed
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //if there is no nonprofit name given in the intent get a default one
            npName = "No Non-Profit Name added.";
            profilePicName = "blank_profile_picture";
            npDesc = "No description added.";
            webURL ="No website added.";
            phoneNum ="No phone number added.";
            address = "No address added.";
            emailAddress = "No email address added.";
            documentID = "cYHsj179NpLh2Hp6evoN";
            getInfo();
        } else {
            //get all info from the database here
            documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
            getFromDatabase();
        }
    }

    //this method gets the info from the database
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        //should change testNP later
        DocumentReference docRef = db.collection("nonprofits").document(documentID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        npName = document.getString("npName");
                        npDesc = document.getString("npDescription");
                        profilePicName = document.getString("profilePic");
                        webURL = document.getString("webURL");
                        address = document.getString("address");
                        phoneNum = document.getString("phoneNumber");
                        emailAddress = document.getString("email");

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

    //this method updates the page to match the non-profit's info
    public void getInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npName);
        nonProfitName.setText(npName);

        //update address
        TextView addr = (TextView) findViewById(R.id.address);
        addr.setText(address);

        //update email
        TextView emailAddr = (TextView) findViewById(R.id.email);
        emailAddr.setText(emailAddress);

        //update description
        TextView nonProfitDesc = (TextView) findViewById(R.id.description);
        nonProfitDesc.setText(npDesc);

        //update phone number
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNum);
        phoneNumber.setText(phoneNum);

        //update website
        TextView webView = (TextView) findViewById(R.id.website);
        webView.setText(webURL);
    }

    //makes the changes to the databases
    public void makeChanges(View view) {
        DocumentReference docRef = db.collection("nonprofits").document(documentID);

        TextView nonProfitName = (TextView) findViewById(R.id.npName);
        TextView addr = (TextView) findViewById(R.id.address);
        TextView emailAddr = (TextView) findViewById(R.id.email);
        TextView nonProfitDesc = (TextView) findViewById(R.id.description);
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNum);
        TextView webView = (TextView) findViewById(R.id.website);

        //update all of the fields to the new values
        docRef.update("npName", nonProfitName.getText().toString());
        docRef.update("address", addr.getText().toString());
        docRef.update("email", emailAddr.getText().toString());
        docRef.update("npDescription", nonProfitDesc.getText().toString());
        docRef.update("phoneNumber", phoneNumber.getText().toString());
        docRef.update("webURL", webView.getText().toString());

        Toast toast = Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT);
        toast.show();
    }

    //changes the profile picture of the non-profit
    public void changePicture(View view) {
        try{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);
        }catch(Exception e){
            Log.i("Error: ",e.toString());
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                Log.i("File not found: ", e.toString());
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "No profile picture was selected.",Toast.LENGTH_LONG).show();
        }
    }

    //goes back to the previous activity
    public void goBack(View view) {
        finish();
    }
} 
