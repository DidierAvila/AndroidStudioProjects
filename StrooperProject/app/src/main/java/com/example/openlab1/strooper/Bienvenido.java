package com.example.openlab1.strooper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

public class Bienvenido extends AppCompatActivity {
    TextView lblUsuarioRe;
    Button btnPlay, btnCustom, btnScore;
    String usuario;
    String nick;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

       inicializarControles();

        b = getIntent().getExtras();
        usuario = b.getString("usuario");
        nick = b.getString("nick");
        lblUsuarioRe.setText(usuario);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bienvenido.this, juego.class);
                //aca se le ponen los datos a pasar a a la otra actividad o se hace una animación
                i.putExtra("usuario" , lblUsuarioRe.getText());
                i.putExtra("nick",nick);
                startActivity(i);
            }
        });
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bienvenido.this, HigthStore.class);
                //aca se le ponen los datos a pasar a a la otra actividad o se hace una animación
                startActivity(i);
            }
        });
    }

    public void inicializarControles(){
        lblUsuarioRe = (TextView)findViewById(R.id.lblUsuarioRe);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnCustom = (Button)findViewById(R.id.btnCuston);
        btnScore = (Button)findViewById(R.id.btnScore);
    }


}
