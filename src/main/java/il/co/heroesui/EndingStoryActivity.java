package il.co.heroesui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Objects;

import static il.co.heroesui.utils.JSONUtils.loadJSONFromStream;

public class EndingStoryActivity extends AppCompatActivity {
    String TAG = "STORY_ENDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_story);

        final TextView scrolledSurvivorName = (TextView) findViewById(R.id.survivor_name);
        final TextView staticSurvivorName = (TextView) findViewById(R.id.survivor_name_static);
        final Button restartStory = (Button) findViewById(R.id.restart_story_from_ending);
        final ImageView imgSurvivor = (ImageView) findViewById(R.id.survivor_epilogue_image);
        final TextView shortInfo = (TextView) findViewById(R.id.short_survivor_info);
        final TextView linkView = (TextView) findViewById(R.id.full_survivor_info_link);

        Intent intent = getIntent();
        final String storyFilename = intent.getStringExtra("story");
        final String survivor = intent.getStringExtra("survivor");
        // TODO InputStream stream = getApplicationContext().openFileInput(storyFilename);
        scrolledSurvivorName.setText(survivor);
        staticSurvivorName.setText(survivor);

        InputStream stream = getResources().openRawResource(getResources().getIdentifier("raw/" + storyFilename, null, this.getPackageName()));
        try {
            final JSONObject storyJSON = new JSONObject(Objects.requireNonNull(loadJSONFromStream(stream)));
            linkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(storyJSON.getString("link")));
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            shortInfo.setText(storyJSON.getString("info"));
            imgSurvivor.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier("drawable/" + storyFilename, null, getPackageName())));
        }
        catch(Exception e) {
            Log.e(TAG, e.toString());
            finish();
        }

        scrolledSurvivorName.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < 0) {
                    scrolledSurvivorName.setVisibility(View.INVISIBLE);
                    staticSurvivorName.setVisibility(View.VISIBLE);
                    restartStory.setVisibility(View.GONE);
                } else {
                    scrolledSurvivorName.setVisibility(View.VISIBLE);
                    staticSurvivorName.setVisibility(View.GONE);
                    restartStory.setVisibility(View.VISIBLE);
                }
            }
        });

        restartStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(EndingStoryActivity.this, SceneActivity.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences(storyFilename, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("currentChapter", 0);
                editor.putInt("currentScene", 0);
                editor.putBoolean("completed", false);
                editor.apply();
                // TODO intent.setFlags(...)
                scene_intent.putExtra("story", storyFilename);
                scene_intent.putExtra("currentChapter", 0);
                scene_intent.putExtra("currentScene", 0);
                scene_intent.putExtra("currentLine", 0);
                startActivity(scene_intent);
            }
        });
    }
}
