package com.tpeleg.heroeshackathon2021.common;

import java.util.HashMap;
import java.util.Map;

public class ScenePart {
    public PartType type;
    public int chapter;
    public int scene;
    public int line;
    public String text;

    public ScenePart(PartType t, int chapter, int scene, int line, String text) {
        this.type = t;
        this.chapter = chapter;
        this.scene = scene;
        this.line = line;
        this.text = text;
    }

    public static ScenePart deserialize(Map<String, Object> data) {
        try {
            PartType t = PartType.valueOf((String)data.get("type"));

            switch (t) {
                case NARRATION:
                    return Narration.deserialize(data=data);
                case CHOICE:
                    return Choice.deserialize(data=data);
                default:
                    throw new UnsupportedClassVersionError(t.name());
            }
        } catch (EnumConstantNotPresentException e) {
            throw new UnsupportedClassVersionError((String)data.get("type"));
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("t", this.type.name());
        data.put("chapter", this.chapter);
        data.put("scene", this.scene);
        data.put("line", this.line);
        data.put("text", this.text);

        return data;
    }

    public static class Narration extends ScenePart {
        public int next_chapter;
        public int next_scene;
        public int next_line;

        public Narration(int chapter, int scene, int line, int next_chapter, int next_scene, int next_line, String text) {
            super(PartType.NARRATION, chapter, scene, line, text);
            this.next_chapter = next_chapter;
            this.next_scene = next_scene;
            this.next_line = next_line;
        }

        public static Narration deserialize(Map<String, Object> data) {
            return new Narration(
                chapter=(int)data.get("chapter"),
                scene=(int)data.get("scene"),
                line=(int)data.get("line"),
                next_chapter=(int)data.get("next_chapter"),
                next_scene=(int)data.get("next_scene"),
                next_line=(int)data.get("next_line")
            );
        }

        @Override
        public Map<String, Object> serialize() {
            Map<String, Object> data = super.serialize();

            data.put("next_chapter", this.next_chapter);
            data.put("next_scene", this.next_scene);
            data.put("next_line", this.next_line);

            return data;
        }
    }

    public static class Choice extends ScenePart {
        public int choices;

        public String[] text;

        public int[] next_chapter;
        public int[] next_scene;
        public int[] next_line;
        public String[] content;

        public Choice(int chapter, int scene, int line, int[] next_chapter, int[] next_scene, int[] next_line, String[] content, String text) {
            super(t=PartType.CHOICE, chapter=chapter, scene=scene, line=line, text=text);
            this.next_chapter = next_chapter;
            this.next_scene = next_scene;
            this.next_line = next_line;
            this.content = content;
        }

        public static Choice deserialize(Map<String, Object> data) {
            Map<String, Object> choices = data.get("choices");

            return new Choice(
                chapter=(int)data.get("chapter"),
                scene=(int)data.get("scene"),
                line=(int)data.get("line")
            );
        }

        @Override
        public Map<String, Object> serialize() {
            Map<String, Object> data = super.serialize();

            data.put("next_chapter", this.next_chapter);
            data.put("next_scene", this.next_scene);
            data.put("next_line", this.next_line);

            return data;
        }
    }
}
