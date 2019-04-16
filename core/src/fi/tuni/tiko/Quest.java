package fi.tuni.tiko;


/**
 * Quest class. This should hold information about the different quests in the game.
 * TODO: This thing
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Quest {
    private int id;
    private String titleKey;
    private String briefingKey;
    private String descriptionKey;
    private String acceptTextKey;
    private String completeTextKey;
    private Reward reward;
    private long questLength;
    private long[] encounterPoint;
    private int difficulty;
    private QuestGiver questGiver;

    enum QuestGiver {oldMan}

    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver) {
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
        this.difficulty = 1;
        generateBundleKeys();
    }

    private void generateBundleKeys() {
        String bundle_id = Utils.convertToId(id);
        String halfKey = "QUEST_" + bundle_id;

        titleKey = halfKey + "_TITLE";
        briefingKey = halfKey + "_TEXT";
        acceptTextKey = halfKey + "_ACCEPT";
        descriptionKey = halfKey + "_DESC";
        completeTextKey = halfKey + "_COMPLETE";
    }

    public long getQuestLength() {
        return questLength;
    }

    public String getTitle() {
        return Utils.readBundle(Scene.getBundle(), titleKey);
    }

    public String getBriefing() {
        return Utils.readBundle(Scene.getBundle(), briefingKey);
    }

    public String getDescription() {
        return Utils.readBundle(Scene.getBundle(), descriptionKey);
    }

    public String getAcceptText() {
        return Utils.readBundle(Scene.getBundle(), acceptTextKey);
    }

    public String getCompleteText() {
        return Utils.readBundle(Scene.getBundle(), completeTextKey);
    }

    public Reward getReward() {
        return reward;
    }

    public int getId() {
        return id;
    }
}