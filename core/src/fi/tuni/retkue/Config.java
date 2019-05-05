package fi.tuni.retkue;

/**
 * Class Config holds configuration information
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class Config {
    /**
     * Start position for the steps counter
     */
    private static float stepStartPosition = 0;

    /**
     * boolean isMuted - wether the background music is muted
     */
    private static boolean isMuted = false;

    /**
     * Language language - language used in the game
     */
    private static Language language;

    /**
     * enum Language different languages available
     */
    enum Language {FINNISH, ENGLISH}

    /**
     * getter for stepStartPosition
     * @return stepStartPosition
     */
    public static float getStepStartPosition() {
        return stepStartPosition;
    }

    /**
     * setter for stepStartPosition
     * @param stepStartPosition new stepStartPosition
     */
    public static void setStepStartPosition(float stepStartPosition) {
        Config.stepStartPosition = stepStartPosition;
    }

    /**
     * getLanguageName returns the two letter String used for bundle
     * @return String of the language name
     */
    public static String getLanguageName() {
        if (language == Language.FINNISH) {
            return "fi";
        } else {
            return "en";
        }
    }

    /**
     * Setter for the language
     * @param l new language
     */
    public static void setLanguage (Language l) {
        language = l;
        Scene.loadBundle();
    }

    /**
     * isMuted
     * @return isMuted
     */
    public static boolean isMuted() {
        return isMuted;
    }

    /**
     * Setter for isMuted
     * @param audio new isMuted
     */
    public static void setIsMuted(boolean audio) {
        Config.isMuted = audio;
    }
}