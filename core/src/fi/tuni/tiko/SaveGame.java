package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveGame {
    public static void save(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        save.putString("title", "Retkue Save");

        saveConfig(save);
        saveParty(save, party);

        save.flush();
    }

    private static void saveConfig (Preferences save) {
        save.putString("language",  Config.getLanguageName());
        save.putBoolean("audio", Config.isAudio());
        save.putFloat("stepStartPosition", Config.getStepStartPosition());
    }

    private static void saveParty (Preferences save, Party party) {
        save.putInteger("gold", party.getGold());
        save.putInteger("steps", party.getSteps());

        for (int x = 0; x < 3; x++) {
            Retku retku = party.findRetku(x);
            save.putString("retku_" + x + "_name", retku.getName());
            save.putInteger("retku_" + x + "_maxHealth", retku.getMaxHealth());
            save.putInteger("retku_" + x + "_currHealth", retku.getCurrHealth());
        }
    }

    public static boolean load(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        String title = save.getString("title", "null");
        if (title.equals("null")) {
            return false;
        }
        System.out.println(title);

        loadConfig(save);
        loadParty(save, party);

        return true;
    }

    private static void loadConfig(Preferences save) {
        String language = save.getString("language", "null");
        setLanguage(language);

        Float stepStart = save.getFloat("stepStartPosition", 0.0f);
        Config.setStepStartPosition(stepStart);

        Boolean audio = save.getBoolean("audio", false);
        Config.setAudio(audio);


        System.out.println(language);
        System.out.println(stepStart);
        System.out.println(audio);
    }

    private static void loadParty(Preferences save, Party party) {
        int gold = save.getInteger("gold", 0);
        party.setGold(gold);
        int steps = save.getInteger("steps", 0);
        party.setSteps(steps);

        for (int x = 0; x < 3; x++) {
            Retku retku = new Retku("", 0);
            String name = save.getString("retku_" + x + "_name", "NO_NAME");
            int maxHealth = save.getInteger("retku_" + x + "_maxHealth", 100);
            int currHealth = save.getInteger("retku_" + x + "_currHealth", 0);
            System.out.println(name + "::" + currHealth + "/" + currHealth);
            retku.setName(name);
            retku.setMaxHealth(maxHealth);
            retku.setCurrHealth(currHealth);
            party.addRetku(retku, x);
        }

        System.out.println(gold);
        System.out.println(steps);
    }

    private static void setLanguage(String language) {
        if (language.equals("FI")) {
            Config.setLanguage(Config.Language.FINNISH);
        } else {
            Config.setLanguage(Config.Language.ENGLISH);
        }
    }
}