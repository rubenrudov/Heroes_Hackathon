package il.co.heroesui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ChapterActivity extends AppCompatActivity {
    TextView mChapterTitle;
    Button bStartChapter;
    Button bRestartStory;
    Button bRestartChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Initialization */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        mChapterTitle = (TextView) findViewById(R.id.chapter_title);
        bStartChapter = (Button) findViewById(R.id.start_chapter_button);
        bRestartStory = (Button) findViewById(R.id.start_over_button);
        bRestartChapter = (Button) findViewById(R.id.restart_previous_chapter_button);

        /* Load intent */
        Intent intent = getIntent();
    }
}