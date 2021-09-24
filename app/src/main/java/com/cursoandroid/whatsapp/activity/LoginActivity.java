package com.cursoandroid.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.config.ConfiguracaoFireBase;
import com.cursoandroid.whatsapp.helper.Permissao;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button botao_entrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificar_usuario_logado();

        email = findViewById(R.id.edit_login_email);
        senha = findViewById(R.id.edit_login_senha);
        botao_entrar = findViewById(R.id.bt_logar);

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validar_login();
            }
        });

    }
    private void verificar_usuario_logado(){
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrir_tela_principal();
        }
    }

    private void validar_login(){
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String erroConexao ="";
                if (task.isSuccessful()){
                    abrir_tela_principal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao Logar usuário", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e){
                        erroConexao = "E-mail e/ou senha inválidos. Tente novamente!!!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroConexao = "E-mail e/ou senha inválidos. Tente novamente!!!";
                    }
                    catch (Exception e) {
                        erroConexao = "Desculpe, ocorreu um erro ao conectar, verifique sua internet " +
                                "ou tente novamente mais tarde!";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, erroConexao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void abrir_tela_principal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrir_tela_cadastro(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

}