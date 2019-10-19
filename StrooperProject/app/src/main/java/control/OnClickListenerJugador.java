package control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openlab1.strooper.R;

import modelo.Jugador;

/**
 * Created by Emerson on 03/05/2016.
 */
public class OnClickListenerJugador implements View.OnClickListener{
    int j;
    int i;

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        final View elementosFormularios = inflater.inflate(R.layout.form_nuevo_jugador,null,false);

        final EditText txtNombre = (EditText) elementosFormularios.findViewById(R.id.lblNombre);
        final EditText txtNick = (EditText) elementosFormularios.findViewById(R.id.lblNick);
        final EditText txtClave1 = (EditText) elementosFormularios.findViewById(R.id.lblClave1);
        final EditText txtClave2 = (EditText) elementosFormularios.findViewById(R.id.lblClave2);
        final TextView lblCantidad = (TextView) elementosFormularios.findViewById(R.id.lblCantidad);
        final TextView lblMnsjVdcnNick = (TextView) elementosFormularios.findViewById(R.id.lblMensaleValidacionNick);
        final TextView lblMnsjVdcnClave = (TextView) elementosFormularios.findViewById(R.id.lblMensaleValidacionContraseña);

        //ocultando mensajes de validacion
        lblMnsjVdcnClave.setTextSize(0);
        lblMnsjVdcnNick.setTextSize(0);
        //validando contraseñas
        txtClave2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtClave1.getText().toString().equals("")) {
                    if (txtClave1.getText().toString().equals(txtClave2.getText().toString())) {
                        lblMnsjVdcnClave.setText("");
                        lblMnsjVdcnClave.setTextSize(0);
                        txtClave1.setTextColor(context.getResources().getColor(R.color.colorVerdeConfirmacionValido));
                        txtClave2.setTextColor(context.getResources().getColor(R.color.colorVerdeConfirmacionValido));
                        j = 0;
                    } else {
                        lblMnsjVdcnClave.setText("Las claves no coinciden");
                        lblMnsjVdcnClave.setTextSize(10);
                        lblMnsjVdcnClave.setTextColor(context.getResources().getColor(R.color.colorFucsiaConfirmacionInvalido));
                        txtClave1.setTextColor(context.getResources().getColor(R.color.colorFucsiaConfirmacionInvalido));
                        txtClave2.setTextColor(context.getResources().getColor(R.color.colorFucsiaConfirmacionInvalido));
                        j = 1;
                        //txtClave1.setbackground
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Validando nick
        txtNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int nickExistente = new TablaControlJugador(context).nickExistente(s.toString());
                if (nickExistente > 0) {
                    lblMnsjVdcnNick.setText("El nick ya Existe");
                    lblMnsjVdcnNick.setTextSize(10);
                    lblMnsjVdcnNick.setTextColor(context.getResources().getColor(R.color.colorFucsiaConfirmacionInvalido));
                    txtNick.setTextColor(context.getResources().getColor(R.color.colorFucsiaConfirmacionInvalido));
                    txtClave1.setEnabled(false);
                    txtClave2.setEnabled(false);
                    i = 1;
                } else {
                    lblMnsjVdcnNick.setText("");
                    lblMnsjVdcnNick.setTextSize(0);
                    txtNick.setTextColor(context.getResources().getColor(R.color.colorVerdeConfirmacionValido));
                    txtClave1.setEnabled(true);
                    txtClave2.setEnabled(true);
                    i = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //consultando la cantidad de jugadores
        int cantidad = new TablaControlJugador(context).contar();
        lblCantidad.setText("Jugadores registrados: " + cantidad);

        new AlertDialog.Builder(context)
                .setView(elementosFormularios)
                .setTitle("Registro Jugadores")
                .setPositiveButton("Registar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (txtNombre.getText().toString().equals("") || j == 1 || i == 1) {
                                    Toast.makeText(context, "No se puede registar el usuario", Toast.LENGTH_SHORT).show();
                                } else {
                                    Jugador jugador = new Jugador();
                                    User UserObject = new User();
                                    jugador.setNombre(txtNombre.getText().toString());
                                    jugador.setNick(txtNick.getText().toString());
                                    jugador.setClave(txtClave1.getText().toString());
                                    jugador.setPuntuacion(0);
                                    boolean fueRegistrado = UserObject.createPlayer(jugador);
                                    //boolean fueRegistrado = new TablaControlJugador(context).crear(jugador);
                                    if (fueRegistrado) {
                                        Toast.makeText(context, "Jugador registrado con exito", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Ha ocurrido un error en el registro", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }
                )
                .setNegativeButton("Cancelar", null)
                .show();
    }


}
