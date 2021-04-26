package com.example.a4p02app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;

import com.example.a4p02app.fragments.chatFragment;
import com.example.a4p02app.fragments.chatListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatActivity extends AppCompatActivity {

    private FirebaseUser activeUser;
    private FirebaseAuth mAuth;

    private FragmentManager fragmentManager;
    private chatFragment chat;
    private chatListFragment chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        activeUser = mAuth.getCurrentUser();

        fragmentManager = getFragmentManager();
        chat = new chatFragment();
        chatList = new chatListFragment();

        changeFragment(1);


    }


    public void changeFragment(int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id) {
            case 1:
                fragmentTransaction
                        .replace(R.id.chat_container, chatList)
                        .addToBackStack(null)
                        .commit();
                break;

            case 2:
                fragmentTransaction.
                        replace(R.id.chat_container, chat)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
