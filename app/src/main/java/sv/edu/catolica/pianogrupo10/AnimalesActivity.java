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
            mediaPlayer.start();
            Toast.makeText(this, "Nota: " + nombre, Toast.LENGTH_SHORT).show();

            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
            });
        }

        registrarEnSecuencia(nombre);
    }

    private void reproducirSonidoLibre(int sonidoResId, String nombre) {
        MediaPlayer sonido = MediaPlayer.create(this, sonidoResId);
        if (sonido != null) {
            sonido.start();
            Toast.makeText(this, "Nota: " + nombre, Toast.LENGTH_SHORT).show();
            sonido.setOnCompletionListener(MediaPlayer::release);
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
            especial.start();
            especial.setOnCompletionListener(MediaPlayer::release);
        }
    }

    // Botones
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
