package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class SaveGame {
    public static void save(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        save.putString("title", "Retkue Save");
        save.flush();
    }

    public static boolean load(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        String title = save.getString("title", "null");
        if (title.equals("null")) {
            return false;
        }
        System.out.println(title);
        return true;
    }
}