package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class GetMoreInfo extends AppCompatActivity {
    String userPrivilege;
    TextInputEditText unit;
    TextInputEditText street;
    TextInputEditText postal;
    TextInputEditText city;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText phone;
    TextInputEditText description;
    TextInputEditText nponame;
    TextInputEditText url;
    Button save;
    TextView np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_info);
        userData.getInstance().updateData();

        unit = findViewById(R.id.unit);
        street = findViewById(R.id.street);
        postal = findViewById(R.id.postal);
        city = findViewById(R.id.city);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        nponame = findViewById(R.id.nponame);
        url = findViewById(R.id.url);
        np = findViewById(R.id.textView5);
        userPrivilege = userData.getInstance().getUser_privilege();

        if (userPrivilege == "npo") {
            np.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            nponame.setVisibility(View.VISIBLE);
            url.setVisibility(View.VISIBLE);
        }
        else if (userPrivilege == "donor") {
            np.setVisibility(View.INVISIBLE);
            description.setVisibility(View.INVISIBLE);
            nponame.setVisibility(View.INVISIBLE);
            url.setVisibility(View.INVISIBLE);
        }

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if statement to ensure none of the fields are empty
                if (city.getText().toString().isEmpty() || street.getText().toString().isEmpty() || postal.getText().toString().isEmpty() ||
                        firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty()|| phone.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();

                } else {
                    DocumentReference post_reference = userData.getInstance().getDocRef();
                    Map<String, Object> create_details = new HashMap<>();
                    create_details.put("user_privilege", userData.getInstance().getUser_privilege());
                    create_details.put("user_email", userData.getInstance().getEmail());
                    create_details.put("address_unit", unit.getText().toString());
                    create_details.put("address_street", street.getText().toString());
                    create_details.put("address_postal", postal.getText().toString());
                    create_details.put("address_province", "ON");
                    create_details.put("address_city", city.getText().toString());
                    create_details.put("user_first_name", firstname.getText().toString());
                    create_details.put("user_last_name", lastname.getText().toString());
                    create_details.put("phoneNumber", phone.getText().toString());
                    create_details.put("UID", userData.getInstance().getUID());
                    if (userPrivilege == "npo") {
                        create_details.put("if_npo_desc", description.getText().toString());
                        create_details.put("if_npo_name", nponame.getText().toString());
                        create_details.put("if_npo_url", url.getText().toString());
                    }
                    post_reference.set(create_details);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}