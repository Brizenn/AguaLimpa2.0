package br.com.nathan.agualimpa.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguraBd {


    private static FirebaseAuth auth;

    public static FirebaseAuth Firebaseautentificacao(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;

    }

}
