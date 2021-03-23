package com.example.a4p02app;

import androidx.annotation.NonNull;

import com.example.a4p02app.data.Firestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertTrue;

public class LoginSignupTest {

    private Firestore f = new Firestore();

    @Test
    public void test(){
        FirebaseFirestore mockDatabase = Mockito.mock(FirebaseFirestore.class);
        FirebaseUser mockUser = Mockito.mock(FirebaseUser.class);

        assertTrue(f.addAccount(mockDatabase, "some@email.com", mockUser, 0));  //Create a mock personal account
    }
}
