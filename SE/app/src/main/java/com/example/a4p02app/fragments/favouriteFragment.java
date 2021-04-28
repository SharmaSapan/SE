package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.NPO;
import com.example.a4p02app.NPOdapter;
import com.example.a4p02app.Post;
import com.example.a4p02app.PostAdapter;
import com.example.a4p02app.R;
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

public class favouriteFragment extends Fragment implements NPOdapter.RowClickListener{

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private String userID;
    private View v;

    NPOdapter npoAdapter;
    ArrayList<NPO> favs;
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);

    RecyclerView fList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favourites, container, false);
        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        userID = String.valueOf(activeUser);
        favs = new ArrayList<>();
        getPostsFromDatabase();

        return v;
    }

    public void getPostsFromDatabase () {
        //Retrieve a list of all favourited items connected to the currently logged in user
        db.collection("accounts/"+userID+"/favourites")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        favs.clear();
                        NPO npotest = new NPO();
                        npotest.setName("Habitat For Humanity");
                        favs.add(npotest);
                        for(DocumentSnapshot snapshot: value) {
                            NPO npo = new NPO();
                            npo.setName(snapshot.getId());
                            //npo.setName(snapshot.getString("pWriter")); might need if name becomes field
                            favs.add(npo);
                            System.out.println(npo.getName()+"-------------------------------");
                        }
                    }
                });
        fList = v.findViewById(R.id.favslist);
        npoAdapter = new NPOdapter(favs, postPic, this);
        fList.setAdapter(npoAdapter);

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

    @Override
    public void onRowClick(String favNPO) {
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("name", favNPO);
        MainActivity.nonprofitFrag.setArguments(args);
        fragmentTransaction
                .replace(R.id.fragment_container, MainActivity.nonprofitFrag)
                .addToBackStack(null)
                .commit();

    }
}
