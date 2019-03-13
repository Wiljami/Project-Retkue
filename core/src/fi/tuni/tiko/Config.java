package fi.tuni.tiko;

public class Config {
    private static float stepStartPosition = 2;
    private static boolean audio;
    private Language language;

    private enum Language {FINNISH, ENGLISH};

    public static float getStepStartPosition() {
        return stepStartPosition;
    }

    public static void setStepStartPosition(float stepStartPosition) {
        Config.stepStartPosition = stepStartPosition;
    }
}