package com.example.desarrollo.calculadorapagos;

import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Calculadora extends AppCompatActivity {

    private EditText EdSalario;
    private EditText EdIngresoBC;
    private EditText EdSalud;
    private EditText EdPension;
    private EditText EdRiesgosLab;
    private EditText EdTotalPagar;
    private Button BtnCalcular;
    private Button BtnBorrar;
    private final Double IBC = 40.00;
    private final double Salud = 12.5;
    private final double Pension = 16;
    private final double ARL = 7500;
    private double Total;
    public String Error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        iniciarComplementos();
        BtnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    if (Calcular()){
                    }
                }
            }
        });
        BtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Borrar();
            }
        });
    }

    private  void Borrar(){
        EdSalario.setText("");
        EdIngresoBC.setText("");
        EdSalud.setText("");
        EdPension.setText("");
        EdRiesgosLab.setText("");
        EdTotalPagar.setText("");
    }

    private boolean Calcular(){
        try {
            double vector []=new double [5];
            double Salario = Double.valueOf(EdSalario.getText().toString()).doubleValue();

            vector[0] = (Salario * IBC)/100;
            vector[1] = (Salario * Salud)/100;
            vector[2] = (Salario * Pension)/100;
            vector[3] =  ARL;
            vector[4] =  vector[0]+vector[1]+vector[2]+vector[3];

            EdIngresoBC.setText(Double.toString(vector[0]));
            EdSalud.setText(Double.toString(vector[1]));
            EdPension.setText(Double.toString(vector[2]));
            EdRiesgosLab.setText(Double.toString(vector[3]));
            EdTotalPagar.setText(Double.toString(vector[4]));

            return  true;
        }catch (Exception e){
            Error = e.toString();
            return  false;
        }
    }

    private boolean validarCampos(){
        if (!EdSalario.getText().toString().equals("")) {
            return true;
        } else {
            EdSalario.setText("");
            EdSalario.requestFocus();
            Toast toscada = Toast.makeText(getApplicationContext(), "Debe ingresar un usuario", Toast.LENGTH_SHORT);
            //toscada.setGravity(Gravity.TOP|Gravity.LEFT,100,0);
            toscada.show();
            return false;
        }
    }

    private void iniciarComplementos(){
        EdSalario = (EditText)findViewById(R.id.edtSalario);
        EdIngresoBC = (EditText)findViewById(R.id.edtIngresoBC);
        EdSalud = (EditText)findViewById(R.id.edtSalud);
        EdPension = (EditText)findViewById(R.id.edtPension);
        EdRiesgosLab = (EditText)findViewById(R.id.edtRiesgosLab);
        EdTotalPagar = (EditText)findViewById(R.id.edtTotalPagar);
        BtnCalcular = (Button)findViewById(R.id.btnCalcular);
        BtnBorrar = (Button)findViewById(R.id.btnBorrar);
    }
}
