package datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Emerson Javid on 29/04/2016.
 */
public class BaseDeDatos extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION=1;
    protected static final String DATABASE_NAME="Strooper";

    public BaseDeDatos(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE jugador " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, "+
                "nick TEXT, "+
                "clave TEXT, " +
                "puntuacion INTEGER );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS jugador;";
        db.execSQL(sql);

        onCreate(db);
    }
}
