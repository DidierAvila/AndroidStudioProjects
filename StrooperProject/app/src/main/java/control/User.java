package control;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Jugador;

import static android.content.ContentValues.TAG;

public class User {

    private static List<Jugador> lista;
    public static String UsuarioLogueado;

    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    public boolean createPlayer(Jugador currentPlayer) {
        final boolean isRegistered = false;
        if (currentPlayer == null)
        {
            return false;
        }
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", currentPlayer.getNombre());
        user.put("nick", currentPlayer.getNick());
        user.put("clave", currentPlayer.getClave());
        user.put("puntaje", currentPlayer.getPuntuacion());

        // Add a new document with a generated ID
        database.collection("Jugadores")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public static final String TAG = "Log";
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public static final String TAG = "Log";

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        return true;
    }

    public boolean getPlayer() {
        final Task<QuerySnapshot> jugadores = database.collection("Jugadores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
       Object[] d = jugadores.getResult().getDocuments().toArray();
       Class<? extends Object[]> example = d.getClass();
        try {
            String F = example.getDeclaredField("nombre").getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<Jugador> consultarPuntuacion()
    {
        lista = new ArrayList<Jugador>();
        database.collection("Jugadores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                //System.out.println (document.getId() + " => " + document.getData());
                                Object[] currentUser = document.getData().values().toArray();
                                /*String name = currentUser[0].toString();
                                int puntaje = Integer.parseInt(currentUser[3].toString());
                                jugador.setNombre(name);
                                jugador.setPuntuacion(puntaje);
                                lista.add(jugador);*/
                                //System.out.println (document.getId() + " => " + currentUser[d]);

                                lista.add(new Jugador(currentUser[0].toString(), Integer.parseInt(currentUser[3].toString())));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for(int i=0; i < lista.size(); i++)
                        {
                            System.out.println (" name => " + lista.get(i).getNombre());
                            System.out.println (" nick => " + lista.get(i).getNick());
                            System.out.println (" puntuación => " + lista.get(i).getPuntuacion());
                        }
                    }
                });

        return lista;
    }

    public boolean login(String userName, String password) {

        // Create a reference to the cities collection
        CollectionReference citiesRef = database.collection("user");

        // Create a query against the collection.
        //Query query = citiesRef.whereEqualTo("state", "CA");

        final String currentUserName = userName;
        final String currentPassword = password;
        database.collection("Jugadores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                System.out.println (document.getId() + " => " + document.getData());
                                Object[] currentUser = document.getData().values().toArray();
                                String name = currentUser[0].toString();
                                String password = currentUser[1].toString();
                                String nick = currentUser[2].toString();
                                if(currentUserName.equals(nick.toString()) && currentPassword.equals(password.toString())) {
                                    System.out.println (document.getId() + " => " + name);
                                    UsuarioLogueado=name;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        if(UsuarioLogueado == null){
            return false;
        }else {
            return true;
        }
    }

    public  void ddd(){
        List<DocumentSnapshot> ce = new ArrayList<DocumentSnapshot>();
        database.collection("Jugadores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                System.out.println (document.getId() + " => " + document.getData());
                                Object[] currentUser = document.getData().values().toArray();
                                String name = currentUser[0].toString();
                                int puntaje = Integer.parseInt(currentUser[3].toString());

                                System.out.println (" name => " + name);
                                System.out.println (" name => " + puntaje);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });





        /*for (DocumentSnapshot document : jugadores1.getDocuments()) {
            System.out.println (document.getId() + " => " + document.getData());
            Object[] currentUser = document.getData().values().toArray();
            String name = currentUser[0].toString();
            int puntaje = Integer.parseInt(currentUser[3].toString());
            System.out.println ("****Resultado***");
            System.out.println (" name => " + name);
            System.out.println (" name => " + puntaje);
        }*/

        /*for (DocumentSnapshot document : ce) {
            System.out.println (document.getId() + " => " + document.getData());
            Object[] currentUser = document.getData().values().toArray();
            String name = currentUser[0].toString();
            int puntaje = Integer.parseInt(currentUser[3].toString());
            System.out.println ("****Resultado***");
            System.out.println (" name => " + name);
            System.out.println (" name => " + puntaje);
        }*/
    }

    public List<Jugador> getScore()
    {
        //asynchronously retrieve multiple documents
        Task<QuerySnapshot> future =
                database.collection("cities").whereEqualTo("capital", true).get();

        database.collection("Jugadores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            lista = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Jugador jugador = new Jugador();
                                System.out.println (document.getId() + " => " + document.getData());
                                Object[] currentUser = document.getData().values().toArray();
                                String name = currentUser[0].toString();
                                int puntaje = Integer.parseInt(currentUser[3].toString());

                                System.out.println (" name => " + name);
                                System.out.println (" name => " + puntaje);

                                jugador.setNombre(name);
                                jugador.setPuntuacion(puntaje);
                                lista.add(jugador);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return getLista();
    }


    public List<Jugador> getLista() {
        return lista;
    }

    public void setLista(List<Jugador> lista) {
        this.lista = lista;
    }

    public boolean crear(Jugador objectJugador){
        boolean fueRegistrado = true;
        return fueRegistrado;
    }

    public int contar(){
        int cantidad = 0;
        return cantidad;
    }

    public List<Jugador> consultar(Jugador jugador){
        List<Jugador> lista = new ArrayList<>();
        return lista;
    }
    //metodo que verifica si el nick existe en la base de datos
    public int nickExistente(String nick){
        int cantidad = 0;
        return cantidad;
    }

    //metodo de acceso al sistema
    public Jugador acceder(String nick, String clave){
        Jugador jugador = new Jugador();
        return jugador;
    }
    //metodo de recuperacion de contraseña
    public int recuperacion(String nombre, String nick){
        int cantidad = 0;
        return cantidad;
    }
    //metodo de almacenamiento de la nueva contraseña
    public boolean nuevaClave(Jugador objectJugador){
        return true;
    }

    //INGRESANDO PUNTUACION;
    public boolean puntuacion(Jugador objectJugador){
        return true;
    }
}
