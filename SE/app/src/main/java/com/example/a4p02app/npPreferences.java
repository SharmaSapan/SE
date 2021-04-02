package com.example.a4p02app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class npPreferences extends Activity {

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
        setContentView(R.layout.nonprofit_preferences);

        //get the currently signed in user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser activeUser = mAuth.getCurrentUser();
        documentID = mAuth.getCurrentUser().getUid().toString();

        //create the spinner for the provinces
        Spinner provList = (Spinner) findViewById(R.id.prov);
        String[] items = new String[]{"NL", "PE", "NS", "NB", "QC", "ON", "MB", "SK", "AB", "BC", "YT", "NT", "NU"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provList.setAdapter(a);

        //get the currently selected province when the spinner is updated
        provList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                province = (String) parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
            unitNum = "No unit number added.";
            sName = "No street name added.";
            city = "No city added.";
            postalCode = "No postal code added.";
            emailAddress = "No email address added.";
            getInfo();
        } else {
            //get all info from the database here
            //documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
            getFromDatabase();
        }
    }

    //this method gets the info from the database
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        //should change testNP later
        DocumentReference docRef = db.collection("test").document(documentID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
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

    //this method updates the page to match the non-profit's info
    public void getInfo(){
        //update non-profit's name
        TextView nonProfitName = (TextView) findViewById(R.id.npName);
        nonProfitName.setText(npName);

        //update address
        TextView unit = (TextView) findViewById(R.id.unitNumber);
        unit.setText(unitNum);
        TextView street = (TextView) findViewById(R.id.streetName);
        street.setText(sName);
        TextView cV = (TextView) findViewById(R.id.city);
        cV.setText(city);
        TextView pCode = (TextView) findViewById(R.id.postalCode);
        pCode.setText(postalCode);

        //update the spinner to use the saved province
        Spinner p = (Spinner) findViewById(R.id.prov);
        p.setSelection(getSpinnerIndex(p, province));

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
        DocumentReference docRef = db.collection("test").document(documentID);

        TextView nonProfitName = (TextView) findViewById(R.id.npName);

        TextView unit = (TextView) findViewById(R.id.unitNumber);
        TextView street = (TextView) findViewById(R.id.streetName);
        TextView cV = (TextView) findViewById(R.id.city);
        TextView pCode = (TextView) findViewById(R.id.postalCode);

        TextView emailAddr = (TextView) findViewById(R.id.email);
        TextView nonProfitDesc = (TextView) findViewById(R.id.description);
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNum);
        TextView webView = (TextView) findViewById(R.id.website);

        //update all of the values
        docRef.update(
                "npName", nonProfitName.getText().toString(),
                "email", emailAddr.getText().toString(),
                "npDescription", nonProfitDesc.getText().toString(),
                "phoneNumber", phoneNumber.getText().toString(),
                "webURL", webView.getText().toString(),
                "address.city", cV.getText().toString(),
                "address.unit_number", unit.getText().toString(),
                "address.street_name", street.getText().toString(),
                "address.postal_code", pCode.getText().toString(),
                "address.province", province
        );

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

    //returns the index of the spinner which should be selected when the settings page is loaded
    private int getSpinnerIndex(Spinner s, String str) {
        for (int i = 0; i < s.getCount(); i++) {
            if(s.getItemAtPosition(i).toString().equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
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
