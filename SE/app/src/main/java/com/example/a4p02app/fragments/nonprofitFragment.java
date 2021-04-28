package com.example.a4p02app.fragments;

import androidx.annotation.Nullable;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a4p02app.R;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4p02app.userData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
    private ImageView btnEmail;
    private ImageView btnMap;
    private ImageView btnOrg;
    private ImageView btnWeb;

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_non_profit, container, false);
        super.onCreate(savedInstanceState);

        passedName = getArguments().getString("name");
        System.out.println(passedName + "--------------------------------------------");

        getFromDatabase();
        setInfo();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
        //this method gets the info from the database
        //not done yet
        //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
    public void getFromDatabase() {
            //npName = userData.getInstance().getNpo_name();
            npName = passedName;
            npDesc = userData.getInstance().getNpo_desc();
            //profilePicName = userData.;
            webURL = userData.getInstance().getNpo_url();
            phoneNum = userData.getInstance().getPhone();
            unitNum = userData.getInstance().getAddress_unit();
            //sName = userData.getInstance().getAddress_street();
            city = userData.getInstance().getAddress_city();
            province = userData.getInstance().getAddress_province();
            postalCode = userData.getInstance().getAddress_postal();
            emailAddress = userData.getInstance().getEmail();

        }


        //this method updates the page to match the non-profit's info
        public void setInfo() {
            //update non-profit's name
            TextView nonProfitName = (TextView) v.findViewById(R.id.npNameDisplay);
            nonProfitName.setText(npName);
            //update profile picture
            ImageView nonProfitPic = (ImageView) v.findViewById(R.id.profilePic);
//        if (profilePicName.equals("") || profilePicName == null) {
//            profilePicName = "blank_profile_picture";
//        }
            Glide.with(nonprofitFragment.this).load(storageReference).into(nonProfitPic);
            //update description
            TextView nonProfitDescription = (TextView) v.findViewById(R.id.profilePart);
            String s = npDesc + "\n\n Address: " + getAddressAsString() + "\nPhone number: " + phoneNum + "\nEmail Address: " + emailAddress;
            nonProfitDescription.setText(s);
        }

        private String getAddressAsString() {
            return address; //unitNum + " " +  sName + ", " + city + ", " + province + ", " + postalCode;
        }

        public void openWebsite(View view) {
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
            if (phoneNum.equals("")) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "This organization does not have a phone number available.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent callInt = new Intent(Intent.ACTION_VIEW);
                callInt.setData(Uri.parse("tel:" + phoneNum));
                startActivity(callInt);
            }
        }

        //this method allows the user to send an email to the organization
        public void sendEmail(View view) {
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
        public void openMap(View view) {
            if (address.equals("")) {
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
