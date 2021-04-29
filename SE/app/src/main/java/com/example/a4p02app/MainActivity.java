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
import com.example.a4p02app.data.FirebaseFunctions;
import com.example.a4p02app.fragments.*;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class MainActivity extends AppCompatActivity {


    //Firebase
    private FirebaseUser activeUser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //UI Component
    public static BottomNavigationView bottomAppBar;
    //public androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();

    //Window Fragments
    public FragmentManager fragmentManager;
    public static profileFragment profileFrag;
    public static favouriteFragment favFrag;
    public static homeFragment homeFrag;
    public static Fragment nonprofitFrag;
    public static postFragment postFrag;
    private static infoFragment infoFrag;
    private NPO_ListFragment npoListFrag;

    public boolean begunTest = false;
    public static boolean isTesting = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check for emulator connection
        if (isTesting) {
            if (!begunTest) {
                try {
                    FirebaseFunctions.checkForEmulator(mAuth, db);
                } catch (Exception e) {
                    System.err.println("Already initialized");
                }
                begunTest = false;
            }
        }

        //Check for previous login
        activeUser = mAuth.getCurrentUser();
        if (activeUser == null) {
            goLogin();
        }
         else {
            startMainActivity();
         }


       /*KeyboardVisibilityEvent.setEventListener(
               this,
               new KeyboardVisibilityEventListener() { //DO NOT SIMPLIFY (lambda) (WILL BREAK) -brian
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        //Log.d(TAG,"onVisibilityChanged: Keyboard visibility changed");
                        if(isOpen){
                            //Log.d(TAG, "onVisibilityChanged: Keyboard is open");
                           bottomAppBar.setVisibility(View.VISIBLE);
                            //Log.d(TAG, "onVisibilityChanged: NavBar got Invisible");
                        }else{
                            //Log.d(TAG, "onVisibilityChanged: Keyboard is closed");
                            bottomAppBar.setVisibility(View.INVISIBLE);
                            //Log.d(TAG, "onVisibilityChanged: NavBar got Visible");
                        }
                    }
                });

        */
        //bottomAppBar.setVisibility(View.VISIBLE);
    }


    private void startMainActivity(){
        setContentView(R.layout.activity_manager);

        //Components
        bottomAppBar = findViewById(R.id.bottomAppBar);
        //topAppBar = findViewById(R.id.topAppBar);

        //Fragments
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
                fragmentTransaction
                        .replace(R.id.fragment_container, homeFrag)
                        .addToBackStack("home")
                        .commit();
                break;

            case 2:
                fragmentTransaction.
                        replace(R.id.fragment_container, profileFrag)
                        .addToBackStack("profile")
                        .commit();
                //setActionBar(INVISIB);
                break;

            case 3:
                fragmentTransaction
                        .replace(R.id.fragment_container, npoListFrag)
                        .addToBackStack("npo")
                        .commit();
                break;

            case 4:
                fragmentTransaction
                        .replace(R.id.fragment_container, favFrag)
                        .addToBackStack("fav")
                        .commit();
                break;

            case 5:
                fragmentTransaction
                        .replace(R.id.fragment_container, postFrag)
                        .addToBackStack("post")
                        .commit();
                break;
        }
    }

    public void goLogin() {//brings user to the login page
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            super.onBackPressed();
            Fragment f = fragmentManager.findFragmentById(R.id.fragment_container);
            if (f instanceof homeFragment) {
                bottomAppBar.getMenu().getItem(0).setChecked(true);
            } else if (f instanceof profileFragment) {
                bottomAppBar.getMenu().getItem(1).setChecked(true);
            } else if (f instanceof NPO_ListFragment) {
                bottomAppBar.getMenu().getItem(2).setChecked(true);
            } else if (f instanceof favouriteFragment) {
                bottomAppBar.getMenu().getItem(3).setChecked(true);
            } else if (f instanceof postFragment) {
                bottomAppBar.getMenu().getItem(4).setChecked(true);
            }
        }
    }

    public void updateUser(FirebaseUser activeUser){
        this.activeUser = activeUser;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}