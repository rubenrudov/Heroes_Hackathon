package il.co.heroesui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Objects;
import il.co.heroesui.handler.StoriesAdapter;
import il.co.heroesui.models.Story;

public class SelectGameActivity extends AppCompatActivity {

    ArrayList<Story> stories;
    RecyclerView recyclerView;
    StoriesAdapter storiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("בחרי/י סיפור");

        recyclerView = findViewById(R.id.recyclerView);
        stories = new ArrayList<>();
        stories.add(new Story("שמחה רותם (קאז'ק)", R.drawable.kazik));
        stories.add(new Story("חיים ביזדרובסקי", R.drawable.haim));
        stories.add(new Story("אווה לביא", R.drawable.eva));
        storiesAdapter = new StoriesAdapter(getApplicationContext(), stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(storiesAdapter);
    }
}
