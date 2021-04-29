package com.example.a4p02app;
/**
 * Custom object class using singleton pattern to get data of logged in user
 * Instead of calling same methods again and again. Setter and getter methods is used to update and read data
 * Data that can be used: UID, documentReference of logged in user, address, email, phone, user_first, user_last, user_privilege,
 * npo_desc, npo_name, npo_url
 * to access properties of document use: userData.getInstance().getDocRef().
 * UID cannot be changed
 * to get something use: userData.getInstance().getPhone();
 * If you need to update and get data somewhere use updateData() method
 */

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;


public class userData {
    private static userData instance;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;
    DocumentReference userDocument;

    private String address_city;
    private String address_postal;
    private String address_province;
    private String address_street;
    private String address_unit;
    private String email;
    private String phone;
    private String user_first;
    private String user_last;
    private String user_privilege;
    private String npo_desc;
    private String npo_name;
    private String npo_url;
    private String id;
    private String image_path;

    // to get single instance of the class and prevent other classes to create an instance
    public static synchronized userData getInstance(){
        if (null == instance) {
            instance = new userData();
        }
        return instance;
    }

    private userData(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = mAuth.getCurrentUser();
        UID = user.getUid();

        userDocument = db.collection("accounts").document(UID);
        userDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    address_city = snapshot.getString("address_city");
                    address_postal = snapshot.getString("address_postal");
                    address_province = snapshot.getString("address_province");
                    address_street = snapshot.getString("address_street");
                    address_unit = snapshot.getString("address_unit");
                    email = snapshot.getString("user_email");
                    phone = snapshot.getString("phoneNumber");
                    user_first = snapshot.getString("user_first_name");
                    user_last = snapshot.getString("user_last_name");
                    user_privilege = snapshot.getString("user_privilege");
                    npo_desc = snapshot.getString("if_npo_desc");
                    npo_name = snapshot.getString("if_npo_name");
                    npo_url = snapshot.getString("if_npo_url");
                    id = snapshot.getString("UID");
                    image_path = snapshot.getString("image_path");
                }
                else System.out.println("No document at user data fetch");
            }
        });
    }

    public void updateData(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
        userDocument = db.collection("accounts").document(UID);
        userDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    address_city = snapshot.getString("address_city");
                    address_postal = snapshot.getString("address_postal");
                    address_province = snapshot.getString("address_province");
                    address_street = snapshot.getString("address_street");
                    address_unit = snapshot.getString("address_unit");
                    email = snapshot.getString("user_email");
                    phone = snapshot.getString("phoneNumber");
                    user_first = snapshot.getString("user_first_name");
                    user_last = snapshot.getString("user_last_name");
                    user_privilege = snapshot.getString("user_privilege");
                    npo_desc = snapshot.getString("if_npo_desc");
                    npo_name = snapshot.getString("if_npo_name");
                    npo_url = snapshot.getString("if_npo_url");
                    id = snapshot.getString("UID");
                    image_path = snapshot.getString("image_path");
                }
                else System.out.println("No document at user data fetch");
            }
        });
    }

    public String getUID() {
        return UID;
    }

    public void addField(String field, Object value){
        userDocument.update(field, value);
    }

    public DocumentReference getDocRef() {
        return userDocument;
    }

    public String getDocID() {
        return id;
    }

    public void setDocID(String id) {
        userDocument.update("UID", id);
    }

    public String getAddress_city() {
        return address_city;
    }
    public String getImage_path() {
        return image_path;
    }

    public void setAddress_city(String address_city) {
        userDocument.update("address_city", address_city);
    }

    public String getAddress_postal() {
        return address_postal;
    }

    public void setAddress_postal(String address_postal) {
        userDocument.update("address_postal", address_postal);
    }

    public String getAddress_province() {
        return address_province;
    }

    public void setAddress_province(String address_province) {
        userDocument.update("address_province", address_province);
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        userDocument.update("address_street", address_street);
    }

    public String getAddress_unit() {
        return address_unit;
    }

    public void setAddress_unit(String address_unit) {
        userDocument.update("address_unit", address_unit);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        userDocument.update("user_email", email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        userDocument.update("phoneNumber", phone);
    }

    public String getUser_first() {
        return user_first;
    }

    public void setUser_first(String user_first) {
        userDocument.update("user_first_name", user_first);
    }

    public String getUser_last() {
        return user_last;
    }

    public void setUser_last(String user_last) {
        userDocument.update("user_last_name", user_last);
    }

    public String getUser_privilege() {
        return user_privilege;
    }

    public void setUser_privilege(String user_privilege) {
        userDocument.update("user_privilege", user_privilege);
    }

    public String getNpo_desc() {
        return npo_desc;
    }

    public void setNpo_desc(String npo_desc) {
        userDocument.update("if_npo_desc", npo_desc);
    }

    public String getNpo_name() {
        return npo_name;
    }

    public void setNpo_name(String npo_name) {
        userDocument.update("if_npo_name", npo_name);
    }

    public String getNpo_url() {
        return npo_url;
    }

    public void setNpo_url(String npo_url) {
        userDocument.update("if_npo_url", npo_url);
    }
}
