package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.PostAdapter;
import com.example.a4p02app.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class homeFragment extends Fragment {


    private View homeview;
    RecyclerView plist;
    PostAdapter pAdapter;
    List<String> manualPostList;
    List<String> postList;
    List<String> nameList;
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList;
    //Context con;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeview = inflater.inflate(R.layout.fragment_home, container, false);

        manualPostList = new ArrayList<>();
        manualPostList.add("Doctors Without Borders");
        manualPostList.add("The Humane Society");
        manualPostList.add("Habitat for Humanity");
        manualPostList.add("United Way");
        manualPostList.add("Niagara Health Foundation");
        manualPostList.add("Red Roof Retreat");
        manualPostList.add("Goodwill Niagara");
        manualPostList.add("Village of Hope");
        manualPostList.add("Alzheimer Society");
        manualPostList.add("Community Living");
        manualPostList.add("Autism Society Ontario Niagara Region");


        nameList = new ArrayList<>();
        postList = new ArrayList<>();
        dateList = new ArrayList<>();
        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
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
        plist = homeview.findViewById(R.id.plist);
        pAdapter = new PostAdapter(nameList,postList,dateList,postPic); //change to nameList <--> manualPostList
        plist.setAdapter(pAdapter);

        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(con, DividerItemDecoration.VERTICAL);
        //plist.addItemDecoration(dividerItemDecoration);
//dividers not working in fragment

        return homeview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }




   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }*/
}


