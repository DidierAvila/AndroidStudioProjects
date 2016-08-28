package com.example.desarrollo.myappdatabase;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desarrollo.myappdatabase.DataBase.HelpDataBase;

public class MainActivity extends AppCompatActivity {

    Button btnGuardar,btnConsultar,btnModificar;
    EditText edDocumento,edNombre,edTelefono,edResponse;
    HelpDataBase objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnModificar = (Button)findViewById(R.id.btnModificar);
        edDocumento = (EditText)findViewById(R.id.edDocument);
        edNombre = (EditText)findViewById(R.id.edNombre);
        edTelefono = (EditText)findViewById(R.id.edTelefono);
        objeto = new HelpDataBase(getApplicationContext());
        edResponse = (EditText)findViewById(R.id.edResponse);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar(v);
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edResponse.setText("Query: " + HelpDataBase.DatosTbla.CREATE_TABLA);
            }
        });

    }

    public void guardar(View v){
        long newRowid=0;
        String Message ="";

        try {
            SQLiteDatabase db = objeto.getWritableDatabase();
            ContentValues values = new ContentValues();
            //values.put(HelpDataBase.DatosTbla.COLUMN_ID, edDocumento.getText().toString());
            values.put(HelpDataBase.DatosTbla.COLUMN_NOMBRE, edNombre.getText().toString());
            values.put(HelpDataBase.DatosTbla.COLUMN_TELEFONO, edTelefono.getText().toString());
            newRowid = db.insert(
                    HelpDataBase.DatosTbla.TABLE_NAME,
                    HelpDataBase.DatosTbla.COLUMN_ID,
                    values);
        }catch (Exception e){
            Message = e.toString();
        }
        Toast.makeText(MainActivity.this, "Guardado" + Message + newRowid, Toast.LENGTH_SHORT).show();
    }

    public void modificar(View v){
        int count=0;
        String mensaje="";
        try {
            SQLiteDatabase db = objeto.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(HelpDataBase.DatosTbla.COLUMN_NOMBRE, edNombre.getText().toString());
            values.put(HelpDataBase.DatosTbla.COLUMN_TELEFONO, edTelefono.getText().toString());
            String[] argumento = {edTelefono.getText().toString()};
            String seleccion = HelpDataBase.DatosTbla.COLUMN_ID + "=?";
            count = db.update(
                    HelpDataBase.DatosTbla.TABLE_NAME,
                    values,
                    seleccion,
                    argumento
            );
        }catch (Exception e){
            mensaje = e.toString();
        }
        Toast.makeText(MainActivity.this, "Guardado" + mensaje + count, Toast.LENGTH_SHORT).show();
    }

}
