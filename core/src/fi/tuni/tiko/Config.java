package fi.tuni.tiko;

public class Config {
    private static float stepStartPosition = 2;
    private static boolean isMuted = false;
    private static Language language;

    enum Language {FINNISH, ENGLISH};

    public static float getStepStartPosition() {
        return stepStartPosition;
    }

    public static void setStepStartPosition(float stepStartPosition) {
        Config.stepStartPosition = stepStartPosition;
    }

    public static String getLanguageName() {
        if (language == Language.FINNISH) {
            return "FI";
        } else {
            return "EN";
        }
    }

    public static void setLanguage (Language l) {
        language = l;
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void setIsMuted(boolean audio) {
        Config.isMuted = audio;
    }
}