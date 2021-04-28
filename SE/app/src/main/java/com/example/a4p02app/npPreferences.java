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

import java.io.FileNotFoundException;
import java.io.InputStream;


public class npPreferences extends Activity {

    String npName;
    String npDesc;
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
        setContentView(R.layout.nonprofit_preferences);

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
        getFromDatabase();

    }

    //this method gets the info from the database
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
        getInfo();
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
        userData.getInstance().setNpo_name(nonProfitName.getText().toString());
        userData.getInstance().setNpo_desc(nonProfitDesc.getText().toString());
        userData.getInstance().setNpo_url(webView.getText().toString());
        userData.getInstance().setPhone(phoneNumber.getText().toString());
        userData.getInstance().setAddress_unit(unit.getText().toString());
        userData.getInstance().setAddress_street(street.getText().toString());
        userData.getInstance().setAddress_city(cV.getText().toString());
        userData.getInstance().setAddress_province(province);
        userData.getInstance().setAddress_postal(pCode.getText().toString());
        userData.getInstance().setEmail(emailAddr.getText().toString());

        Toast toast = Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT);
        toast.show();
        // update data app wide
        userData.getInstance().updateData();
    }

    //changes the profile picture of the non-profit
    public void changePicture(View view) {
       try{
           //upload the image
            showImageChooser();
            imageHandler i = new imageHandler(filePath);
            i.uploadProfile();
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
