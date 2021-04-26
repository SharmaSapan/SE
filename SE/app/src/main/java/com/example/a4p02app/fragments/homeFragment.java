package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

import com.example.a4p02app.ChatActivity;
import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.Post;
import com.example.a4p02app.PostAdapter;
import com.example.a4p02app.R;
import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<Post> postList;
    List<String> nameList;
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList;
    //Context con;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeview = inflater.inflate(R.layout.fragment_home, container, false);

       // nameList = new ArrayList<>();
        postList = new ArrayList<>();
       // dateList = new ArrayList<>();

        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
        fsdb.collection("posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            //adds all current field in firestore to the lists
            @Override
            public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int i=0;
                postList.clear();

                for(DocumentSnapshot snapshot: value){
                    Post post = new Post();
                    post.setContent(snapshot.getString("pContent"));
                    post.setName(snapshot.getString("pWriter"));
                    post.setDate(Objects.requireNonNull(snapshot.getTimestamp("pDate")).toDate().toString());

                    //System.out.println(post.getName());
                    postList.add(post);
                    System.out.println(postList.get(i).getName());
                    i++;
                    //postList.add(val.getString("pContent"));
                }
                //nameList.clear();
               // for(DocumentSnapshot snapshot: value) {
                    //post.setName(snapshot.getString("pWriter"));
                    //nameList.add(snapshot.getString("pWriter"));
                //}
                //dateList.clear();
                //for(DocumentSnapshot snapshot: value){
                    //post.setDate(Objects.requireNonNull(snapshot.getTimestamp("pDate")).toDate().toString());
                    //dateList.add(Objects.requireNonNull(snapshot.getTimestamp("pDate")).toDate().toString());
                //}
               // System.out.println(postList.get(0).getName());
                //System.out.println(postList.get(1));
                //System.out.println(postList.get(2));
                //System.out.println(postList.get(3));
                //System.out.println(postList.get(4));
                plist = homeview.findViewById(R.id.plist);
                pAdapter = new PostAdapter(postList, postPic); //change to nameList <--> manualPostList
                plist.setAdapter(pAdapter);
            }
        });
        /*System.out.println(postList.get(0).getName());
        //System.out.println(postList.get(1));
        //System.out.println(postList.get(2));
        //System.out.println(postList.get(3));
        //System.out.println(postList.get(4));
        plist = homeview.findViewById(R.id.plist);
        pAdapter = new PostAdapter(postList, postPic); //change to nameList <--> manualPostList
        plist.setAdapter(pAdapter);*/

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

        //Search
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings){
            return true;
        }
        else if (item.getItemId() == R.id.log_out_button){
            getActivity().finish();
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


