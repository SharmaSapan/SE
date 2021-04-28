package com.example.a4p02app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class GetMoreInfo extends AppCompatActivity {
    String userPrivilege;
    String email;
    EditText unit;
    EditText street;
    EditText postal;
    EditText city;
    EditText firstname;
    EditText lastname;
    EditText phone;
    EditText description;
    EditText nponame;
    EditText url;
    Button save;
    TextView np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_info);
        unit = (EditText) findViewById(R.id.unit);
        street = (EditText) findViewById(R.id.street);
        postal = (EditText) findViewById(R.id.postal);
        city = (EditText) findViewById(R.id.city);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        description = (EditText) findViewById(R.id.description);
        nponame = (EditText) findViewById(R.id.nponame);
        url = (EditText) findViewById(R.id.url);
        np = (TextView) findViewById(R.id.textView5);
        userPrivilege = userData.getInstance().getUser_privilege();
        description.setVisibility(View.INVISIBLE);
        nponame.setVisibility(View.INVISIBLE);
        url.setVisibility(View.INVISIBLE);
        if (userPrivilege == "npo") {
            np.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            nponame.setVisibility(View.VISIBLE);
            url.setVisibility(View.VISIBLE);
        }
        else if (userPrivilege == "donor") {
            description.setVisibility(View.INVISIBLE);
            nponame.setVisibility(View.INVISIBLE);
            url.setVisibility(View.INVISIBLE);
        }

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DocumentReference post_reference = userData.getInstance().getDocRef();
                Map<String, Object> create_details = new HashMap<>();
                create_details.put("user_email", userData.getInstance().getEmail());
                create_details.put("address_unit", unit.getText().toString());
                create_details.put("address_street", street.getText().toString());
                create_details.put("address_postal", postal.getText().toString());
                create_details.put("address_province", "ON");
                create_details.put("address_city", city.getText().toString());
                create_details.put("user_first_name", firstname.getText().toString());
                create_details.put("user_last_name", lastname.getText().toString());
                create_details.put("phoneNumber", phone.getText().toString());
                if (userPrivilege == "npo") {
                    create_details.put("if_npo_desc", description.getText().toString());
                    create_details.put("if_npo_name", nponame.getText().toString());
                    create_details.put("if_npo_url", url.getText().toString());
                }
                post_reference.set(create_details);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}