package com.example.openlab1.strooper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import control.TablaControlJugador;
import modelo.Jugador;

public class RecuperacionClave extends AppCompatActivity {
    Button generar;
    TextView lblContraseña;
    EditText nombre, nick, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_clave);

        inicializarControles();
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().equals("") || nick.getText().toString().equals("")){
                    Toast toscada = Toast.makeText(getApplicationContext(), "No se permiten campos vacios", Toast.LENGTH_SHORT);
                    //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
                    toscada.show();
                }else{
                    //String nick2 = nick.getText().toString();
                    Jugador jugador =  new Jugador();

                    //TablaControlJugador control = TablaControlJugador(context);
                    Random r = new Random();
                    int maxNombre = nombre.getText().length();
                    maxNombre = r.nextInt(maxNombre + 1);
                    char caracter = nombre.getText().charAt(maxNombre);
                }
            }
        });
    }
    public void inicializarControles(){
        generar = (Button) findViewById(R.id.btnGenerarClave);
        lblContraseña = (TextView) findViewById(R.id.lblContraseña);
        nombre = (EditText) findViewById(R.id.txtNombre);
        nick = (EditText) findViewById(R.id.txtNick);
        contraseña = (EditText) findViewById(R.id.txtNuevaClave);

        lblContraseña.setTextSize(0);
    }


}
