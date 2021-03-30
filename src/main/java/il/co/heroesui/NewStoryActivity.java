package il.co.heroesui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewStoryActivity extends AppCompatActivity {
    FirebaseDatabase database;
    Task<Void> databaseReference;
    EditText storyTitle, chap1, chap2, scene1, scene2, chap2scene1, chap2scene2;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);
        database = FirebaseDatabase.getInstance();

        storyTitle = findViewById(R.id.storyTitleEditText);
        chap1 = findViewById(R.id.chapterTitle);
        chap2 = findViewById(R.id.chapter2Title);
        scene1 = findViewById(R.id.scene1);
        scene2 = findViewById(R.id.scene2);
        chap2scene1 = findViewById(R.id.chap2scene1);
        chap2scene2 = findViewById(R.id.chap2scene2);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> addMap = new HashMap<>();
                addMap.put("storyTitle", storyTitle.getText().toString());
                addMap.put("chapter1", chap1.getText().toString());
                addMap.put("chapter2",  chap2.getText().toString());
                addMap.put("chapter1scene1",  scene1.getText().toString());
                addMap.put("chapter1scene2",  scene2.getText().toString());
                addMap.put("chapter2scene1",  chap2scene1.getText().toString());
                addMap.put("chapter2scene2",  chap2scene2.getText().toString());
                databaseReference = database.getReference().child(storyTitle.getText().toString()).setValue(addMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Ruby", "DONE");
                                }
                            }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Ruby", "PROBLEM");
                                }
                            }
                        );
                    }
                });
    }
}
