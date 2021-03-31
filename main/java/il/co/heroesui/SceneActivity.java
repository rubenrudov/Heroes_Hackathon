package il.co.heroesui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SceneActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button bOption1;
    private Button bOption2;
    private Button bOption3;
    private int currentLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        Intent gameData = getIntent();
        // mTextView = (TextView) findViewById(R.id.main_text);
        // bOption1 = (Button) findViewById(R.id.choice_option_1);
        // bOption2 = (Button) findViewById(R.id.choice_option_2);
        // bOption3 = (Button) findViewById(R.id.choice_option_3);
    }
}