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
import android.util.Log;
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

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class MainActivity extends AppCompatActivity {

    //Firebase
    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    //UI Component
    private BottomNavigationView bottomAppBar;

    //Window Fragments
    private FragmentManager fragmentManager;
    private donationFragment donationFrag;
    private profileFragment profileFrag;
    private favouriteFragment favFrag;
    private homeFragment homeFrag;
    private nonprofitFragment nonprofitFrag;
    private postFragment postFrag;
    private infoFragment infoFrag;
    private NPO_ListFragment npoListFrag;


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
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        //Log.d(TAG,"onVisibilityChanged: Keyboard visibility changed");
                        if(isOpen){
                            //Log.d(TAG, "onVisibilityChanged: Keyboard is open");
                            bottomAppBar.setVisibility(View.INVISIBLE);
                            //Log.d(TAG, "onVisibilityChanged: NavBar got Invisible");
                        }else{
                            //Log.d(TAG, "onVisibilityChanged: Keyboard is closed");
                            bottomAppBar.setVisibility(View.VISIBLE);
                            //Log.d(TAG, "onVisibilityChanged: NavBar got Visible");
                        }
                    }
                });
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
            case R.id.npolist:
                changeFragment(3);
                return true;
            case R.id.favs:
                changeFragment(4);
                return true;
            case R.id.post:
                changeFragment(5);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    private void startMainActivity(){
        setContentView(R.layout.activity_manager);

        //Components
        bottomAppBar = findViewById(R.id.bottomAppBar);
        //topAppBar = findViewById(R.id.topAppBar);

        //Fragments
        donationFrag = new donationFragment();
        profileFrag = new profileFragment();
        favFrag = new favouriteFragment();
        homeFrag = new homeFragment();
        nonprofitFrag = new nonprofitFragment();
        postFrag = new postFragment();
        infoFrag = new infoFragment();
        npoListFrag = new NPO_ListFragment();

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
                case R.id.npolist:
                    changeFragment(3);
                    return true;
                case R.id.favs:
                    changeFragment(4);
                    return true;
                case R.id.post:
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
                        replace(R.id.fragment_container, profileFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case 3:
                //topAppBar.setTitle("Info");
                fragmentTransaction
                        .replace(R.id.fragment_container, npoListFrag)
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