package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.example.a4p02app.R;
import com.example.a4p02app.donations;
import com.example.a4p02app.messages;
import com.example.a4p02app.userData;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profileFragment extends Fragment {

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
    private String first_name;
    private String last_name;
    private String idtype;
    private String phoneNum;
    private String unitNum;
    private String sName;
    private String city;
    private String province;
    private String postalCode;
    private String emailAddress;

    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_non_profit, container, false);

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

        TextView profilePart2 = (TextView) v.findViewById(R.id.profilePart2);
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

        //profilePart2.setText(combine);

        return v;
    }
}
