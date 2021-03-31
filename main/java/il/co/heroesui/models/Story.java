package il.co.heroesui.models;

import java.util.Arrays;

public class Story {
    private String title;
    private int resource;
    private String[] scene, chapters;

    public Story(String title, int resource) {
        this.title = title;
        this.resource = resource;
        this.scene = null;
        this.chapters = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", resource=" + resource +
                ", scene=" + Arrays.toString(scene) +
                ", chapters=" + Arrays.toString(chapters) +
                '}';
    }
}
