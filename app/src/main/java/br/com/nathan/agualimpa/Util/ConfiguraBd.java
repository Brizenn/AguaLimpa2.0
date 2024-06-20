package br.com.nathan.agualimpa.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraBd {


    private static FirebaseAuth auth;

    public static FirebaseAuth Firebaseautentificacao(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;

    }

    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;

    public static FirebaseAuth getFirebaseAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static DatabaseReference getFirebaseDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }

}
