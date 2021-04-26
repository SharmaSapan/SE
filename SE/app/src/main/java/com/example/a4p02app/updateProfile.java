package com.example.a4p02app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class updateProfile extends Activity {
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
        setContentView(R.layout.activity_update_profile);


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

        //update name
        TextView first = (TextView) findViewById(R.id.first_name);
        first.setText(first_name);
        TextView last = (TextView) findViewById(R.id.last_name);
        first.setText(last_name);
        //update address
        TextView unit = (TextView) findViewById(R.id.unitNumber);
        unit.setText(unitNum);
        TextView street = (TextView) findViewById(R.id.streetName);
        street.setText(sName);
        TextView cV = (TextView) findViewById(R.id.city);
        cV.setText(city);
        TextView pCode = (TextView) findViewById(R.id.postalCode);
        pCode.setText(postalCode);
        //update email
        TextView emailAddr = (TextView) findViewById(R.id.email);
        emailAddr.setText(emailAddress);
        //update phone number
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNum);
        phoneNumber.setText(phoneNum);

    }
    //makes the changes to the databases
    public void makeChanges(View view) {
        //update name
        TextView first = (TextView) findViewById(R.id.first_name);
        TextView last = (TextView) findViewById(R.id.last_name);
        TextView unit = (TextView) findViewById(R.id.unitNumber);
        TextView street = (TextView) findViewById(R.id.streetName);
        TextView cV = (TextView) findViewById(R.id.city);
        TextView pCode = (TextView) findViewById(R.id.postalCode);
        TextView emailAddr = (TextView) findViewById(R.id.email);
        TextView phoneNumber = (TextView) findViewById(R.id.phoneNum);

        userData.getInstance().setUser_first(first.getText().toString());
        userData.getInstance().setUser_last(last.getText().toString());
        userData.getInstance().setPhone(phoneNumber.getText().toString());
        userData.getInstance().setAddress_unit(unit.getText().toString());
        userData.getInstance().setAddress_street(street.getText().toString());
        userData.getInstance().setAddress_city(cV.getText().toString());
        userData.getInstance().setAddress_postal(pCode.getText().toString());
        userData.getInstance().setEmail(emailAddr.getText().toString());

        Toast toast = Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT);
        toast.show();
        // update data app wide
        userData.getInstance().updateData();
    }
}
