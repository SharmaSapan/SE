package com.example.a4p02app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentContainerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a4p02app.R;
import com.example.a4p02app.fragments.*;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Firebase
    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    //UI Components
    private boolean searcherIsDown;
    private View slidingSearch;
    private View searcher;
    private View closerArrow;
    private View header;
    private View sb;
    private View searchbar;
    private View view;
    ListView aList;
    String[] theList = {"Donate Message 1", "Donate Message 2", "Donate Message 3", "Donate Message 4",
            "Donate Message 5", "Donate Message 6","Donate Message 7","Donate Message 8","Donate Message 9",
            "Donate Message 10"};

    private BottomNavigationView bottomAppBar;
    private Toolbar topAppBar;

    //Window Fragments
    private FragmentManager fragmentManager;
    private donationFragment donationFrag;
    private profileFragment profileFrag;
    private favouriteFragment favFrag;
    private homeFragment homeFrag;
    private nonprofitFragment nonprofitFrag ;
    private postFragment postFrag;
    private infoFragment infoFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseAuth.getInstance().signOut();
        //mAuth = FirebaseAuth.getInstance();
        //activeUser = mAuth.getCurrentUser();

        //if (activeUser == null) {
        //    goLogin();
        //}
        // else {
        startMainActivity();
        // }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.home_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("hi!!!!!!");

        switch (item.getItemId()) {
            case R.id.home:
                changeFragment(1);
                return true;
            case R.id.profile:
                changeFragment(2);
                return true;
            case R.id.info:
                changeFragment(3);
                return true;
            case R.id.favs:
                changeFragment(4);
                return true;
            case R.id.settings:
                changeFragment(5);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void start_activity(){
        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;//starts the searcher off as not visible
        aList = (ListView)findViewById(R.id.homeList); //sets up the array of announcements
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_home_list, R.id.textView_, theList);
        aList.setAdapter(arrayAdapter);
    }


    private void startMainActivity(){
        setContentView(R.layout.activity_manager);

        //Components
        bottomAppBar = findViewById(R.id.bottomAppBar);
        topAppBar = findViewById(R.id.topAppBar);

        //Fragments
        donationFrag = new donationFragment();
        profileFrag = new profileFragment();
        favFrag = new favouriteFragment();
        homeFrag = new homeFragment();
        nonprofitFrag = new nonprofitFragment();
        postFrag = new postFragment();
        infoFrag = new infoFragment();

        fragmentManager = getFragmentManager();
        changeFragment(1);


        //Listeners
        bottomAppBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    changeFragment(1);
                    return true;
                case R.id.profile:
                    changeFragment(2);
                    return true;
                case R.id.info:
                    changeFragment(3);
                    return true;
                case R.id.favs:
                    changeFragment(4);
                    return true;
                case R.id.settings:
                    changeFragment(5);
                    return true;
            }
            return false;
        });
    }

    public void changeFragment(int id){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id) {
            case 1:
                //topAppBar.setTitle("Donation Machine");
                fragmentTransaction
                        .replace(R.id.fragment_container, homeFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case 2:
                //topAppBar.setTitle("Profile");
                fragmentTransaction.
                        replace(R.id.fragment_container, nonprofitFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case 3:
                //topAppBar.setTitle("Info");
                fragmentTransaction
                        .replace(R.id.fragment_container, infoFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case 4:
                //topAppBar.setTitle("Favorites");
                fragmentTransaction
                        .replace(R.id.fragment_container, favFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case 5:
                //topAppBar.setTitle("Settings");
                fragmentTransaction
                        .replace(R.id.fragment_container, postFrag)
                        .addToBackStack(null)
                        .commit();
                break;
        }
        fragmentTransaction.addToBackStack(null);


    }



    private void slideUp() {
        //method to bring up the searchbar

        header = findViewById(R.id.header); //sets the header to be visible
        header.setVisibility(View.VISIBLE);
        searcher = findViewById(R.id.searcher);//sets the entire search area to be visible
        searcher.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, slidingSearch.getHeight()*-1);
        animate.setDuration(500);
        animate.setFillAfter(true);
        sb = findViewById(R.id.search_button); //sets the search button to be visible
        sb.setVisibility(View.VISIBLE);
        slidingSearch.startAnimation(animate);
        slidingSearch.postDelayed(() -> searchbar.setVisibility(View.INVISIBLE), 300);
        slidingSearch.setVisibility(View.GONE); //sets the search area to be gone
        closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be gone
        closerArrow.setVisibility(View.INVISIBLE);

        searcherIsDown = false;
    }

    public void reveal(View view) {
        //method to slide down the searchbar
        if (searcherIsDown) {
            slideUp();

        }
        else {
            header = findViewById(R.id.header); //sets the header to be visible
            header.setVisibility(View.VISIBLE);
            searchbar = findViewById(R.id.searchbar); //to be replaced with search bar
            searchbar.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0,0, slidingSearch.getHeight()*-1,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            slidingSearch.startAnimation(animate);
            slidingSearch.postDelayed(() -> searchbar.setVisibility(View.VISIBLE), 300);//delays start of Visibility of standin/searchbar
            sb = findViewById(R.id.search_button); //sets the search button to be gone
            sb.setVisibility(View.INVISIBLE);
            closerArrow = findViewById(R.id.closerArrow); //sets the closer arrow to be visible
            closerArrow.setVisibility(View.VISIBLE);


            searcherIsDown = true;
        }
    }


    /*
    public void goHome(View view) {//reloads Home page since user is at home page already
        setContentView(R.layout.activity_main);

        slidingSearch = findViewById(R.id.searcher);
        slidingSearch.setVisibility(View.INVISIBLE);
        searcherIsDown = false;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    */

    public void goProfile(View view) {//will go to User profile
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    public void goInfo(View view) {//will bring user to the info page for selected non-profit
        Intent intent = new Intent(this, nonProfit.class);
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