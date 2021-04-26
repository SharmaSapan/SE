package com.example.a4p02app.fragments;

import android.app.Fragment;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.NPOAdapter;
import com.example.a4p02app.NPOlist;
import com.example.a4p02app.R;
import com.example.a4p02app.donations;
import com.example.a4p02app.makePost;
import com.example.a4p02app.messages;
import com.example.a4p02app.nonProfit;
import com.example.a4p02app.profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class favouriteFragment extends Fragment {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private String userID;
    private View v;

    List<String> postList = new ArrayList<String>();
    List<String> nameList = new ArrayList<String>();
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);
    List<String> dateList = new ArrayList<String>();

    RecyclerView fList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favourites, container, false);
        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        userID = String.valueOf(activeUser);
        getPostsFromDatabase();

        return v;
    }

    public void getPostsFromDatabase () {
        //Retrieve a list of all favourited items connected to the currently logged in user
        db.collection("accounts/"+userID+"/favourites")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        fList = v.findViewById(R.id.flist);
        NPOAdapter myAdapter = new NPOAdapter(getActivity(), nameList, postPic);

        fList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        fList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fList.setAdapter(myAdapter);
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
            public boolean onQueryTextChange(String newText) {return false; }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out_button){
            getActivity().finish();
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
