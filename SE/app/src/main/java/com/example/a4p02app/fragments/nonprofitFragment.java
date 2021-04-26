package com.example.a4p02app.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a4p02app.MainActivity;
import com.example.a4p02app.R;

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

public class nonprofitFragment extends Fragment {

    private String passedName;
    private String npName;
    private String npDesc;
    private String profilePicName;
    private String documentID;
    private String webURL;
    private String phoneNum;
    private String unitNum;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String emailAddress;

    private ImageView btnEmail;
    private ImageView btnMap;
    private ImageView btnOrg;
    private ImageView btnWeb;

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View v;

    /*public static nonprofitFragment newInstance(String param1) {
        nonprofitFragment fragment = new nonprofitFragment();
        Bundle args = new Bundle();
        args.putString(param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_non_profit, container, false);
        passedName = getArguments().getString("name");
        System.out.println(passedName +"--------------------------------------------");
        //this page should be sent the id of the non-profit in the intent so that
        //the correct non-profit is displayed
        //Bundle extras = getActivity().getIntent().getExtras();
        if (passedName == null) {
            //if there is no nonprofit name given in the intent display a default one
            npName = "No Non-Profit Name added.";
            profilePicName = "blank_profile_picture";
            npDesc = "No description added.";
            webURL ="No website added.";
            phoneNum ="No phone number added.";
            address = "No address added";
            //unitNum = "No unit number added.";
            //sName = "No street name added.";
            //city = "No city added.";
            //postalCode = "No postal code added.";
            emailAddress = "No email address added.";
            getInfo(npName);
        } else {
            //get all info from the database here
            documentID = passedName;
            //documentID = extras.getString("NON_PROFIT_TO_DISPLAY");
            getFromDatabase();
            // Set title bar
            //((MainActivity) getActivity())
                    //.setActionBarTitle(npName);
        }

        btnEmail = v.findViewById(R.id.email);
        btnMap = v.findViewById(R.id.map);
        btnOrg = v.findViewById(R.id.callOrg);
        btnWeb = v.findViewById(R.id.visitWebsite);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(v);
            }
        });

        btnOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOrganization(v);
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite(v);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        // Set title bar
        //((MainActivity) getActivity())
                //.setActionBarTitle(npName);
    }

    //this method gets the info from the database
    //not done yet
    //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
        //should change testNP later
        DocumentReference docRef = db.collection("nonprofits").document(documentID);;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        // Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        npName = passedName;
                        System.out.println(passedName +"--------------------------------------------");
                        npDesc = document.getString("npDescription");
                        profilePicName = document.getString("profilePic");
                        webURL = document.getString("webURL");
                        address = document.getString("address");
                        //get the map for the address values
                        //Map<String, String> addMap = (Map<String, String>) document.get("address");
                        //unitNum = addMap.get("unit_number");
                        //sName = addMap.get("street_name");
                        //city = addMap.get("city");
                        //postalCode = addMap.get("postal_code");
                        //province = addMap.get("province");

                        phoneNum = document.getString("phoneNumber");
                        emailAddress = document.getString("email");
                        //update the page
                        getInfo(npName);
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
    public void getInfo(String npName){
        //update non-profit's name
        //TextView nonProfitName = (TextView) v.findViewById(R.id.npNameDisplay);
        //nonProfitName.setText(npName);
        //getActivity().getActionBar().setTitle("Fragment1");
        //update profile picture -- change this to work with image handler
        /*ImageView nonProfitPic = (ImageView) findViewById(R.id.profilePic);
        if (profilePicName.equals("") || profilePicName == null) {
            profilePicName = "blank_profile_picture";
        }
        int id = getResources().getIdentifier(profilePicName, "drawable", getPackageName());
        nonProfitPic.setImageResource(id);*/

        //update description
        TextView nonProfitDescription = (TextView) v.findViewById(R.id.profilePart);
        String s = npDesc + "\n\n Address: " + getAddressAsString() + "\nPhone number: " + phoneNum + "\nEmail Address: " + emailAddress;
        nonProfitDescription.setText(s);
    }

    private String getAddressAsString(){
        return address; //unitNum + " " +  sName + ", " + city + ", " + province + ", " + postalCode;
    }

    public void openWebsite(View view){
        if (webURL.equals("")) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "This organization does not have a website available.", Toast.LENGTH_SHORT);
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
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "This organization does not have a phone number available.", Toast.LENGTH_SHORT);
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
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "This organization does not have an email address available.", Toast.LENGTH_SHORT);
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
        if(address.equals("")) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "This organization does not have an address available.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String map = "http://maps.google.co.in/maps?q=" + getAddressAsString();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        }
    }



    public void writeMessage(View view) {
        //message writing pop-up?
        //then when sent it saves to past messages accessible by user profile
    }
}
