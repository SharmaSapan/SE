package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profile extends AppCompatActivity {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
    String first_name;
    String last_name;
    String idtype;
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
        setContentView(R.layout.activity_profile);

        first_name = userData.getInstance().getUser_first();
        last_name = userData.getInstance().getUser_last();
        idtype = userData.getInstance().getUser_privilege();
        phoneNum = userData.getInstance().getPhone();
        unitNum = userData.getInstance().getAddress_unit();
        sName = userData.getInstance().getAddress_street();
        city = userData.getInstance().getAddress_city();
        province = userData.getInstance().getAddress_province();
        postalCode = userData.getInstance().getAddress_postal();
        emailAddress = userData.getInstance().getEmail();

        TextView profilePart2 = (TextView) findViewById(R.id.profilePart2);
        String combine = first_name +"\n"+
                last_name+"\n"+
                idtype+"\n"+
                phoneNum+"\n"+
                unitNum+"\n"+
                sName+"\n"+
                city+"\n"+
                province+"\n"+
                postalCode+"\n"+
                emailAddress;
        profilePart2.setText(combine);


    }


    public void goBack(View view) {
        //go back to the previous activity
        finish();
    }
    public void goDonos(View view) {//will go to users completed donations
        Intent intent = new Intent(this, donations.class);
        startActivity(intent);
    }
    public void goMessages(View view) {//will go to users sent messages
        Intent intent = new Intent(this, messages.class);
        startActivity(intent);
    }

    public void goHome(View view) {//will go to Home page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    public void goInfo(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, nonProfit.class);
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
    public void goUpdateProfile(View view) {//will go to Users favourited non-profits
        Intent intent = new Intent(this, updateProfile.class);
        startActivity(intent);
    }
}