package control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datos.BaseDeDatos;
import modelo.Jugador;

/**
 * Created by Emerson Javid on 29/04/2016.
 */
public class TablaControlJugador extends BaseDeDatos {

    public TablaControlJugador(Context context) {
        super(context);
    }

    public boolean crear(Jugador objectJugador){

        ContentValues values = new ContentValues();
        values.put("nombre" ,objectJugador.getNombre());
        values.put("nick", objectJugador.getNick());
        values.put("clave", objectJugador.getClave());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean fueRegistrado = db.insert("jugador",null,values) > 0;
        db.close();
        return fueRegistrado;
    }

    public int contar(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM jugador;";
        int cantidad = db.rawQuery(sql,null).getCount();
        db.close();
        return cantidad;
    }

    public List<Jugador> consultar(Jugador jugador){
    List<Jugador> lista = new ArrayList<>();
    String sql = "SELECT * FROM jugador WHERE nick='"+jugador.getNick()+"' "+
            "AND clave='"+jugador.getClave()+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String nick = cursor.getString(cursor.getColumnIndex("nick"));

                jugador = new Jugador();
                //jugador.id = id;
                jugador.setNombre(nombre);
                jugador.setNick(nick);

                lista.add(jugador);

            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return lista;
    }
    //metodo que verifica si el nick existe en la base de datos
   public int nickExistente(String nick){
       SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT nick FROM jugador WHERE nick = '"+nick+"';";
        int cantidad = db.rawQuery(sql,null).getCount();
        db.close();
      return cantidad;
    }

    //metodo de acceso al sistema
    public Jugador acceder(String nick, String clave){
        String sql = "SELECT * FROM jugador WHERE nick = '"+nick+"' AND clave ='"+clave+"';";
        Jugador jugador = new Jugador();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            jugador.setNombre(nombre);
            return jugador;
        }
        cursor.close();
        db.close();
        return null;
    }
    //metodo de recuperacion de contraseña
    public int recuperacion(String nombre, String nick){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM jugador WHERE nombre = '"+nombre+"' nick= '"+nick+"';";
        int cantidad = db.rawQuery(sql,null).getCount();
        db.close();
        return cantidad;
    }
    //metodo de almacenamiento de la nueva contraseña
    public boolean nuevaClave(Jugador objectJugador){

        ContentValues values = new ContentValues();
        values.put("clave", objectJugador.getClave());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean fueRegistrado = db.update("jugador",values,"nick='"+objectJugador.getNick()+"'",null) > 0;
        db.close();
        return fueRegistrado;
    }

    //INGRESANDO PUNTUACION;
    public boolean puntuacion(Jugador objectJugador){
        ContentValues values = new ContentValues();
        values.put("puntuacion", objectJugador.getPuntuacion());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean fueRegistrado = db.update("jugador",values,"nick = ?", new String[]{objectJugador.getNick()}) > 0;
        db.close();
        return fueRegistrado;
    }
    //consulta select * from puntuacion order by puntuacion desc limit 4;
    public List<Jugador> consultarPuntuacion(){
        //int i = 0;
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT * FROM jugador ORDER BY puntuacion DESC limit 5;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                int puntuacion = Integer.parseInt(cursor.getString(cursor.getColumnIndex("puntuacion")));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                Jugador jugador = new Jugador();
                jugador.setNombre(nombre);

                jugador.setPuntuacion(puntuacion);
                lista.add(jugador);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }
}
