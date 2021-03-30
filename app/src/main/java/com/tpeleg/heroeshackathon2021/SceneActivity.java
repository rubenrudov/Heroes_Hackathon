package com.tpeleg.heroeshackathon2021;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SceneActivity extends Activity {

    private TextView mTextView;
    private Button bOption1;
    private Button bOption2;
    private Button bOption3;
    private int currentLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        mTextView = (TextView) findViewById(R.id.main_text);
        bOption1 = (Button) findViewById(R.id.choice_option_1);
        bOption2 = (Button) findViewById(R.id.choice_option_2);
        bOption3 = (Button) findViewById(R.id.choice_option_3);

        Intent intent = getIntent();
    }

    /*
    Presents a single line of narration
     */
    protected void showNarration(String text) {
        mTextView.setText(text);

        if (!(mTextView.getVisibility() == View.VISIBLE && bOption1.getVisibility() == View.GONE)) {
            mTextView.setVisibility(View.VISIBLE);
            bOption1.setVisibility(View.GONE);
            bOption2.setVisibility(View.GONE);
            bOption3.setVisibility(View.GONE);
        }
    }

    /*
    Presents a choice of options
     */
    protected void showChoice(
            String option_1,
            String option_2,
            String option_3,
    ) {
        mTextView.setVisibility(View.GONE);


    }

    /*
    Advances the story
    */
    protected void nextLine(int option_id) {
        this.currentLine++;
    }
}