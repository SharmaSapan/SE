package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.a4p02app.MainActivity;
import com.example.a4p02app.R;
import com.example.a4p02app.updateProfile;
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

    private Button btnEdit;
    private Button btnDonations;
    private Button btnMessages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        System.out.println("User ID: " + userData.getInstance().getUID());

        System.out.println(userData.getInstance().getUser_first());

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

        profilePart2.setText(combine);

        btnEdit = (Button) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), updateProfile.class);
                startActivity(intent);
            }
        });

        btnDonations = v.findViewById(R.id.donations);
        btnDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "For Future Implementation", Toast.LENGTH_SHORT).show();
            }
        });

        btnMessages = v.findViewById(R.id.messages);
        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "For Future Implementation", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
}
