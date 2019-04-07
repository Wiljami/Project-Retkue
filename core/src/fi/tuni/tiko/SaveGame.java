package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Class SaveGame handles loading and saving the data within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0313
 */
public class SaveGame {
    /**
     * save method saves the game data to the Preferences file.
     *
     * Each save has key "title" with val "Retkue Save". If this is not there upon load, the load
     * will assume that the save is not valid and start a fresh game.
     * @param saveFile name of the savefile
     * @param party reference to the player party
     */
    public static void save(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        save.putString("title", "Retkue Save");

        saveConfig(save);
        saveParty(save, party);

        save.flush();
    }

    /**
     * load reads the Preferences file for the game variables. If there is no matching title
     * variable within the Preferences file, the load will assume that this is a brand new game.
     * Such as fresh install and return false to inform about it. Otherwise returns true.
     * @param saveFile filename of the save
     * @param party reference to the player party
     * @return boolean wether the load is succesful
     */
    public static boolean load(String saveFile, Party party) {
        Preferences save = (Gdx.app.getPreferences(saveFile));
        String title = save.getString("title", "null");
        if (title.equals("null")) {
            return false;
        }

        loadConfig(save);
        loadParty(save, party);

        return true;
    }

    /**
     * Saves the variables in the Config
     * @param save Preferences to which saves are made
     */
    private static void saveConfig (Preferences save) {
        save.putString("language",  Config.getLanguageName());
        save.putBoolean("muted", Config.isMuted());
        save.putFloat("stepStartPosition", Config.getStepStartPosition());
    }

    /**
     * Saves the variables in the player's party
     * @param save reference to the Preferences save
     * @param party reference to the player party
     */
    private static void saveParty (Preferences save, Party party) {
        save.putInteger("gold", party.getGold());
        save.putInteger("steps", party.getSteps());

        for (int x = 0; x < 3; x++) {
            Retku retku = party.findRetku(x);
            save.putInteger("retku_" + x + "_maxHealth", retku.getMaxHealth());
            save.putInteger("retku_" + x + "_currHealth", retku.getCurrHealth());
            if(retku.getSlotA() != null)
            save.putInteger("retku_" + x + "_slotA", retku.getSlotA().getId());
            if(retku.getSlotB() != null)
            save.putInteger("retku_" + x + "_slotB", retku.getSlotB().getId());
            if(retku.getSlotC() != null)
            save.putInteger("retku_" + x + "_slotC", retku.getSlotC().getId());
        }
    }

    /**
     * Loads Config values from the save
     * @param save Preference save
     */
    private static void loadConfig(Preferences save) {
        String language = save.getString("language", "null");
        setLanguage(language);

        Float stepStart = save.getFloat("stepStartPosition", 0.0f);
        Config.setStepStartPosition(stepStart);

        Boolean muted = save.getBoolean("muted", false);
        Config.setIsMuted(muted);
    }

    /**
     * Loads party variables from the save
     * @param save Preferences save
     * @param party Party object to which the values are saved
     */
    private static void loadParty(Preferences save, Party party) {
        int gold = save.getInteger("gold", 0);
        party.setGold(gold);
        int steps = save.getInteger("steps", 0);
        party.setSteps(steps);

        for (int x = 0; x < 3; x++) {
            Retku retku = new Retku("", 0, "", party);
            int maxHealth = save.getInteger("retku_" + x + "_maxHealth", 100);
            int currHealth = save.getInteger("retku_" + x + "_currHealth", 0);
            int itemAId = save.getInteger("retku_" + x + "_slotA", 0);
            int itemBId = save.getInteger("retku_" + x + "_slotB", 0);
            int itemCId = save.getInteger("retku_" + x + "_slotC", 0);
            retku.setMaxHealth(maxHealth);
            retku.setCurrHealth(currHealth);
            retku.giveItemById(itemAId, x);
            retku.giveItemById(itemBId, x);
            retku.giveItemById(itemCId, x);
            party.loadRetku(retku, x);
        }
    }

    /**
     * Converts the language string read from the Preferences file and sets the language in Config
     * accordingly.
     * @param language string language
     */
    private static void setLanguage(String language) {
        if (language.equals("FI")) {
            Config.setLanguage(Config.Language.FINNISH);
        } else {
            Config.setLanguage(Config.Language.ENGLISH);
        }
    }
}