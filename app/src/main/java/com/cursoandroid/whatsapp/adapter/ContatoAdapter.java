package com.cursoandroid.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {
    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@NonNull Context c,  @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if (contatos != null){
            // inicializar o objeto de montagem da view
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //montar view apartir do xml
            view = inflater.inflate(R.layout.lista_contatos,parent, false);
            //recupera elemento para exibição
           TextView nomeContato =  view.findViewById(R.id.tv_nome);
           TextView emailContato = view.findViewById(R.id.tv_email);

           Contato contato = contatos.get(position);
           nomeContato.setText(contato.getNome());
           emailContato.setText(contato.getEmail());

        }
        return view;
    }
}
