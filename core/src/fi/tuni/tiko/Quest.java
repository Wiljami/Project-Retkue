package fi.tuni.tiko;

/**
 * Quest class. This should hold information about the different quests in the game.
 * TODO: This thing
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Quest {
    private int id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private Reward reward;
    private long questLength;
    private int difficulty;
    private QuestGiver questGiver;

    enum QuestGiver {oldMan}

    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver) {
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
    }

    public long getQuestLength() {
        return questLength;
    }
}