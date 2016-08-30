package com.example.desarrollo.appmvc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Datos.Connection;
import Model.Users;

public class MainActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText edName,edLastName,edUsername,edPassword;
    private TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialComponent();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateText()){
                    Register();
                }
            }
        });

    }

    private void Register(){
        try {
            Connection ObjConecction = new Connection(getApplicationContext());
            Users ObjUser = new Users();

            ObjUser.COLUMN_FULL_NAME = edName.getText().toString();
            ObjUser.COLUMN_LAST_NAME = edLastName.getText().toString();
            ObjUser.COLUMN_USERNAME = edUsername.getText().toString();
            ObjUser.COLUMN_PASSWORD = edPassword.toString().toString();
            ObjConecction.setsTransaction("Register");

            if (ObjConecction.ExecuteStatements(ObjUser)){
                Toast.makeText(MainActivity.this, "OK ", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(MainActivity.this, "KO: " + ObjConecction.getsMessage(), Toast.LENGTH_SHORT);
            }
            txtMensaje.setText(ObjConecction.getsMessage());
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT);
            txtMensaje.setText(ex.getMessage());
        }
    }

    private boolean ValidateText(){
        if (edName.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"Name is required",Toast.LENGTH_LONG);
            return false;
        }
        if (edLastName.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_LONG);
            return false;
        }
        if (edUsername.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT);
            return false;
        }
        if (edPassword.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void InitialComponent(){
        btnRegister = (Button)findViewById(R.id.btnRegister);
        edName = (EditText)findViewById(R.id.edFullName);
        edLastName = (EditText)findViewById(R.id.edLastName);
        edUsername = (EditText)findViewById(R.id.edUsername);
        edPassword = (EditText)findViewById(R.id.edPassword);
        txtMensaje = (TextView)findViewById(R.id.txvMensaje);
    }
}
