package il.co.heroesui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Locale;

import il.co.heroesui.utils.JSONUtils;

public class ChapterActivity extends AppCompatActivity {
    TextView mChapterTitle;
    TextView mChapterNumber;
    Button bStartChapter;
    Button bRestartStory;
    Button bRestartChapter;
    ImageView imgChapterCover;
    LinearLayout layoutScreen;
    JSONObject chapter;

    private int currentChapter;
    private String storyFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;

        /* Initialization */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        mChapterTitle = (TextView) findViewById(R.id.chapter_title);
        mChapterNumber = (TextView) findViewById(R.id.chapter_number);
        bStartChapter = (Button) findViewById(R.id.start_chapter_button);
        bRestartStory = (Button) findViewById(R.id.start_over_button);
        bRestartChapter = (Button) findViewById(R.id.restart_previous_chapter_button);
        imgChapterCover = (ImageView) findViewById(R.id.chapter_cover_image);

        /* Load intent */
        intent = getIntent();
        currentChapter = intent.getIntExtra("currentChapter", 0);
        storyFilename = intent.getStringExtra("story");
        chapter = getCurrentChapter();

        mChapterNumber.setText(String.format(Locale.getDefault(), "פרק %d", currentChapter + 1));
        try {
            mChapterTitle.setText(chapter.getString("title"));
            imgChapterCover.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier("drawable/" + storyFilename + "_" + chapter.getString("title_image"), null, getPackageName())));
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
        }
        bRestartChapter.setText(String.format(Locale.getDefault(), "חזור לתחילת פרק %d", currentChapter));
        bStartChapter.setText("המשך בסיפור");

        bStartChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(ChapterActivity.this, SceneActivity.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences(storyFilename, 0);
                // TODO intent.setFlags(...)
                scene_intent.putExtra("story", storyFilename);
                scene_intent.putExtra("currentChapter", currentChapter);
                scene_intent.putExtra("currentScene", pref.getInt("currentScene", 0));
                scene_intent.putExtra("currentLine", pref.getInt("currentLine", 0));
                startActivity(scene_intent);
                // TODO Skip chapter without scenes
            }
        });

        bRestartStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(ChapterActivity.this, SceneActivity.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences(storyFilename, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("currentChapter", 0);
                editor.putInt("currentScene", 0);
                editor.apply();
                // TODO intent.setFlags(...)
                scene_intent.putExtra("story", storyFilename);
                scene_intent.putExtra("currentChapter", 0);
                scene_intent.putExtra("currentScene", 0);
                scene_intent.putExtra("currentLine", 0);
                startActivity(scene_intent);
                // TODO Skip chapter without scenes
            }
        });

        bRestartChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(ChapterActivity.this, SceneActivity.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences(storyFilename, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("currentChapter", currentChapter - 1);
                editor.putInt("currentScene", 0);
                editor.apply();
                // TODO intent.setFlags(...)
                scene_intent.putExtra("story", storyFilename);
                scene_intent.putExtra("currentChapter", currentChapter - 1);
                scene_intent.putExtra("currentScene", 0);
                scene_intent.putExtra("currentLine", 0);
                startActivity(scene_intent);
                // TODO Skip chapter without scenes
            }
        });
    }

    // Returns the information on the current chapter from the story
    @Nullable
    protected JSONObject getCurrentChapter() {
        try { // TODO Use (de)serializable class and storyJSON = ex_storyJSON.getClass(...)
            // TODO InputStream stream = getApplicationContext().openFileInput(storyFilename);
            InputStream stream = getResources().openRawResource(getResources().getIdentifier("raw/" + storyFilename, null, this.getPackageName()));
            JSONArray chapters = new JSONObject(JSONUtils.loadJSONFromStream(stream)).getJSONArray("chapters");
            return chapters.getJSONObject(currentChapter);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}