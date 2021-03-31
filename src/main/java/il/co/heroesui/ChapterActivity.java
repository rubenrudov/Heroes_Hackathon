package il.co.heroesui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ChapterActivity extends AppCompatActivity {

    // Properties
    Intent intent;
    Button chap1, chap2, chap3;
    TextView storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Initialization */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
    }
}