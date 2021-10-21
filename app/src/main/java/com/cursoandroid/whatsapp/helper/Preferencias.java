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
    private final String CHAVE_INDENTIFICADOR = "identificadorUsuarioLogado";




    public Preferencias(Context contexto_parametro) {
        contexto = contexto_parametro;
        prefereces = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = prefereces.edit();
    }
    public void salvar_dados(String identificadorUsuario){
        editor.putString(CHAVE_INDENTIFICADOR, identificadorUsuario);
        editor.commit();
    }

    public String get_identificador(){
        return prefereces.getString(CHAVE_INDENTIFICADOR, null);
    }
}
