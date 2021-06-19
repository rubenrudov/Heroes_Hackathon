package il.co.heroesui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONUtils {
    public static String loadJSONFromStream(InputStream is) {
        String json = "";
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);  // TODO Fix this warning, maybe write something that makes more sense
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
