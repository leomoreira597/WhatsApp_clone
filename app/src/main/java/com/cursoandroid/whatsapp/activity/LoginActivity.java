package com.cursoandroid.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private EditText cod_pais;
    private EditText cod_area;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = findViewById(R.id.edit_telefone);
        cod_pais = findViewById(R.id.edit_codigo_pais);
        cod_area = findViewById(R.id.edit_area);
        nome = findViewById(R.id.edit_nome);
        cadastrar = findViewById(R.id.botao_cadastrar);

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskCodPais = new MaskTextWatcher(cod_pais, simpleMaskCodPais);
        cod_pais.addTextChangedListener(maskCodPais);

        SimpleMaskFormatter simpleMaskArea = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskCodArea = new MaskTextWatcher(cod_area, simpleMaskArea);
        cod_area.addTextChangedListener(maskCodArea);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome_usuario = nome.getText().toString();
                String telefone_completo =
                        cod_pais.getText().toString() +
                                cod_area.getText().toString() +
                                telefone.getText().toString();
                String telefone_sem_formatacao = telefone_completo.replace("+", "");
                telefone_sem_formatacao = telefone_completo.replace("-","");
                //gerar token
                Random randomico = new Random();
                int numero_aleatorio = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numero_aleatorio);
                String mensagemEnvio = "WhatasApp código de confirmação: " + token;
                //salvar dados para validação
                Preferencias preferencias = new Preferencias(getApplicationContext());
                preferencias.salvar_usuario_preferencias(nome_usuario, telefone_sem_formatacao, token);
                //envio de sms
                boolean enviadoSms = enviaSMS("+" + telefone_sem_formatacao, mensagemEnvio);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();

            }
        });
    }
    private boolean enviaSMS(String telefone, String mensagem){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}