package com.cursoandroid.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context contexto;
    private SharedPreferences prefereces;
    private final String  NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";



    public Preferencias(Context contexto_parametro) {
        contexto = contexto_parametro;
        prefereces = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = prefereces.edit();
    }
    public void salvar_usuario_preferencias(String nome, String telefone, String token){
        editor.putString("nome", nome);
        editor.putString("telefone", telefone);
        editor.putString("token", token);
        editor.commit();
    }
    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME, prefereces.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, prefereces.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, prefereces.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
