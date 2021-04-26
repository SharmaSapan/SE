package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a4p02app.MainActivity;
import com.example.a4p02app.NPOlist;
import com.example.a4p02app.R;
import com.example.a4p02app.donations;
import com.example.a4p02app.favourites;
import com.example.a4p02app.messages;
import com.example.a4p02app.nonProfit;
import com.example.a4p02app.profile;
import com.example.a4p02app.userData;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class postFragment extends Fragment {

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

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final String UID = userData.getInstance().getUID();; //gets current users' id

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_make_post, container, false);

        title = (EditText) v.findViewById(R.id.Title);
        description = (EditText) v.findViewById(R.id.Description);
        item = (EditText) v.findViewById(R.id.Item);
        dropoff_location = (EditText) v.findViewById(R.id.DropoffLocation); // fill user location
        // generate geo stamp from the user provided location
        // get time from system post_date
        tags = (EditText) v.findViewById(R.id.Tags);
        Post = (Button) v.findViewById(R.id.Post);


        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if statement to ensure none of the fields are empty
                if (author_id.getText().toString().isEmpty() || description.getText().toString().isEmpty() || dropoff_location.getText().toString().isEmpty()
                        || item.getText().toString().isEmpty() || location_geostamp.getText().toString().isEmpty() || post_type.getText().toString().isEmpty() ||
                        post_date.getText().toString().isEmpty() || tags.getText().toString().isEmpty() || title.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity().getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();

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

        return v;
    }
}

