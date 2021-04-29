package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.R;
import com.example.a4p02app.updateProfile;
import com.example.a4p02app.userData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profileFragment extends Fragment {

    private StorageReference storageReference;
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
    private ImageView newpic;
    private ImageView pic;
    private Button btnEdit;
    private Button btnDonations;
    private Button btnMessages;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profileAddress;
    private String path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_profile, container, false);
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
        // image handler
        pic = v.findViewById(R.id.pic);
        path = userData.getInstance().getUID()+"/"+userData.getInstance().getImage_path();
        storageReference = FirebaseStorage.getInstance().getReference().child(path);
        Glide.with(getActivity().getApplicationContext()).load(storageReference).into(pic);

        profileName = v.findViewById(R.id.profileName);
        String combine = first_name +" "+ last_name;
        profileName.setText(combine);

        profileEmail = v.findViewById(R.id.profileEmail);
        String combineE = emailAddress + "\n" + phoneNum;
        profileEmail.setText(combineE);

        profileAddress = v.findViewById(R.id.profileAddress);
        String combineA = unitNum + " " + sName + "\n" + city + " " + province + "\n" + postalCode;
        profileAddress.setText(combineA);

                /*idtype+"\n"+
                phoneNum+"\n"+
                unitNum+"\n"+
                sName+"\n"+
                city+"\n"+
                province+"\n"+
                postalCode+"\n"+
                emailAddress;*/



        btnEdit = v.findViewById(R.id.btnEdit);
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

}
