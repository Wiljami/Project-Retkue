package fi.tuni.tiko;

/**
 * Quest class. This should hold information about the different quests in the game.
 * TODO: This thing
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Quest {
    private int ID;
    private String title;
    private String shortDescription;
    private String longDescription;
    private int reward;
    private long questLength;
    private int difficulty;
    private QuestGiver questGiver;

    enum QuestGiver {oldMan}

    public Quest () {
        questLength = 1000000L;
    }

    public long getQuestLength() {
        return questLength;
    }
}