package il.co.heroesui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ChapterActivity extends AppCompatActivity {
    TextView mChapterTitle;
    Button bStartChapter;
    Button bRestartStory;
    Button bRestartChapter;

    private int currentChapter;
    private String storyFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO String storyFilename;
        Intent intent;

        /* Initialization */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        mChapterTitle = (TextView) findViewById(R.id.chapter_title);
        bStartChapter = (Button) findViewById(R.id.start_chapter_button);
        bRestartStory = (Button) findViewById(R.id.start_over_button);
        bRestartChapter = (Button) findViewById(R.id.restart_previous_chapter_button);

        /* Load intent */
        intent = getIntent();
        currentChapter = intent.getIntExtra("currentChapter", 0);
        storyFilename = intent.getStringExtra("story");

        mChapterTitle.setText(String.format(Locale.getDefault(), "פרק %d", currentChapter + 1));
        bRestartChapter.setText(String.format(Locale.getDefault(), "חזור לתחילת פרק %d", currentChapter));

        bStartChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(ChapterActivity.this, SceneActivity.class);
                // TODO intent.setFlags(...)
                scene_intent.putExtra("story", storyFilename);
                scene_intent.putExtra("currentChapter", currentChapter);
                // TODO Save progress to shared preferences
                scene_intent.putExtra("currentScene", 0);
                scene_intent.putExtra("currentLine", 0);
                startActivity(scene_intent);
                // TODO Skip chapter without scenes
            }
        });

        bRestartStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene_intent = new Intent(ChapterActivity.this, SceneActivity.class);
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
}