package sv.edu.catolica.pianogrupo10;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstrumentosActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private List<String> secuenciaUsuario = new ArrayList<>();
    private final List<String> secuenciaCorrecta = Arrays.asList("Piano", "Violin", "Flauta");
    private List<MediaPlayer> activeMediaPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instrumentos);
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
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        MediaPlayer sonido = MediaPlayer.create(this, sonidoResId);
        if (sonido != null) {
            mediaPlayer = sonido;
            activeMediaPlayers.add(sonido);
            sonido.start();
            Toast.makeText(this, "Nota: " + nombre, Toast.LENGTH_SHORT).show();
            sonido.setOnCompletionListener(mp -> {
                activeMediaPlayers.remove(mp);
                mp.release();
                mediaPlayer = null;
            });
        }
    }


    private void registrarEnSecuencia(String nombre) {
        secuenciaUsuario.add(nombre);

        if (secuenciaUsuario.size() > secuenciaCorrecta.size()) {
            secuenciaUsuario.remove(0);
        }

        if (secuenciaUsuario.equals(secuenciaCorrecta)) {
            reproducirPistaEspecial();
            secuenciaUsuario.clear();
        }
    }

    private void reproducirPistaEspecial() {
        Toast.makeText(this, "Secuencia ", Toast.LENGTH_LONG).show();
        MediaPlayer especial = MediaPlayer.create(this, R.raw.carmen);
        if (especial != null) {
            activeMediaPlayers.add(especial);
            especial.start();
            especial.setOnCompletionListener(mp -> {
                activeMediaPlayers.remove(mp);
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
    public void reproducirAcordeon(View view) {
        reproducirSonidoConInterrupcion(R.raw.acordeon, "Acordeon");
    }

    public void reproducirFlauta(View view) {
        reproducirSonidoConInterrupcion(R.raw.flauta, "Flauta");
    }

    public void reproducirGuitarra(View view) {
        reproducirSonidoConInterrupcion(R.raw.guitarra, "Guitarra");
    }

    public void reproducirPiano(View view) {
        reproducirSonidoConInterrupcion(R.raw.piano, "Piano");
    }

    public void reproducirTambores(View view) {
        reproducirSonidoConInterrupcion(R.raw.tambores, "Tambores");
    }

    public void reproducirTrompeta(View view) {
        reproducirSonidoConInterrupcion(R.raw.trompeta, "Trompeta");
    }

    public void reproducirViolin(View view) {
        reproducirSonidoConInterrupcion(R.raw.violin, "Violin");
    }
}