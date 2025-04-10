package sv.edu.catolica.pianogrupo10;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AnimalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.animales);
    }
    private void reproducirSonido(int sonidoResId, String notaNombre) {
        MediaPlayer sonido = MediaPlayer.create(this, sonidoResId);

        try {
            sonido.start();
            Toast.makeText(this, "Nota: " + notaNombre, Toast.LENGTH_SHORT).show();

            sonido.setOnCompletionListener(MediaPlayer::release);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void reproducirPollo(View view) {
        reproducirSonido(R.raw.pollo, "Pollo");
    }

    public void reproducirGallo(View view) {
        reproducirSonido(R.raw.gallo, "Gallo");
    }

    public void reproducirOveja(View view) {
        reproducirSonido(R.raw.oveja, "Oveja");
    }

    public void reproducirCabra(View view) {
        reproducirSonido(R.raw.cabra, "Cabra");
    }

    public void reproducirPato(View view) {
        reproducirSonido(R.raw.pato, "Pato");
    }

    public void reproducirVaca(View view) {
        reproducirSonido(R.raw.vaca, "Vaca");
    }

    public void reproducirPajaro(View view) {
        reproducirSonido(R.raw.pajaro, "Pajaro");
    }
}


