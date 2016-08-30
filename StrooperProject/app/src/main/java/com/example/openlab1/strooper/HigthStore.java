package com.example.openlab1.strooper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import control.TablaControlJugador;
import modelo.Jugador;

public class HigthStore extends AppCompatActivity {
    //TextView n1;
    //TextView p1;
    ListView lstVpuntos;
    ListView lstVnombre;
    ArrayAdapter adaptadorN;
    ArrayAdapter adaptadorP;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_higth_store);
        lstVpuntos = (ListView) findViewById(R.id.lstVpuntuacion);
        lstVnombre = (ListView) findViewById(R.id.lstVnombres);
        List<Jugador> lista;//contiene la lista general con nombres y puntuaciones retornados por el metodo consultarPuntuaciones
        List<String> nombres = new ArrayList(); //contiene la lista de solo los nombres de los jugadores
        List<Integer> puntos = new ArrayList(); //contiene la lista de solo los puntos de los jugadores
        lista = new TablaControlJugador(this).consultarPuntuacion();
        Jugador e = new Jugador();
        for (int i = 0; i < lista.size();i++){
             e = lista.get(i);
            nombres.add(e.getNombre());
            puntos.add(e.getPuntuacion());
        }
        adaptadorN = new ArrayAdapter(this, android.R.layout.simple_list_item_1,nombres);
        adaptadorP = new ArrayAdapter(this, android.R.layout.simple_list_item_1,puntos);
        lstVpuntos.setAdapter(adaptadorP);
        lstVnombre.setAdapter(adaptadorN);
    }
}
