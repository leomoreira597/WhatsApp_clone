package com.cursoandroid.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {
    private EditText codigo_validacao;
    private Button validar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigo_validacao = findViewById(R.id.edit_cod_validacao);
        validar = findViewById(R.id.btn_validar);

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskCodigoValidacao = new MaskTextWatcher(codigo_validacao, simpleMaskCodigoValidacao);
        codigo_validacao.addTextChangedListener(maskCodigoValidacao);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperar dados da preferencias do usuario
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                
                String token_gerado = usuario.get("token");
                String token_digitado = codigo_validacao.getText().toString();
                
                if (token_digitado.equals(token_gerado)){
                    Toast.makeText(ValidadorActivity.this, "Token Validado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ValidadorActivity.this, "Token não validado!!!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}