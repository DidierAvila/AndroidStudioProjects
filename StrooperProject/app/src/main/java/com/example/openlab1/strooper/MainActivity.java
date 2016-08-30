package com.example.openlab1.strooper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import control.OnClickListenerJugador;
import control.TablaControlJugador;
import modelo.Jugador;

public class MainActivity extends AppCompatActivity {
    Button ingresar,btnRegistrar;
    EditText txtUsuario, txtClave;
    TextView lblMnsjConteo;
    int intentos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador();
        btnRegistrar.setOnClickListener(new OnClickListenerJugador());
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validarCampos();
            }
        });
    }

    public void controlador() {
        ingresar = (Button) findViewById(R.id.btnIngresar);
        txtUsuario = (EditText) findViewById(R.id.pwdUsuario);
        txtClave = (EditText) findViewById(R.id.pwdClave);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        lblMnsjConteo = (TextView) findViewById(R.id.lblMnsjConteo);

    }


    public void validarCampos() {

        if (!txtUsuario.getText().toString().equals("")) {
            if (!txtClave.getText().toString().equals("")) {
                Jugador jugador = new Jugador();
                TablaControlJugador tablaControl = new TablaControlJugador(this);
                if (tablaControl.acceder(txtUsuario.getText().toString(),txtClave.getText().toString()) != null) {
                    //txtUsuario.getText().toString().equals("emerson") && txtClave.getText().toString().equals("12345")
                    jugador = tablaControl.acceder(txtUsuario.getText().toString(),txtClave.getText().toString());
                    Intent i = new Intent(MainActivity.this, Bienvenido.class);
                    //aca se le ponen los datos a pasar a a la otra actividad o se hace una animación
                    i.putExtra("usuario" , jugador.getNombre());
                    i.putExtra("nick",txtUsuario.getText().toString());
                    startActivity(i);
                    this.finish();
                } else {
                    intentos = intentos + 1;
                    controlIntentos();
                    txtUsuario.setText("");
                    txtClave.setText("");
                    Toast toscada = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
                    //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
                    toscada.show();
                }
            } else {
                intentos = intentos + 1;
                controlIntentos();
                txtClave.setText("");
                txtClave.requestFocus();
                Toast toscada = Toast.makeText(getApplicationContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT);
                //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
                toscada.show();
            }
        } else {
            intentos = intentos + 1;
            controlIntentos();
            txtUsuario.setText("");
            txtUsuario.requestFocus();
            Toast toscada = Toast.makeText(getApplicationContext(), "Debe ingresar un usuario", Toast.LENGTH_SHORT);
            //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
            toscada.show();
        }
    }

    public void controlIntentos() {
        if (intentos == 3) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setMessage("¿Olvidaste tu contraseña?");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //codigo de recuperacion de contraseña, llevarlon a otra actividad
                    Intent i = new Intent(MainActivity.this, RecuperacionClave.class);
                    startActivity(i);
                    finish();
                }
            });
            alerta.setNegativeButton("Cancelar", null);//new DialogInterface.OnClickListener() {
            // @Override
            //public void onClick(DialogInterface dialog, int which) {
            //codigo

            // }
            //});
            alerta.create();
            alerta.show();
        } else if (intentos == 5) {
            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtClave.setText("");
                    txtUsuario.setText("");
                    txtClave.setEnabled(false);
                    txtUsuario.setEnabled(false);
                    ingresar.setEnabled(false);
                    //ocultar teclado.-----------------------------------
                    //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    //imm.hideSoftInputFromWindow(txtUsuario.getWindowToken(), 0);
                    //imm.hideSoftInputFromWindow(txtClave.getWindowToken(), 0);
                    lblMnsjConteo.setText("Intente de nuevo en: " + (millisUntilFinished / 1000));

                }

                @Override
                public void onFinish() {
                    txtClave.setEnabled(true);
                    txtUsuario.setEnabled(true);
                    ingresar.setEnabled(true);
                    txtUsuario.requestFocus();
                    lblMnsjConteo.setText("");
                    intentos = 0;
                }
            }.start();
        }
    }
}

