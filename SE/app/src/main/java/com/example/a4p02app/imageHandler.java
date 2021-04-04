package com.example.a4p02app;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/*

// to get user upload from file using upload button
    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

// handle the result from the intent
    Uri filepath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }


        */
// stores image after taking upload from user in firebase storage and creates url for image in firebase accounts document under UID
public class imageHandler extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final String UID = userData.getInstance().getUID();

    Uri filepath;
    public imageHandler(Uri path){
    filepath = path;
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    void uploadProfile() {
        if (filepath != null) {
            StorageReference id_path = storageRef.child(UID + "." + getFileExtension(filepath));
            id_path.putFile(filepath);
            userData.getInstance().getDocRef().update("image_path", id_path);
        }
    }


}
/*
// to get logged in user photo
StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userData.getInstance().getUID());
// ImageView in your Activity
ImageView imageView = findViewById(R.id.imageView);
// Download directly from StorageReference using Glide
Glide.with(context).load(storageReference).into(imageView);
 */
/*
// not sure if we can get photos for adpater during binding so we need snapshot of
 DocumentReference url_location = db.collection("test").document(UID); //TODO: change test to accounts
 // get snapshot of document and then String imageUrl = url_location.getString("image_path");
 Glide.with(context).load(imageUrl).into(imageView);
 */