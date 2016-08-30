package com.example.emilio.ejemplosqlite.Databse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by emilio on 22/08/16.
 */
public class Ayudadatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Moviles.db";

    public Ayudadatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner<<<<<< class that defines the table contents */
    public static abstract class Datotabla implements BaseColumns {
        public static final String TABLE_NAME = "apredices";
        public static final String COLUMN_ID = "cedula";
        public static final String COLUMN_NAME = "Nombre";
        public static final String COLUMN_NAME_ = "telefono";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String CREAR_TABLA =
                "CREATE TABLE " + Datotabla.TABLE_NAME + " (" +
                        Datotabla.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        Datotabla.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        Datotabla.COLUMN_NAME_ + " INTEGER"     +   " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Datotabla.TABLE_NAME;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Datotabla.CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Datotabla.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
