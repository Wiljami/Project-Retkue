package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveGame {
    public static void save(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        save.putString("title", "Retkue Save");
        save.putString("language",  Config.getLanguageName());
        save.putBoolean("audio", Config.isAudio());
        save.putFloat("stepStartPosition", Config.getStepStartPosition());

        save.flush();
    }

    public static boolean load(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        String title = save.getString("title", "null");
        if (title.equals("null")) {
            return false;
        }

        String language = save.getString("language", "null");
        setLanguage(language);

        Float steps = save.getFloat("stepStartPosition", 0.0f);
        Config.setStepStartPosition(steps);

        Boolean audio = save.getBoolean("audio", false);
        Config.setAudio(audio);

        System.out.println(title);
        System.out.println(language);
        System.out.println(steps);
        System.out.println(audio);

        return true;
    }

    private static void setLanguage(String language) {
        if (language.equals("FI")) {
            Config.setLanguage(Config.Language.FINNISH);
        } else {
            Config.setLanguage(Config.Language.ENGLISH);
        }
    }
}