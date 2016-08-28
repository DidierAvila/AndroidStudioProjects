package com.example.desarrollo.myappdatabase.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.sql.Types;

/**
 * Created by Desarrollo on 8/22/2016.
 */
public class HelpDataBase extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Aprendices";


    public HelpDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static abstract class DatosTbla implements BaseColumns {
        public static final String TABLE_NAME = "Aprendices";
        public static final String COLUMN_ID = "Cedula";
        public static final String COLUMN_NOMBRE = "Nombre";
        public static final String COLUMN_TELEFONO= "Telefono";

        public static final String SQL_DELETE_aprendices = "DROP TABLE IF EXISTS " + DatosTbla.TABLE_NAME;

        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ", ";
        public static final String CREATE_TABLA=
                "CREATE TABLE Aprendices " +
                        "(Cedula INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        "Nombre TEXT, "+
                        "Telefono TEXT );";

        public static final String CREATE_TABLA3=
                "CREATE TABLE " + DatosTbla.TABLE_NAME + " (" +
                        DatosTbla.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        DatosTbla.COLUMN_NOMBRE + " TEXT, " +
                        DatosTbla.COLUMN_TELEFONO + " TEXT " +
                " )";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatosTbla.CREATE_TABLA);
        //db.execSQL(DatosTbla.CREATE_TABLA1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatosTbla.SQL_DELETE_aprendices);
        onCreate(db);
    }
}
