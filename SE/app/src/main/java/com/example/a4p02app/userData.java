package com.example.a4p02app;
/**
 * Custom object class using singleton pattern to get data of logged in user
 * Instead of calling same methods again and again. Setter and getter methods is used to update and read data
 * Data that can be used: UID, documentReference of logged in user, address, email, phone, user_first, user_last, user_privilege,
 * npo_desc, npo_name, npo_url
 * UID cannot be changed
 * to get something use: userData.getInstance().getPhone();
 */
//TODO: update setters if needed or else delete
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class userData {
    private static userData instance;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UID = user.getUid();
    DocumentReference userDocument = db.collection("test").document(UID);

    private Object address;
    private String email;
    private String phone;
    private String user_first;
    private String user_last;
    private String user_privilege;
    private String npo_desc;
    private String npo_name;
    private String npo_url;

    // to get single instance of the class and prevent other classes to create an instance
    public static synchronized userData getInstance(){
        if (null == instance) {
            instance = new userData();
        }
        return instance;
    }

    private userData(){
        userDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    address = snapshot.get("address");
                    email = snapshot.getString("user_email");
                    phone = snapshot.getString("user_phone");
                    user_first = snapshot.getString("user_first");
                    user_last = snapshot.getString("user_last");
                    user_privilege = snapshot.getString("user_privilege");
                    npo_desc = snapshot.getString("npo_desc");
                    npo_name = snapshot.getString("npo_name");
                    npo_url = snapshot.getString("npo_url");
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        userDocument.update("address", address);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        userDocument.update("email", email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        userDocument.update("phone", phone);
    }

    public String getUser_first() {
        return user_first;
    }

    public void setUser_first(String user_first) {
        userDocument.update("user_first", user_first);
    }

    public String getUser_last() {
        return user_last;
    }

    public void setUser_last(String user_last) {
        userDocument.update("user_last", user_last);
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
        userDocument.update("npo_desc", npo_desc);
    }

    public String getNpo_name() {
        return npo_name;
    }

    public void setNpo_name(String npo_name) {
        userDocument.update("npo_name", npo_name);
    }

    public String getNpo_url() {
        return npo_url;
    }

    public void setNpo_url(String npo_url) {
        userDocument.update("npo_url", npo_url);
    }
}
