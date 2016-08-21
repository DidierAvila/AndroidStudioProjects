package com.example.openlab1.strooper;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import control.TablaControlJugador;
import modelo.Jugador;

public class juego extends AppCompatActivity implements SensorEventListener{
    TextView lblSegundos,segundos,color, lbltiempo, cantidadCorrectos, cantidadIncorrectos, cantidadIntentos,puntos;
    ImageView ImgOk, ImgKo;
    MediaPlayer gameOver,fatality;
    LinearLayout linear;
    SensorManager sm;
    Sensor sensor;
    RelativeLayout fondo;
    String usuario;
    String nick;
    Bundle b;
    private boolean Validar;
    private int Intentos;
    private int contadorCorrectos;
    private int contadorIncorrectos;
    private long acomuladdorTiempo;
    private int acomuladorPuntos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        inicializarControles();
        b = getIntent().getExtras();
        nick = b.getString("nick");
        usuario = b.getString("usuario");

    }
    public void inicializarControles(){
        lblSegundos = (TextView)findViewById(R.id.lblSegundos);
        segundos = (TextView)findViewById(R.id.segundos);
        color = (TextView)findViewById(R.id.lblcolor);
        lbltiempo = (TextView)findViewById(R.id.lblTiempo);
        //btnPlay = (Button)findViewById(R.id.btnPlay);
        //btnCustom = (Button)findViewById(R.id.btnPlay);
        //btnScore = (Button)findViewById(R.id.btnPlay);
        ImgOk = (ImageView) findViewById(R.id.imgOk);
        ImgKo = (ImageView) findViewById(R.id.imgKo);
        cantidadCorrectos =(TextView) findViewById(R.id.txtvValorCorrecto);
        cantidadIncorrectos=(TextView) findViewById(R.id.txtvValorInCorrecto);
        cantidadIntentos=(TextView) findViewById(R.id.txtvIntentos);
        puntos=(TextView) findViewById(R.id.lblPuntos);
        cuenta.cancel();

        lblSegundos.setText("Jugar");
        lbltiempo.setText("");
        segundos.setText("");
        color.setText("");
        ImgOk.setEnabled(false);
        ImgKo.setEnabled(false);

        //inicializar Sonido
        gameOver = MediaPlayer.create(this,R.raw.gor);
        fatality = MediaPlayer.create(this,R.raw.fatality);
        //layout
        linear = (LinearLayout) findViewById(R.id.linear);
        //sensor
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        fondo  = (RelativeLayout) findViewById(R.id.fondo);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

CountDownTimer cuenta = new CountDownTimer(4000,1000) {
    @Override
    public void onTick(long millisUntilFinished) {
        lblSegundos.setText(""+millisUntilFinished/1000);
        setAcomuladdorTiempo(millisUntilFinished/1000);
    }

    @Override
    public void onFinish() {
        cambiarTexto();
        cuenta.start();
    }
}.start();

   public void JugarYa(View view) {
        if(lblSegundos.getText().toString().equalsIgnoreCase("Jugar")){
            setIntentos(0);
            setContadorCorrectos(0);
            setContadorIncorrectos(0);
            setAcomuladorPuntos(0);
            lbltiempo.setText("TIEMPO");
            segundos.setText("Segundos");
           /* lbltiempo.setTextSize(25);
            lblSegundos.setTextSize(87);
            color.setTextSize(67);
            segundos.setTextSize(17);*/
            cantidadIntentos.setText(getIntentos()+"");
            cantidadCorrectos.setText(getContadorCorrectos()+"");
            cantidadIncorrectos.setText(getContadorIncorrectos()+"");
            puntos.setText(getAcomuladorPuntos()+"");
            ImgOk.setEnabled(true);
            ImgKo.setEnabled(true);
            cambiarTexto();
            cuenta.start();
        }
    }

    private void calificar(){}

    private void cambiarTexto(){

        String Texto[] = new String[4];
        Random ObjR = new Random();
        Texto[0] = "Azul";
        Texto[1] = "Amarillo";
        Texto[2] = "Rojo";
        Texto[3] = "Verde";

        int bandera = ObjR.nextInt(4);
        switch (bandera)
        {
            case 0:
                color.setText(Texto[bandera]);
                break;
            case 1:
                color.setText(Texto[bandera]);
                break;
            case 2:
                color.setText(Texto[bandera]);
                break;
            case 3:
                color.setText(Texto[bandera]);
                break;
            default:
        }
        int numeroColor = ObjR.nextInt(4);
        switch (numeroColor)
        {
            case 0:
                color.setTextColor(this.getResources().getColor(R.color.Azul));
                break;
            case 1:
                color.setTextColor(this.getResources().getColor(R.color.Amarillo));
                break;
            case 2:
                color.setTextColor(this.getResources().getColor(R.color.Rojo));
                break;
            case 3:
                color.setTextColor(this.getResources().getColor(R.color.Verde));
                break;
            default:
        }

        if(bandera == numeroColor){
            setValidar(true);
        }else {
            setValidar(false);
        }
    }

    public void imgOK(View view) {
        int contadorCorrectos = getContadorCorrectos();
        int contadorIncorrectos = getContadorIncorrectos();
        int intentos = getIntentos();
        intentos = intentos + 1;
        setIntentos(intentos);
        cantidadIntentos.setText(intentos+"");

        if(getValidar()== true){
            contadorCorrectos = contadorCorrectos + 1;
            cantidadCorrectos.setText(contadorCorrectos+"");
            setContadorCorrectos(contadorCorrectos);
            //metodo de asignacion de puntos
            puntuacion();
            cambio1000puntos(getAcomuladorPuntos());
        }else{
            contadorIncorrectos = contadorIncorrectos + 1;
            cantidadIncorrectos.setText(contadorIncorrectos+"");
            setContadorIncorrectos(contadorIncorrectos);
            fatality.start();
        }
        //Se puede convertir en un metodo
        if(contadorIncorrectos == 5){
            hasPerdido();

        }else {
            cambiarTexto();
            cuenta.cancel();
            cuenta.onFinish();
        }
    }

    public void imgKO(View view) {
        int contadorIncorrectos = getContadorIncorrectos();
        int contadorCorrectos = getContadorCorrectos();
        int intentos = getIntentos();
        intentos = intentos + 1;
        setIntentos(intentos);
        cantidadIntentos.setText(intentos+"");

        if(getValidar()== true){
            contadorIncorrectos = contadorIncorrectos + 1;
            cantidadIncorrectos.setText(contadorIncorrectos+"");
            setContadorIncorrectos(contadorIncorrectos);
            fatality.start();
        }else {
            contadorCorrectos = contadorCorrectos + 1;
            cantidadCorrectos.setText(contadorCorrectos + "");
            setContadorCorrectos(contadorCorrectos);

            puntuacion();
            cambio1000puntos(getAcomuladorPuntos());
        }
        //Se puede convertir en un metodo
        if(contadorIncorrectos == 5){
            hasPerdido();

        }else {
            cambiarTexto();
            cuenta.cancel();
            cuenta.onFinish();
        }
    }

    public void hasPerdido(){
        cuenta.cancel();
        lblSegundos.setText("Jugar");
        lblSegundos.setTextSize(50);
        lbltiempo.setText("");
        lbltiempo.setTextSize(0);
        segundos.setText("");
        color.setText("Has perdido");
        color.setTextSize(55);
        color.setTextColor(this.getResources().getColor(R.color.Rojo));
        ImgOk.setEnabled(false);
        ImgKo.setEnabled(false);
        gameOver.start();
        //guardando puntuacion
        Jugador jugador = new Jugador();
        jugador.setPuntuacion(getAcomuladorPuntos());
        jugador.setNick(nick);
        boolean puntuacionRegistrada = new TablaControlJugador(juego.this).puntuacion(jugador);
       /* if (puntuacionRegistrada) {
            Toast.makeText(this, "Jugador registrado con exito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ha ocurrido un error en el registro", Toast.LENGTH_SHORT).show();
        }*/

    }
    public void puntuacion(){
        if(getAcomuladdorTiempo() == 1){
            setAcomuladorPuntos(getAcomuladorPuntos() + 7);
            puntos.setText(getAcomuladorPuntos()+"" );
        }else if(getAcomuladdorTiempo() == 2){
            setAcomuladorPuntos(getAcomuladorPuntos() + 15);
            puntos.setText(getAcomuladorPuntos()+"");
        }else {
            setAcomuladorPuntos(getAcomuladorPuntos() + 20);
            puntos.setText(getAcomuladorPuntos()+"");
        }
    }

    public void cambio1000puntos(int ac){
        if(ac > 600 && ac < 1299 ){
          linear.setBackgroundColor(this.getResources().getColor(R.color.color500));
        }
        else if(ac > 1300){
            linear.setBackgroundColor(this.getResources().getColor(R.color.color1200));
        }//
    }

    public boolean getValidar() { return Validar; }

    public void setValidar(boolean validar) { Validar = validar; }

    public int getIntentos() { return Intentos;  }

    public void setIntentos(int intentos) { Intentos = intentos; }

    public int getContadorCorrectos() { return contadorCorrectos; }

    public void setContadorCorrectos(int contadorCorrectos) { this.contadorCorrectos = contadorCorrectos; }

    public int getContadorIncorrectos() { return contadorIncorrectos; }

    public void setContadorIncorrectos(int contadorIncorrectos) { this.contadorIncorrectos = contadorIncorrectos; }

    public int getAcomuladorPuntos() { return acomuladorPuntos; }

    public void setAcomuladorPuntos(int acomuladorPuntos) { this.acomuladorPuntos = acomuladorPuntos; }

    public long getAcomuladdorTiempo() { return acomuladdorTiempo; }

    public void setAcomuladdorTiempo(long acomuladdorTiempo) { this.acomuladdorTiempo = acomuladdorTiempo; }

    //SENSORES-.............
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0){
            fondo.setBackgroundColor(this.getResources().getColor(R.color.FondoAlter));
        }else {
            fondo.setBackgroundColor(this.getResources().getColor(R.color.FondoOriginal));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //REDES SOCIALES-.............
    public void compartirenTwitter(View view) {
        // Create intent using ACTION_VIEW and a normal Twitter url:
        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                urlEncode(usuario+" está jugando StrooperApp"),
                urlEncode("http://www.sena.edu.co"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

// Narrow down to official Twitter app, if available:
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        startActivity(intent);
    }
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {

            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    public void compartirEnFacebook(View view) {
        String mensaje = usuario+" está jugando StrooperApp!";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, mensaje);


        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }


        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + mensaje;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    public void compartirChoose(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "http://www.sena.edu.co");
        startActivity(Intent.createChooser(sharingIntent, "Compartir Strooper"));
    }
}

