package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.R;
import com.example.a4p02app.updateProfile;
import com.example.a4p02app.userData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class postDetailsFragment extends Fragment {

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
    String postTitle;
    String postDesc;
    String postItem;
    String postTime;
    String postDrop;
    String passedUID;
    String passedPostID;
    private View v;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button linkNPO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_post_details, container, false);





        linkNPO = (Button) v.findViewById(R.id.linkToNPO);


        passedUID = getArguments().getString("UID");
        passedPostID = getArguments().getString("authid");
        System.out.println(passedUID + "---------------------------ID-----------------");

        getFromDatabase(passedUID, passedPostID);

        linkNPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putString("UID", passedUID);
                MainActivity.nonprofitFrag.setArguments(args);
                fragmentTransaction
                        .replace(R.id.fragment_container, MainActivity.nonprofitFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });


        /*String uid = userData.getInstance().getUID();
        /*DocumentReference fav_reference = userData.getInstance()
                .getDocRef().collection("favourites").document();

        db.collectionGroup("accounts")
                .whereEqualTo("")
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

         */
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

    public void getFromDatabase(String passedUID, String passedPostID) {
        DocumentReference docRef = db.collection("accounts/"+passedUID+"/post")
                .document(passedPostID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        // Log.d("TAG: ", "DocumentSnapshot data: " + document.getData());
                        postTitle = document.getString("title");
                        postDesc = document.getString("description");
                        postItem = document.getString("item");
                        postTime = document.getTimestamp("post_date").toDate().toString();
                        postDrop = document.getString("dropoff_location");
                        //profilePicName = document.getString("profilePic");
                        //webURL = document.getString("if_npo_url");
                        //address = document.getString("address");
                        //phoneNum = document.getString("phoneNumber");
                        //emailAddress = document.getString("user_email");
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



    //this method updates the page to match the post's info
    public void setInfo() {
        //update post Details
        TextView pTitle = (TextView) v.findViewById(R.id.postTitle);
        pTitle.setText(postTitle);

        TextView pDesc = (TextView) v.findViewById(R.id.postDesc);
        pDesc.setText(postDesc);

        TextView pDrop = (TextView) v.findViewById(R.id.dropoff);
        pDrop.setText(postDrop);

        TextView pItem = (TextView) v.findViewById(R.id.item);
        pItem.setText(postItem);

        //postDate as well

        //update profile picture
        //ImageView nonProfitPic = (ImageView) v.findViewById(R.id.profilePic);
//        if (profilePicName.equals("") || profilePicName == null) {
//            profilePicName = "blank_profile_picture";
//        }
        //Glide.with(nonprofitFragment.this).load(storageReference).into(nonProfitPic);
        //update description

        //String s = npDesc + "\n\n Address: " + getAddressAsString() + "\nPhone number: " + phoneNum + "\nEmail Address: " + emailAddress;
        //nonProfitDescription.setText(s);
    }

}