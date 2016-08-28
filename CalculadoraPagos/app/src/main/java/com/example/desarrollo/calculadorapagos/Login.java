package com.example.desarrollo.calculadorapagos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText EdUsuario;
    private EditText EdClave;
    private Button BtnIniciarSesion;
    private Button BtnRegistrarse;
    private int Intentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarComplementos();
        BtnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    Intent i = new Intent(Login.this, Calculadora.class);
                    //aca se le ponen los datos a pasar a a la otra actividad o se hace una animación
                    i.putExtra("usuario" , "Invitado");
                    startActivity(i);
                }
            }
        });

    }

    private boolean validarCampos(){
        if (!EdUsuario.getText().toString().equals("")) {
            if (!EdClave.getText().toString().equals("")) {
                return true;
            } else {
                Intentos = Intentos + 1;
                controlIntentos();
                EdClave.setText("");
                EdClave.requestFocus();
                Toast toscada = Toast.makeText(getApplicationContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT);
                //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
                toscada.show();
                return false;
            }
        } else {
            Intentos = Intentos + 1;
            controlIntentos();
            EdUsuario.setText("");
            EdUsuario.requestFocus();
            Toast toscada = Toast.makeText(getApplicationContext(), "Debe ingresar un usuario", Toast.LENGTH_SHORT);
            //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
            toscada.show();
            return false;
        }
    }

    private void controlIntentos() {

    }

    private void iniciarComplementos(){
        EdUsuario = (EditText)findViewById(R.id.edtUsuario);
        EdClave = (EditText)findViewById(R.id.edtClave);
        BtnIniciarSesion = (Button)findViewById(R.id.btnIngresar);
        BtnRegistrarse = (Button)findViewById(R.id.btnRegistrarse);
    }

}
