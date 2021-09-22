package com.cursoandroid.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFireBase {
    private static DatabaseReference referencia_FireBase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFireBase(){

        if (referencia_FireBase == null) {
            referencia_FireBase = FirebaseDatabase.getInstance().getReference();
        }

        return referencia_FireBase;
    }
    public static FirebaseAuth getFirebaseAutenticacao(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
