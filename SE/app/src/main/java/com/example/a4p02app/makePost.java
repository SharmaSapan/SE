package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class makePost extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //gets current user
    final String UID = user.getUid(); //gets current users' id
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        author_id = (EditText) findViewById(R.id.Author_id);
        description = (EditText) findViewById(R.id.Description);
        dropoff_location = (EditText) findViewById(R.id.DropoffLocation);
        item = (EditText) findViewById(R.id.Item);
        location_geostamp = (EditText) findViewById(R.id.LocationGeoStamp);
        post_type = (EditText) findViewById(R.id.PostType);
        post_date = (EditText) findViewById(R.id.PostDate);
        tags = (EditText) findViewById(R.id.Tags);
        title = (EditText) findViewById(R.id.Title);
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

                DocumentReference post_reference = db.collection("test").document(UID).collection("post").document(); //when user clicks on post button
                //tries to access the documents in the post collection
                post_reference.update("Author id", author_id.getText().toString());
                post_reference.update("Description", description.getText().toString());
                post_reference.update("Dropoff Location", dropoff_location.getText().toString());
                post_reference.update("Item", item.getText().toString());
                post_reference.update("Location Geo Stamp", location_geostamp.getText().toString());
                post_reference.update("Post Date", post_date.getText().toString());
                post_reference.update("Post Type", post_type.getText().toString());
                post_reference.update("Title", title.getText().toString());
                post_reference.update("Tags", tags.getText().toString());

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

