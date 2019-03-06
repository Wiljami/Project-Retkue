package fi.tuni.tiko;

/**
 * Quest class. This should hold information about the different quests in the game.
 * @author Viljami Pietarila
 * @version 2019.0306
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
        questLength = 100000;
    }

    public void begin() {
        questStarted = System.currentTimeMillis();
    }

    public long timeLeft() {
        return questStarted - System.currentTimeMillis() + questLength;
    }

    public void boost() {
        questLength = timeLeft()/2;
    }
}