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
    private String briefing;
    private String description;
    private String acceptText;
    private String completeText;
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
        this.difficulty = 1;
        readDescriptions();
    }

    private void readDescriptions() {
        String bundle_id = Utils.convertToId(id);
        String halfKey = "QUEST_" + bundle_id;
        String key = halfKey + "_TITLE";
        title = Utils.readBundle(Scene.getBundle(), key);
        key = halfKey + "_TEXT";
        briefing = Utils.readBundle(Scene.getBundle(), key);
        key = halfKey + "_ACCEPT";
        acceptText = Utils.readBundle(Scene.getBundle(), key);
        key = halfKey + "_DESC";
        description = Utils.readBundle(Scene.getBundle(), key);
        key = halfKey + "_COMPLETE";
        completeText = Utils.readBundle(Scene.getBundle(), key);
    }

    public long getQuestLength() {
        return questLength;
    }

    public String getTitle() {
        return title;
    }
}