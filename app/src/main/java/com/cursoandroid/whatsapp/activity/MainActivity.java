package com.cursoandroid.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cursoandroid.whatsapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //private DatabaseReference referencia_firebase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   referencia_firebase.child("pontos").setValue(100);
    }
}