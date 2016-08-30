package com.example.emilio.ejemplosqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilio.ejemplosqlite.Databse.Ayudadatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button guardar , modifivar, consultar,listar;
    EditText cedula , nombre, telefono;
    TextView mensaje;
    ListView lista;
     Ayudadatabase obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guardar =(Button)findViewById(R.id.guadar);
        consultar =(Button)findViewById(R.id.consultar);
        modifivar =(Button)findViewById(R.id.modificar);
        listar =(Button)findViewById(R.id.listar);
        lista =(ListView) findViewById(R.id.listView);
        obj =new Ayudadatabase(getApplicationContext());
        cedula =(EditText) findViewById(R.id.IDENTIFICACION);
        nombre =(EditText)findViewById(R.id.nombre);
        telefono =(EditText)findViewById(R.id.telefono);
        mensaje =(TextView)findViewById(R.id.mensaje);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarr(view);
            }


        });
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }


        });
        modifivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actializar(view);
            }


        });
        modifivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actializar(view);
            }


        });
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar(view);
            }


        });
    }
    public void guardarr(View v) {
        // Gets the data repository in write mode


        int duration = Toast.LENGTH_SHORT;
        try {
            SQLiteDatabase db = obj.getWritableDatabase();
// Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Ayudadatabase.Datotabla.COLUMN_ID, cedula.getText().toString());
            values.put(Ayudadatabase.Datotabla.COLUMN_NAME, nombre.getText().toString());
            values.put(Ayudadatabase.Datotabla.COLUMN_NAME_, telefono.getText().toString());

// Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Ayudadatabase.Datotabla.TABLE_NAME,
                    Ayudadatabase.Datotabla.COLUMN_ID,

                    values);
            mensaje.setText("se guardo " + newRowId + " Corectamente");
            Toast.makeText(MainActivity.this, "se guardo " + newRowId + " Corectamente", duration).show();
            //  Snackbar.make(v,"se guardo " + newRowId + " Corectamente");
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), duration).show();


        }
    }
    private  void listar(View view){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase db = obj.getReadableDatabase();

        String[] datos={Ayudadatabase.Datotabla.COLUMN_ID,Ayudadatabase.Datotabla.COLUMN_NAME, Ayudadatabase.Datotabla.COLUMN_NAME_};

        Cursor c = db.query(Ayudadatabase.Datotabla.TABLE_NAME,
                datos,
              null,
              null,
                null,
                null,
                null);
        int fila =c.getCount();
        for (int i =0;i<fila;i++){
            c.moveToNext();
            int cedu =c.getInt(0);
            String nombre =c.getString(1);
            int  telefono =c.getInt(0);

            arrayList.add(cedu+""+nombre+""+telefono);
        }
        ArrayAdapter <String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        lista.setAdapter(arrayAdapter);
    }
    public void consultar(View v ) {
        //metodo leer datos
        SQLiteDatabase db = obj.getReadableDatabase();
        String[] argumento = {
                cedula.getText().toString()
        };

        String[] datos={Ayudadatabase.Datotabla.COLUMN_NAME, Ayudadatabase.Datotabla.COLUMN_NAME_};

        Cursor c = db.query(Ayudadatabase.Datotabla.TABLE_NAME,
                datos,
                Ayudadatabase.Datotabla.COLUMN_ID+"=?",
                argumento,
                null,
                null,
                null);

        if(c.getCount()<1){
            Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_SHORT).show();
        }else{
            c.moveToFirst();
            nombre.setText(c.getString(0));
            telefono.setText(c.getString(1));
        }

    }
    public void Actializar(View v) {
        // Gets the data repository in write mode

        String[] argumento = {
                cedula.getText().toString()
        };
        int duration = Toast.LENGTH_SHORT;

        try {
            SQLiteDatabase db = obj.getWritableDatabase();
// Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            ///values.put(Ayudadatabase.Datotabla.COLUMN_ID, cedula.getText().toString());
            values.put(Ayudadatabase.Datotabla.COLUMN_NAME, nombre.getText().toString());
            values.put(Ayudadatabase.Datotabla.COLUMN_NAME_, telefono.getText().toString());
            String selelecion =Ayudadatabase.Datotabla.COLUMN_ID +"=?";
// Insert the new row, returning the primary key value of the new row
            int newRowId;
            newRowId = db.update(
                    Ayudadatabase.Datotabla.TABLE_NAME,
                    values
            ,selelecion,
                    argumento);
            mensaje.setText("se guardo " + newRowId + " Corectamente");
            Toast.makeText(MainActivity.this, "se actualizo " + newRowId + " Corectamente", duration).show();
            //  Snackbar.make(v,"se guardo " + newRowId + " Corectamente");
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), duration).show();


        }
    }
}
