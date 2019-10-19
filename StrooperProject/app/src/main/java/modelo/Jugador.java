package modelo;

/**
 * Created by Emerson Javid on 29/04/2016.
 */
public class Jugador {

    private int id;
    private String nick;
    private String clave;
    private String nombre;
    private int puntuacion;

    public Jugador(){

    }

    public Jugador(String nombre, int puntuacion){
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public Jugador(String nick, String clave, String nombre) {
        this.nick = nick;
        this.clave = clave;
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {return puntuacion;}

    public void setPuntuacion(int puntuacion) {this.puntuacion = puntuacion;}

}
