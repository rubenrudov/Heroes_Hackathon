package il.co.heroesui;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    // Buttons for actions
    Button startGame, addNewStory, about;
    FloatingActionButton settings;
    // Music playing properties
    MediaPlayer backgroundMusic;
    AudioManager audioManager;
    boolean isntMuted = true; // The app would launch the music player as it launches.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // UI components setting
        startGame = findViewById(R.id.newGameButton);
        addNewStory = findViewById(R.id.newStoryAdding);
        about = findViewById(R.id.about);
        settings = findViewById(R.id.settings);
        // Music
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        final int[] max = {audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)};
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max[0] / 4, 0);
        backgroundMusic = MediaPlayer.create(this, R.raw.horror);
        backgroundMusic.start();
        // OnClickListening for the buttons
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Taking the user for story picking and than to the game itself
                startActivity(new Intent(MainActivity.this, SelectGameActivity.class));
            }
        });
        addNewStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Taking the user to the story adding platform
                startActivity(new Intent(MainActivity.this, NewStoryActivity.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Taking the user to our landing page
                String url = "https://heroes-hackathon.firebaseapp.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Write a settings activity -> advanced...
                // Settings activity / Muter for the music, we'll discuss about it later...
                // startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                max[0] = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                if (isntMuted) {
                    backgroundMusic.setVolume(0, 0);
                    settings.setImageResource(R.drawable.ic_volume_off);
                    isntMuted = false;
                }

                else
                {
                    backgroundMusic.setVolume(1, 1);
                    settings.setImageResource(R.drawable.ic_volume);
                    isntMuted = true;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundMusic.stop();
    }
}
