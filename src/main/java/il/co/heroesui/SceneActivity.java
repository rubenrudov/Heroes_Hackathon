package il.co.heroesui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static il.co.heroesui.utils.JSONUtils.loadJSONFromStream;


public class SceneActivity extends AppCompatActivity {
    public static final String TAG = "SCENE_VIEW";
    public static final String LINE_SPLIT_REGEX = "(\\s*\\r?\\n\\s*)+";  // Split by newline and strip

    private TextView mTextView;
    private Button bOption1;
    private Button bOption2;
    private Button bOption3;
    private int currentChapter;
    private int currentScene;
    private int currentLine;
    private int lastChapter;
    private int lastScene;
    private int lastLine;
    private JSONObject storyJSON;
    private String[] currentSceneLines;
    private Boolean inScene;
    private String storyFilename;
    private String survivorName;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Initialization */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mTextView = (TextView) findViewById(R.id.main_text);
        bOption1 = (Button) findViewById(R.id.choice_option_1);
        bOption2 = (Button) findViewById(R.id.choice_option_2);
        bOption3 = (Button) findViewById(R.id.choice_option_3);
        ConstraintLayout layoutSceneContainer = (ConstraintLayout) findViewById(R.id.scene_container);
        inScene = true;
        JSONObject chapter;

        /* Load intent */
        Intent intent = getIntent();
        survivorName = intent.getStringExtra("survivor");
        // TODO Load game
        storyFilename = intent.getStringExtra("story");
        /* Load shared prefs */
        pref = getApplicationContext().getSharedPreferences(storyFilename, 0);
        if (pref.getBoolean("completed", false)) {
            startEpilogue();
        }
        currentChapter = intent.getIntExtra("currentChapter", pref.getInt("currentChapter", 0));
        currentScene = intent.getIntExtra("currentScene", pref.getInt("currentScene", 0));
        currentLine = intent.getIntExtra("currentLine", 0);

