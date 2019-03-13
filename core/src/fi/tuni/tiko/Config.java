package fi.tuni.tiko;

public class Config {
    private static float stepStartPosition = 2;
    private static boolean audio;
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

    public static boolean isAudio() {
        return audio;
    }

    public static void setAudio(boolean audio) {
        Config.audio = audio;
    }
}