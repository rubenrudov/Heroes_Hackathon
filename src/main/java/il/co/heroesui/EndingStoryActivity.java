package il.co.heroesui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Intent intent = getIntent();
        final String storyFilename = intent.getStringExtra("story");
        // TODO InputStream stream = getApplicationContext().openFileInput(storyFilename);
        InputStream stream = getResources().openRawResource(getResources().getIdentifier("raw/" + storyFilename, null, this.getPackageName()));
        try {
            JSONObject storyJSON = new JSONObject(Objects.requireNonNull(loadJSONFromStream(stream)));}
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
                } else {
                    scrolledSurvivorName.setVisibility(View.VISIBLE);
                    staticSurvivorName.setVisibility(View.GONE);
                }
            }
        });
    }
}
