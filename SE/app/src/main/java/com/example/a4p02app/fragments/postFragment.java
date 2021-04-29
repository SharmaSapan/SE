package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.a4p02app.MainActivity;
import com.example.a4p02app.R;
import com.example.a4p02app.imageHandler;
import com.example.a4p02app.userData;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class postFragment extends Fragment {

    Button Post;
    EditText description;
    EditText dropoff_location;
    EditText item;
    EditText tags;
    EditText title;
    Button Upload;

    private final int PICK_IMAGE_REQUEST = 71;
    Uri filePath;

    View v;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            imageHandler im = new imageHandler(filePath);
            im.uploadProfile();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_make_post, container, false);

        title = v.findViewById(R.id.Title);
        description = v.findViewById(R.id.Description);
        item = v.findViewById(R.id.Item);
        dropoff_location = v.findViewById(R.id.DropoffLocation); // fill user location

        tags = v.findViewById(R.id.Tags);
        Post = v.findViewById(R.id.Post);
        Upload = v.findViewById(R.id.Upload);
        Date currentTime = Calendar.getInstance().getTime();

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if statement to ensure none of the fields are empty
                if (description.getText().toString().isEmpty() || dropoff_location.getText().toString().isEmpty() || item.getText().toString().isEmpty() ||
                        tags.getText().toString().isEmpty() || title.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity().getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();

                } else {

                    //when user clicks on post button it creates a new document and add data to it
                    DocumentReference post_reference = userData.getInstance().getDocRef().collection("post").document();//tries to access the documents in the post collection
                    Map<String, Object> post_data = new HashMap<>();

                    post_data.put("author_id", userData.getInstance().getUID());
                    post_data.put("description", description.getText().toString());
                    post_data.put("dropoff_location", dropoff_location.getText().toString()); //
                    post_data.put("item", item.getText().toString());
                    post_data.put("post_date", currentTime); //
                    post_data.put("post_type", userData.getInstance().getUser_privilege());
                    post_data.put("title", title.getText().toString());
                    post_data.put("tags", tags.getText().toString());
                    if (filePath!=null){
                        post_data.put("image_path", filePath.getLastPathSegment().toString());
                    }
                    if (userData.getInstance().getUser_privilege() == "npo") {
                        post_data.put("if_npo_phone", userData.getInstance().getPhone());
                        post_data.put("if_npo_url", userData.getInstance().getNpo_url());
                    }
                    post_reference.set(post_data);
                    title.getText().clear();
                    description.getText().clear();
                    item.getText().clear();
                    dropoff_location.getText().clear();
                    tags.getText().clear();
                    Toast.makeText(getActivity().getApplicationContext(), "Post uploaded!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        return v;
    }


}

