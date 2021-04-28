package com.example.a4p02app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a4p02app.data.Firestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static FirebaseFirestore db;

    private static final String TAG = "SignupActivity";
    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtConfirm;
    private Button btnSignup;
    private RadioGroup rdoGroup;
    private RadioButton rdoPersonal;
    private RadioButton rdoBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        btnSignup = findViewById(R.id.btnSignUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirm);
        rdoGroup = findViewById(R.id.rdoGroup);
        rdoPersonal = findViewById(R.id.rdoPersonal);
        rdoBusiness = findViewById(R.id.rdoBusiness);


        btnSignup.setOnClickListener(v -> {
            try {
                firebaseUserSignup();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        rdoPersonal.setChecked(true);
    }

    /**
     * Create an entry in firebase auth and in firestore for a new user
     */
    private void firebaseUserSignup() {
        db = FirebaseFirestore.getInstance();

        String email = txtEmail.getText().toString();
        String pass = txtPassword.getText().toString();
        String confirm = txtConfirm.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty() && !confirm.isEmpty()){
            if (pass.equals(confirm)) {

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");

                                int accountType = 0;
                                if (!rdoPersonal.isChecked()){
                                    accountType = 1;
                                }

                                FirebaseUser user = mAuth.getCurrentUser();
                                String UID = user.getUid();
                                Map<String, Object> deets = new HashMap<>();
                                deets.put("user_email", email);
                                deets.put("address_unit", "");
                                deets.put("address_street", "");
                                deets.put("address_postal", "");
                                deets.put("address_province", "ON");
                                deets.put("address_city", "");
                                deets.put("user_first_name", "");
                                deets.put("user_last_name", "");
                                deets.put("phoneNumber", "");
                                deets.put("if_npo_desc", "");
                                deets.put("if_npo_name", "");
                                deets.put("if_npo_url", "");
                                deets.put("UID", UID);
                                if (accountType == 0)
                                    deets.put("user_privilege", "donor");
                                if (accountType == 1)
                                    deets.put("user_privilege", "npo");
                                // create an accounts document with userID as unique id
                                db.collection("accounts").document(UID)
                                        .set(deets)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });

                                Intent intent = new Intent(getApplicationContext(), GetMoreInfo.class);
                                startActivity(intent);

                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Log.d(TAG, "createUserWithEmail:failure -> " + errorCode);

                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(this, "Email already in use.", Toast.LENGTH_SHORT).show();

                                else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

                                else if (task.getException() instanceof FirebaseAuthWeakPasswordException)
                                    Toast.makeText(this, "Password should be 8 characters or longer.", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(this, "Error during sign up. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                txtConfirm.clearComposingText();
            }
        }
        else {
            Toast.makeText(this, "Missing fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
