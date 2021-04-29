package com.example.a4p02app.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.NPO;
import com.example.a4p02app.NPOdapter;
import com.example.a4p02app.Post;
import com.example.a4p02app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NPO_ListFragment extends Fragment implements NPOdapter.RowClickListener{

    private View npoListview;
    RecyclerView npolist;
    NPOdapter npoAdapter;
    ArrayList<NPO> NPOs;
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon,
            R.drawable.blank_profile_picture,R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        npoListview = inflater.inflate(R.layout.fragment_npo_list, container, false);

        NPOs = new ArrayList<>();
        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
        fsdb.collection("accounts")
                .whereEqualTo("user_privilege","npo")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            //adds all current field in firestore to the lists
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                
                NPOs.clear();
                for(DocumentSnapshot snapshot: value) {
                    NPO npo = new NPO();
                    npo.setUID(snapshot.getId());
                    npo.setName(snapshot.getString("if_npo_name"));
                    NPOs.add(npo);
                }
                NPOs.sort(Comparator.comparing(NPO::getName));//sort by alphabetical
            }
        });
        npolist = npoListview.findViewById(R.id.npolist);
        npoAdapter = new NPOdapter(NPOs, postPic, this);
        npolist.setAdapter(npoAdapter);


        return npoListview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Donation Machine");

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
                npoAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    //Override All Existing Menu Options*
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
    public void onRowClick(String uid) {
        //Fragment fragment = nonprofitFragment.newInstance(npo);

        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("UID", uid);
        MainActivity.nonprofitFrag.setArguments(args);
        fragmentTransaction
                .replace(R.id.fragment_container, MainActivity.nonprofitFrag)
                .addToBackStack(null)
                .commit();
    }
}