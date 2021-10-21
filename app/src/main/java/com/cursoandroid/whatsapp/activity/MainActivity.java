package com.cursoandroid.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsapp.Fragments.ContatosFragment;
import com.cursoandroid.whatsapp.Fragments.ConversasFragment;
import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.config.ConfiguracaoFireBase;
import com.cursoandroid.whatsapp.helper.Base64Custom;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Contato;
import com.cursoandroid.whatsapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth usuario_autenticacao;
    private Toolbar toolbar;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private String identificador_do_contato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smartTabLayout = findViewById(R.id.abasWhats);
        viewPager = findViewById(R.id.viewpager);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Conversas", ConversasFragment.class)
                        .add("Contatos", ContatosFragment.class)
                .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        usuario_autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
         toolbar = findViewById(R.id.toolbar_principal);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogar_usuario();
                return true;
            case R.id.configuracao:
                return true;
            case R.id.item_adicionar:
                abrir_cadastro_contato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void abrir_cadastro_contato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        //configuraçoes de dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);
        EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);
        //configura botoes
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String e_mail_contato = editText.getText().toString();
                //Valida se email foi digitado
                if (e_mail_contato.isEmpty()){
                    Toast.makeText(MainActivity.this, "Digite o Email", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Verificar se usuario existe
                    identificador_do_contato = Base64Custom.codificarBase64(e_mail_contato);
                    //recuperar instancia no fire base
                    firebase = ConfiguracaoFireBase.getFireBase().child("usuarios").child(identificador_do_contato);
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null){
                                Usuario usuario_contato = snapshot.getValue(Usuario.class);
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.get_identificador();
                                usuario_autenticacao.getCurrentUser().getEmail();
                                firebase = ConfiguracaoFireBase.getFireBase();
                                firebase = firebase.child("contatos").child(identificadorUsuarioLogado)
                                        .child(identificador_do_contato);
                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificador_do_contato);
                                contato.setEmail(usuario_contato.getEmail());
                                contato.setNome(usuario_contato.getNome());
                                firebase.setValue(contato);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Esse E-Mail não está cadastrado em nosso banco de dados",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deslogar_usuario(){
        usuario_autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}