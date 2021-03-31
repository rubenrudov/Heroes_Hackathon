package il.co.heroesui.handler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import il.co.heroesui.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import il.co.heroesui.SceneActivity;
import il.co.heroesui.models.Story;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Story> stories;
    // TODO: Add onClickListener property

    public StoriesAdapter(Context context, ArrayList<Story> stories) {
        this.stories = stories;
        this.context = context;
        // TODO: Duplicate list for filtering feature
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        // view.setOnClickListener(this.onClickProp);
        // On click listener for the view (Static for now)
        //        view.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                TextView title = view.findViewById(R.id.storyTitle);
        //                if (title.getText().toString().contains("קאז'ק")) {
        //
        //                } else {
        //                    Log.d("Ruby", "Other");
        //                }
        //            }
        //        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Story story = this.stories.get(position);
        viewHolder.title.setText(story.getTitle());
        viewHolder.theme.setBackgroundResource(story.getResource());
    }

    @Override
    public int getItemCount() {
        return this.stories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView theme;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.storyTitle);
            this.theme = itemView.findViewById(R.id.theme);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;

                    if (title.getText().toString().contains("קאז'ק")) {
                        intent = new Intent(StoriesAdapter.this.context, SceneActivity.class);
                        intent.putExtra("story", "kazik");
                    }
                    else if (title.getText().toString().contains("חיים")){
                        intent = new Intent(StoriesAdapter.this.context, SceneActivity.class);
                        intent.putExtra("story", "haim");
                    }

                    else {
                        intent = new Intent(StoriesAdapter.this.context, SceneActivity.class);
                        intent.putExtra("story", "eva");
                    }
                    StoriesAdapter.this.context.startActivity(intent);
                }
            });
        }
    }
}
