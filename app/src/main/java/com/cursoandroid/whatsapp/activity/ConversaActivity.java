package com.cursoandroid.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.config.ConfiguracaoFireBase;
import com.cursoandroid.whatsapp.helper.Base64Custom;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Mensagem;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DatabaseReference firebase;
    private ListView listview;
    private ArrayList<String> mensagens;
    private ArrayAdapter adapter;
    //dados destinatario
    private String nomeUsuarioDestinatario;
    private String emailDestinatario;
    private String idUsuarioDestinatario;
    //dados do remetente
    private String idUsuarioRemetente;

    private EditText edit_mensagem;
    private ImageButton bt_mensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        edit_mensagem = findViewById(R.id.edit_mensagem);
        bt_mensagem = findViewById(R.id.bt_enviar);
        listview = findViewById(R.id.lv_conversas);

        //dados  do usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.get_identificador();


        /*
        Bundle aparentimente serve para recuperar dados de outras activites
        nesse caso foi possivel recuperar usando junto com os extra inpiut
         */
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nomeUsuarioDestinatario = extra.getString("nome");
            emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinatario);

        }

        toolbar = findViewById(R.id.tb_conversa);
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar);
        setSupportActionBar(toolbar);

        //monta listview w adapter
        mensagens = new ArrayList<>();
        adapter = new ArrayAdapter(
                ConversaActivity.this,
                android.R.layout.simple_list_item_1,
                mensagens
        );
        listview.setAdapter(adapter);
        //recuperar mensagens do fire base
        firebase = ConfiguracaoFireBase.getFireBase()
                .child("mensagens");


        bt_mensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensagem = edit_mensagem.getText().toString();
                if (textoMensagem.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show();
                }
                else{
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);
                    salvar_mensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    edit_mensagem.setText("");
                }
            }
        });

    }
    private boolean salvar_mensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try {
            firebase = ConfiguracaoFireBase.getFireBase().child("mensagem");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}