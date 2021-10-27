package com.cursoandroid.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.cursoandroid.whatsapp.R;

public class ConversaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        toolbar = findViewById(R.id.tb_conversa);
        toolbar.setTitle("Usúario");
        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar);
        setSupportActionBar(toolbar);


    }
}