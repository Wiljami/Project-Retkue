package fi.tuni.tiko;

/**
 * Quest class. This should hold information about the different quests in the game.
 * TODO: This thing
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Quest {
    private String title;
    private String shortDescription;
    private String longDescription;
    private int reward;
    private long questLength;
    private int difficulty;
    //private enum questGiver?

    private static long questStarted;

    public Quest () {
        questLength = 1000000L;
    }

    public void begin() {
        questStarted = System.currentTimeMillis();
    }

    public long timeLeft() {
        return questStarted - System.currentTimeMillis() + questLength;
    }

    public void boost() {
        questLength = timeLeft()/2 + System.currentTimeMillis() - questStarted;
    }
}