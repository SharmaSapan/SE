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
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4p02app.LoginActivity;
import com.example.a4p02app.MainActivity;
import com.example.a4p02app.NPOdapter;
import com.example.a4p02app.PostAdapter;
import com.example.a4p02app.R;
import com.example.a4p02app.donations;
import com.example.a4p02app.messages;
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

public class NPO_ListFragment extends Fragment implements NPOdapter.RowClickListener{

    private View npoListview;
    RecyclerView npolist;
    NPOdapter npoAdapter;
    List<String> NPOs;
    List<Integer> postPic = Arrays.asList(R.drawable.app_icon,R.drawable.blank_profile_picture,
            R.drawable.app_icon,R.drawable.blank_profile_picture,R.drawable.app_icon);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        npoListview = inflater.inflate(R.layout.fragment_npo_list, container, false);

        NPOs = new ArrayList<>();
        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
        fsdb.collection("nonprofits").addSnapshotListener(new EventListener<QuerySnapshot>() {
            //adds all current field in firestore to the lists
            @Override
            public void onEvent( QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                
                NPOs.clear();
                for(DocumentSnapshot snapshot: value) {
                    NPOs.add(snapshot.getId());
                }
            }
        });
        npolist = npoListview.findViewById(R.id.npolist);
        npoAdapter = new NPOdapter(NPOs, postPic, this);
        npolist.setAdapter(npoAdapter);

        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(con, DividerItemDecoration.VERTICAL);
        //plist.addItemDecoration(dividerItemDecoration);
//dividers not working in fragment, to figure out

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
    public void onRowClick(String npo) {
        //Fragment fragment = nonprofitFragment.newInstance(npo);

        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("name", npo);
        MainActivity.nonprofitFrag.setArguments(args);
        fragmentTransaction
                .replace(R.id.fragment_container, MainActivity.nonprofitFrag)
                .addToBackStack(null)
                .commit();
    }
}