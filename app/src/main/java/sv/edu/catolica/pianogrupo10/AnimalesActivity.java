package sv.edu.catolica.pianogrupo10;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sv.edu.catolica.pianogrupo10.R;

public class AnimalesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private List<String> secuenciaUsuario = new ArrayList<>();
    private final List<String> secuenciaCorrecta = Arrays.asList("Pollo", "Vaca", "Pato");
    private List<MediaPlayer> activeMediaPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.animales);
    }

    private void reproducirSonidoConInterrupcion(int sonidoResId, String nombre) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, sonidoResId);
        if (mediaPlayer != null) {
            activeMediaPlayers.add(mediaPlayer);
            mediaPlayer.start();
            Toast.makeText(this, "Nota: " + nombre, Toast.LENGTH_SHORT).show();

            mediaPlayer.setOnCompletionListener(mp -> {
                activeMediaPlayers.remove(mp);
                mp.release();
                mediaPlayer = null;
            });
        }

        registrarEnSecuencia(nombre);
    }

    private void reproducirSonidoLibre(int sonidoResId, String nombre) {
        // Detener cualquier sonido que se esté reproduciendo actualmente
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        MediaPlayer sonido = MediaPlayer.create(this, sonidoResId);
        if (sonido != null) {
            mediaPlayer = sonido; // Asignar el nuevo MediaPlayer a la variable mediaPlayer
            activeMediaPlayers.add(sonido);
            sonido.start();
            Toast.makeText(this, "Nota: " + nombre, Toast.LENGTH_SHORT).show();
            sonido.setOnCompletionListener(mp -> {
                activeMediaPlayers.remove(mp);
                mp.release();
                mediaPlayer = null; // Asegurarse de limpiar la referencia
            });
        }
    }


    private void registrarEnSecuencia(String nombre) {
        secuenciaUsuario.add(nombre);

        if (secuenciaUsuario.size() > secuenciaCorrecta.size()) {
            secuenciaUsuario.remove(0); // elimina el primero
        }

        if (secuenciaUsuario.equals(secuenciaCorrecta)) {
            reproducirPistaEspecial();
            secuenciaUsuario.clear();
        }
    }

    private void reproducirPistaEspecial() {
        Toast.makeText(this, "Secuencia ", Toast.LENGTH_LONG).show();
        MediaPlayer especial = MediaPlayer.create(this, R.raw.pollitopio);
        if (especial != null) {
            activeMediaPlayers.add(especial); // Añadir a la lista de activos
            especial.start();
            especial.setOnCompletionListener(mp -> {
                activeMediaPlayers.remove(mp); // Eliminar de la lista cuando termine
                mp.release();
            });
        }
    }

    // Método para detener todos los sonidos
    private void stopAllSounds() {
        for (MediaPlayer mp : activeMediaPlayers) {
            if (mp != null && mp.isPlaying()) {
                mp.stop();
                mp.release();
            }
        }
        activeMediaPlayers.clear();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAllSounds();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAllSounds();
    }

    // Botones (métodos sin cambios)
    public void reproducirPollo(View view) {
        reproducirSonidoConInterrupcion(R.raw.pollo, "Pollo");
    }

    public void reproducirVaca(View view) {
        reproducirSonidoConInterrupcion(R.raw.vaca, "Vaca");
    }

    public void reproducirPato(View view) {
        reproducirSonidoConInterrupcion(R.raw.pato, "Pato");
    }

    public void reproducirGallo(View view) {
        secuenciaUsuario.clear();
        reproducirSonidoLibre(R.raw.gallo, "Gallo");
    }

    public void reproducirOveja(View view) {
        secuenciaUsuario.clear();
        reproducirSonidoLibre(R.raw.oveja, "Oveja");
    }

    public void reproducirCabra(View view) {
        secuenciaUsuario.clear();
        reproducirSonidoLibre(R.raw.cabra, "Cabra");
    }

    public void reproducirPajaro(View view) {
        secuenciaUsuario.clear();
        reproducirSonidoLibre(R.raw.pajaro, "Pájaro");
    }
}