        mTextView.setText(survivorName);
        try {
            Log.i(TAG, getFilesDir().getAbsolutePath());
            // TODO InputStream stream = getApplicationContext().openFileInput(storyFilename);
            InputStream stream = getResources().openRawResource(getResources().getIdentifier("raw/" + storyFilename, null, this.getPackageName()));
            storyJSON = new JSONObject(Objects.requireNonNull(loadJSONFromStream(stream)));
            lastChapter = storyJSON.getJSONArray("chapters").length() - 1;
            lastScene = Objects.requireNonNull(getCurrentChapter()).getJSONArray("scenes").length() - 1;
            stream.close();
            updateSceneLines();
            chapter = getCurrentChapter();
            layoutSceneContainer.setBackground(ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier("drawable/" + chapter.getString("background"), null, getPackageName())));
            showNarration(getCurrentLine());
        }
        catch(Exception e) {
            Log.e(TAG, e.toString());
            finish();
        }

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFollowingLine();
            }
        });

        bOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFollowingLine(1);
            }
        });

        bOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFollowingLine(2);
            }
        });

        bOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFollowingLine(3);
            }
        });
    }

    private void updateSceneLines() throws JSONException {
        currentSceneLines = Objects.requireNonNull(getCurrentScene()).getString("text").split(LINE_SPLIT_REGEX);
        lastLine = currentSceneLines.length - 1;
    }

    private void updateSceneLines(int choice_option) throws JSONException {
        currentSceneLines = Objects.requireNonNull(getCurrentScene()).getJSONArray("effects").optString(choice_option - 1, "").split(LINE_SPLIT_REGEX);
        lastLine = currentSceneLines.length - 1;
    }

    // Returns the information on the current scene from the story
    @Nullable
    protected JSONObject getCurrentScene() {
        try { // TODO Use (de)serializable class and storyJSON = ex_storyJSON.getClass(...)
            JSONObject chapter = getCurrentChapter();
            assert chapter != null;  // TODO Change from assert to something more descriptive than crashing
            JSONArray scenes = chapter.getJSONArray("scenes");
            return scenes.getJSONObject(currentScene);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Returns the information on the current chapter from the story
    @Nullable
    protected JSONObject getCurrentChapter() {
        try { // TODO Use (de)serializable class and storyJSON = ex_storyJSON.getClass(...)
            JSONArray chapters = storyJSON.getJSONArray("chapters");
            return chapters.getJSONObject(currentChapter);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Returns the text of the current line
    protected String getCurrentLine() {
        return currentSceneLines[currentLine];
    }

    // Transitions to the following line from the story
    protected void gotoFollowingLine() {
        // TODO Save game
        SharedPreferences.Editor editor;
        try { // TODO Use (de)serializable class and storyJSON = ex_storyJSON.getClass(...)
            JSONObject scene;
            String firstOption;
            if (lastLine <= currentLine) {
                if (inScene) {
                    // Move to choice
                    scene = getCurrentScene();
                    assert scene != null;  // TODO Change assert into something more descriptive than crashing
                    firstOption = scene.getJSONArray("choices").optString(0, "");
                    inScene = false;
                    if (firstOption.isEmpty()) {
                        // Skip to next scene / chapter
                        gotoFollowingLine();
                    } else {
                        showChoice(
                            firstOption,
                            scene.getJSONArray("choices").optString(1, ""),
                            scene.getJSONArray("choices").optString(2, "")
                        );
                    }
                } else {
                    if (lastScene <= currentScene) {
                        if (lastChapter <= currentChapter) {
                            // Move to epilogue
                            editor = pref.edit();
                            editor.putBoolean("completed", true);
                            editor.apply();
                            startEpilogue();
                        } else {
                            // Move to next chapter
                            startNextChapter();
                        }
                    } else {
                        // Move to next scene
                        startNextScene();
                    }
                }
            } else {
                currentLine++;
                showNarration(getCurrentLine());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Transitions to the following line from the story after a choice
    protected void gotoFollowingLine(int choice_option) {
        // TODO Save game
        JSONObject scene;
        String effect;

        try { // TODO Use (de)serializable class and storyJSON = ex_storyJSON.getClass(...)
            if (inScene) {
                Log.w(TAG, "No choice here!");
                return;
            }
            // Move to effect (reaction / consequence)
            scene = getCurrentScene();
            effect = scene.getJSONArray("effects").optString(choice_option - 1, "");
            if (effect.isEmpty()) {
                // There are no choices, go straight to next line
                gotoFollowingLine();
            } else {
                startChoice(choice_option);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void startChoice(int choice_option) throws JSONException {
        updateSceneLines(choice_option);
        currentLine = 0;
        showNarration(getCurrentLine());
    }

    protected void startNextScene() throws JSONException {
        SharedPreferences.Editor editor;
        editor = pref.edit();
        editor.putInt("currentScene", ++currentScene);
        editor.apply();
        currentLine = 0;
        updateSceneLines();
        showNarration(getCurrentLine());
        inScene = true;
    }

    protected void startNextChapter() throws JSONException {
        SharedPreferences.Editor editor;
        Intent intent = new Intent(SceneActivity.this, ChapterActivity.class);
        editor = pref.edit();
        editor.putInt("currentChapter", ++currentChapter);
        editor.putInt("currentScene", 0);
        editor.apply();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("currentChapter", currentChapter);
        intent.putExtra("story", storyFilename);
        inScene = true;
        startActivity(intent);
    }

    protected void startEpilogue() {
        Intent intent = new Intent(SceneActivity.this, EndingStoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("story", storyFilename);
        intent.putExtra("survivor", survivorName);
        startActivity(intent);
    }

    // Presents a single line of narration
    protected void showNarration(String text) {
        if (text.startsWith("~")) {
            // Show quote
            text = text.replaceFirst("~\\s*", "");
            mTextView.setTextAppearance(R.style.Quote);
        } else {
            // Show normal narration
            mTextView.setTextAppearance(R.style.Narration);
        }
        mTextView.setText(text);

        if (!(mTextView.getVisibility() == View.VISIBLE && bOption1.getVisibility() == View.GONE)) {
            mTextView.setVisibility(View.VISIBLE);
            bOption1.setVisibility(View.GONE);
            bOption2.setVisibility(View.GONE);
            bOption3.setVisibility(View.GONE);
        }
    }

    // Presents a choice of options
    protected void showChoice(
        String option_1,
        String option_2,
        String option_3
    ) {
        mTextView.setVisibility(View.GONE);
        if (!option_1.isEmpty()) {
            bOption1.setVisibility(View.VISIBLE);
            bOption1.setText(option_1);
        }
        if (!option_2.isEmpty()) {
            bOption2.setVisibility(View.VISIBLE);
            bOption2.setText(option_2);
        }
        if (!option_3.isEmpty()) {
            bOption3.setVisibility(View.VISIBLE);
            bOption3.setText(option_3);
        }
    }

    // Advances the story
    protected void nextLine(int option_id) {
        this.currentLine++;
    }
}