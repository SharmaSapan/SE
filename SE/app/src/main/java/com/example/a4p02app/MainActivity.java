package com.example.a4p02app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a4p02app.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private View view;

    //List<String> postList;
    //List<String> nameList;
    //List<Integer> postPic;
    //List<String> dateList;

    RecyclerView pList;
    PostAdapter myAdapter;
    List<String> postList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
    R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();


        FirebaseAuth.getInstance().signOut();

        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        if (activeUser == null) {
            goLogin();
        }
        else {
            goHome(view);
        }

        setContentView(R.layout.activity_main);


        fsdb.collection("posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            //adds all current field in firestore to the lists
            @Override
            public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                postList.clear();
                for(DocumentSnapshot val: value){
                    postList.add(val.getString("pContent"));
                }
                nameList.clear();
                for(DocumentSnapshot snapshot: value) {
                    nameList.add(snapshot.getString("pWriter"));
                }
                dateList.clear();
                for(DocumentSnapshot snapshot: value){
                    dateList.add(Objects.requireNonNull(snapshot.getTimestamp("pDate")).toDate().toString());
                }

            }
        });

        pList = findViewById(R.id.plist);
        myAdapter = new PostAdapter(this, nameList, postList, dateList, postPic);

        pList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        pList.setLayoutManager(new LinearLayoutManager(this));
        pList.setAdapter(myAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//searchbar implementation

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_action);
        SearchView sv = (SearchView) item.getActionView();

        sv. setImeOptions(EditorInfo.IME_ACTION_DONE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchThrough(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;//super.onCreateOptionsMenu(menu);
    }




    public void goHome(View view) {//reloads Home page since user is at home page already
        setContentView(R.layout.activity_main);

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

    public void makePost(View view) {//will bring user to post writing page
        Intent intent = new Intent(this, makePost.class);
        startActivity(intent);
    }

    public void goLogin() {//brings user to the login page
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }


    public void updateUser(FirebaseUser activeUser){
        this.activeUser = activeUser;
    }
}