package com.cursoandroid.whatsapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.activity.ConversaActivity;
import com.cursoandroid.whatsapp.adapter.ContatoAdapter;
import com.cursoandroid.whatsapp.config.ConfiguracaoFireBase;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.cursoandroid.whatsapp.model.Contato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContatosFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference fireBase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        fireBase.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        fireBase.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contatos = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);
       /* adapter = new ArrayAdapter(
                //pega a main activity
                getActivity(),
                //layout de lista
                R.layout.lista_contatos,
                //dados para exibição
                contatos
        );*/
        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);
        //Recuperar contatos do fire base
        Preferencias preferencias = new Preferencias(getActivity());
        String identificador_usuario_logado = preferencias.get_identificador();
        fireBase = ConfiguracaoFireBase.getFireBase()
                    .child("contatos")
                    .child(identificador_usuario_logado);
        //Listener para recuperar contato
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //limpar a lista
                contatos.clear();

                //listar contatos
                for (DataSnapshot dados: snapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                //recupera dado
                Contato contato = contatos.get(position);
                //envia dados para conversa activity
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                startActivity(intent);
            }
        });
        return view;

    }
}