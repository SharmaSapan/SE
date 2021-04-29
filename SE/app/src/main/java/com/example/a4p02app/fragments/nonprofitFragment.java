package com.example.a4p02app.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.NPO;
import com.example.a4p02app.R;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4p02app.userData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//the default profile picture is from https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/

public class nonprofitFragment extends Fragment {

    private String passedUID;
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
    private ImageButton btnAddtoFavs;
    private ImageButton btnRemFromFavs;

    //get an instance of Firebase so that the firestore database can be used
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_non_profit, container, false);
        super.onCreate(savedInstanceState);


        passedUID = getArguments().getString("UID");
        System.out.println(passedUID + "---------------------------ID-----------------");
        String uid = userData.getInstance().getUID();
        DocumentReference fav_reference = userData.getInstance()
                .getDocRef().collection("favourites").document();
        db.collection("accounts/"+uid+"/favourites")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentSnapshot snapshot : value) {
                            if(Objects.equals(snapshot.getString("UID"), passedUID)){
                                showIsFav();
                                break;
                            }else{
                                addToFav();
                            }
                        }
                    }
                });
        //if(passedUID){


        //}


        getFromDatabase(passedUID);
        setInfo();
        btnRemFromFavs= v.findViewById(R.id.btnRemFromFavs);
        btnAddtoFavs = v.findViewById(R.id.btnAddtoFavs);
        btnAddtoFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DocumentReference fav_reference = userData.getInstance()
                            .getDocRef().collection("favourites").document();
                    //tries to access the documents in the favourites collection
                    Map<String, Object> fav_data = new HashMap<>();
                    fav_data.put("UID", passedUID);
                    fav_data.put("name", npName);
                    fav_reference.set(fav_data);
                    Toast.makeText(v.getContext(), "Added to Favourites!", Toast.LENGTH_SHORT).show();

                    //NPO npo = new NPO();
                    //npo.setFavourite();
                }

        });
        btnRemFromFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.collection("accounts/"+uid+"/favourites")
                CollectionReference favRef = db.collection("accounts/"+uid+"/favourites");
                Query query = favRef.whereEqualTo("UID", passedUID);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                favRef.document(document.getId()).delete();
                            }
                        }
                    }
                });
                Toast.makeText(v.getContext(), "Removed From Favourites", Toast.LENGTH_SHORT).show();

            }

        });
        return v;
    }

    private void addToFav() {

        btnRemFromFavs.setVisibility(View.GONE);
        btnAddtoFavs.setVisibility(View.VISIBLE);
    }

    private void showIsFav() {
        btnAddtoFavs.setVisibility(View.GONE);
        btnRemFromFavs.setVisibility(View.VISIBLE);

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out_button){
            getActivity().finish();
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
        //this method gets the info from the database
        //not done yet
        //for more info on this, go to https://firebase.google.com/docs/firestore/query-data/get-data
        public void getFromDatabase(String passedUID) {
            DocumentReference docRef = db.collection("accounts").document(passedUID);;
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
                            //profilePicName = document.getString("profilePic");
                            webURL = document.getString("if_npo_url");
                            address = document.getString("address");
                            phoneNum = document.getString("phoneNumber");
                            emailAddress = document.getString("user_email");
                            //update the page
                            setInfo();
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
