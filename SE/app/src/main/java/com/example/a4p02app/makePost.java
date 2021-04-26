package com.example.a4p02app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class makePost extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final String UID = userData.getInstance().getUID();; //gets current users' id


    Button Post;
    EditText author_id;
    EditText description;
    EditText dropoff_location;
    EditText item;
    EditText location_geostamp;
    EditText post_type;
    EditText post_date;
    EditText tags;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        title = (EditText) findViewById(R.id.Title);
        description = (EditText) findViewById(R.id.Description);
        item = (EditText) findViewById(R.id.Item);
        dropoff_location = (EditText) findViewById(R.id.DropoffLocation); // fill user location
        // generate geo stamp from the user provided location
        // get time from system post_date
        tags = (EditText) findViewById(R.id.Tags);
        Post = (Button) findViewById(R.id.Post);

        Post.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //if statement to ensure none of the fields are empty
                if (author_id.getText().toString().isEmpty() || description.getText().toString().isEmpty() || dropoff_location.getText().toString().isEmpty()
                        || item.getText().toString().isEmpty() || location_geostamp.getText().toString().isEmpty() || post_type.getText().toString().isEmpty() ||
                        post_date.getText().toString().isEmpty() || tags.getText().toString().isEmpty() || title.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();

                } else {

                    //when user clicks on post button it creates a new document and add data to it
                    DocumentReference post_reference = userData.getInstance().getDocRef().collection("post").document();//tries to access the documents in the post collection
                    Map<String, Object> post_data = new HashMap<>();

                    post_data.put("author_id", userData.getInstance().getUID());
                    post_data.put("description", description.getText().toString());
                    post_data.put("dropoff_location", dropoff_location.getText().toString()); //
                    post_data.put("item", item.getText().toString());
                    post_data.put("location_geostamp", location_geostamp.getText().toString()); //
                    post_data.put("post_date", post_date.getText().toString()); //
                    post_data.put("post_type", userData.getInstance().getUser_privilege());
                    post_data.put("title", title.getText().toString());
                    post_data.put("tags", tags.getText().toString());
                    if (userData.getInstance().getUser_privilege() == "npo") {
                        post_data.put("if_npo_phone", userData.getInstance().getPhone());
                        post_data.put("if_npo_url", userData.getInstance().getNpo_url());
                    }
                    post_reference.set(post_data);

                }
            }
        });
    }

    public void writePost(View view) {
    }

    public void goBack(View view) {
        //go back to the previous activity
        //if post is being written, ask user if they want to finish or leave page
        finish();
    }

    public void goHome(View view) {//will go to Home page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }
    public void goInfo(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, nonProfit.class);
        startActivity(intent);
    }

    public void goNPOList(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, NPOlist.class);
        startActivity(intent);
    }

    public void goFavs(View view) {//will go to Users favourited non-profits
        Intent intent = new Intent(this, favourites.class);
        startActivity(intent);
    }
    public void mPost(View view) {//will reload post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }
}

