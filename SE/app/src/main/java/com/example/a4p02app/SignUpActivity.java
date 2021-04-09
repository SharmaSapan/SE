package com.example.a4p02app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.a4p02app.data.Firestore;
import com.example.a4p02app.data.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtConfirm;
    private Button btnSignup;
    private String[] accountTypes = {"Personal", "Business"};
    private Spinner accountSpinner;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        accountSpinner = findViewById(R.id.accountSpinner);
        btnSignup = findViewById(R.id.btnSignUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirm);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accountTypes);
        accountSpinner.setAdapter(adapter);

        btnSignup.setOnClickListener(v -> {
            try {
                firebaseUserSignup();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * Create an entry in firebase auth and in firestore for a new user
     */
    private void firebaseUserSignup() {
        String email = txtEmail.getText().toString();
        String pass = txtPassword.getText().toString();
        String confirm = txtConfirm.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty() && !confirm.isEmpty()){
            if (pass.equals(confirm)) {

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Firestore.addAccount(email, user, accountSpinner.getSelectedItemPosition());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
